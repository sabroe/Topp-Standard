/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yelstream.topp.standard.domain.commerce.persistence.model.ext.accounting;

import com.yelstream.topp.standard.domain.commerce.persistence.model.core.Account;
import com.yelstream.topp.standard.domain.commerce.persistence.model.core.Address;
import com.yelstream.topp.standard.domain.commerce.persistence.model.core.order.Order;
import com.yelstream.topp.standard.domain.commerce.persistence.model.core.order.OrderItem;
import com.yelstream.topp.standard.domain.commerce.persistence.money.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A billing document issued to an account, representing the amount due for one or more orders or services.
 * <p>
 * Serves as the formal request for payment after goods/services are delivered or agreed upon.
 * Can be linked to one Order (simple B2C) or multiple Orders / Quotes / partial deliveries (B2B).
 * </p>
 * <p>
 * Key attributes include invoice number, issue and due dates, total amount, status, and payment status.
 * Owns a collection of InvoiceLine items (composition with cascade and orphan removal).
 * </p>
 * <p>
 * This entity participates in the following key business flows and interactions:
 * </p>
 * <ol>
 *     <li>Order fulfillment completion → automatic invoice creation</li>
 *     <li>Manual invoicing for services, subscriptions, or negotiated B2B terms</li>
 *     <li>Payment processing and reconciliation (capture, partial payments, reminders)</li>
 *     <li>Accounts receivable tracking and overdue management</li>
 *     <li>Credit note / adjustment issuance (after returns or disputes)</li>
 *     <li>Legal / tax reporting (VAT, sales tax, e-invoicing formats)</li>
 * </ol>
 */
@Entity
@Table
public class Invoice {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Version
    private Long version;

    @Column(unique = true, nullable = false)
    @Getter
    private String invoiceNumber;       // e.g. INV-2026-000456 – business key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Getter
    private Account account;            // recipient / debtor

    @Getter
    private Instant issueDate;

    @Getter @Setter
    private Instant dueDate;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private InvoiceStatus status;       // DRAFT, ISSUED, PARTIALLY_PAID, PAID, OVERDUE, CANCELLED

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private PaymentStatus paymentStatus;

    @Embedded
    @AttributeOverride(name = "amount",   column = @Column(name = "total_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "total_currency"))
    @Getter
    private Money total;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<InvoiceLine> lines = new ArrayList<>();

    @ManyToOne
    @Getter @Setter
    private Address billingAddress;

    // Example: link to originating orders (optional – can be many-to-many or via lines)
    @ManyToMany
    @Getter
    private List<Order> orders = new ArrayList<>();

    // Business method example
    public InvoiceLine addLine(OrderItem orderLine, Money amount) {
        InvoiceLine line = new InvoiceLine();
        line.setInvoice(this);
        line.setOrderLine(orderLine);
        line.setAmount(amount);
        lines.add(line);
        recalculateTotal();
        return line;
    }

    private void recalculateTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        String currency = null;
        for (InvoiceLine line : lines) {
            Money amt = line.getAmount();
            if (amt != null) {
                if (currency == null) currency = amt.getCurrency();
                else if (!currency.equals(amt.getCurrency())) {
                    throw new IllegalStateException("Mixed currencies on invoice");
                }
                sum = sum.add(amt.getAmount());
            }
        }
        if (currency != null) {
            this.total = new Money(sum, currency);
        }
    }
}
