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

package com.yelstream.topp.standard.domain.commerce.persistence.model.ext.quotation;

import com.yelstream.topp.standard.domain.commerce.persistence.model.core.Account;
import com.yelstream.topp.standard.domain.commerce.persistence.model.core.Address;
import com.yelstream.topp.standard.domain.commerce.persistence.model.core.Product;
import com.yelstream.topp.standard.domain.commerce.persistence.model.core.User;
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
 * A formal priced proposal, typically used in B2B to present negotiated or requested pricing to a buyer.
 * <p>
 * Represents a temporary but structured commercial offer that can be negotiated, approved, rejected,
 * or converted into a confirmed Order. It serves as the bridge between shopping intent (Cart) and
 * committed purchase (Order) in scenarios requiring review, approval, or price discussion.
 * </p>
 * <p>
 * Key attributes include business identifier, validity period, requesting party, total proposed value,
 * and status tracking. It owns a collection of QuoteLine items (composition with cascade and orphan removal).
 * </p>
 * <p>
 * This entity participates in the following key business flows and interactions:
 * </p>
 * <ol>
 *     <li>Buyer requests pricing or terms for larger/complex purchases (via Cart → QuoteRequest)</li>
 *     <li>Sales team creates or responds with a formal Quote including proposed prices and conditions</li>
 *     <li>Negotiation rounds (revisions, counter-proposals, price/quantity adjustments)</li>
 *     <li>Internal approval workflows (manager review, credit check, legal sign-off in B2B)</li>
 *     <li>Acceptance → conversion to Order (with price/quantity lock-in)</li>
 *     <li>Rejection/expiration → cleanup or archival</li>
 * </ol>
 */
@Entity
@Table
public class Quote {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Version
    private Long version;

    @Column(unique = true, nullable = false)
    @Getter
    private String quoteNumber;         // e.g. Q-2026-000123 – generated business key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Getter
    private Account account;            // the buying company/individual

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    private User requestedBy;           // the person who requested or is negotiating

    @Getter
    private Instant requestedAt;

    @Getter @Setter
    private Instant validUntil;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private QuoteStatus status;

    @Embedded
    @AttributeOverride(name = "amount",   column = @Column(name = "total_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "total_currency"))
    @Getter
    private Money total;                // calculated / proposed total

    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    private List<QuoteLine> lines = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter @Setter
    private Address shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter @Setter
    private Address billingAddress;

    // Business method: add a line item
    public QuoteLine addLine(Product product,
                             int quantity,
                             Money proposedPrice) {
        QuoteLine line = new QuoteLine();
        line.setQuote(this);
        line.setProduct(product);
        line.setQuantity(quantity);
        line.setProposedPrice(proposedPrice);
        lines.add(line);
        recalculateTotal();
        return line;
    }

    // Simple total recalculation example
    private void recalculateTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        String currency = null;
        for (QuoteLine line : lines) {
            Money p = line.getProposedPrice();
            if (p != null) {
                if (currency == null) {
                    currency = p.getCurrency();
                } else if (!currency.equals(p.getCurrency())) {
                    throw new IllegalStateException("Mixed currencies in quote");
                }
                sum = sum.add(p.getAmount());
            }
        }
        if (currency != null) {
            this.total = new Money(sum, currency);
        } else {
            this.total = null;
        }
    }
}
