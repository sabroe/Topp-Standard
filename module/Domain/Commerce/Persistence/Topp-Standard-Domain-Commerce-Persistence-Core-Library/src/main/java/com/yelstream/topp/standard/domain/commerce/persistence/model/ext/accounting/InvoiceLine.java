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
 * A single line item on an invoice, detailing the charged amount for a specific order line, product, or service.
 * <p>
 * Captures what was billed, how much, and any tax/discount references.
 * </p>
 * <p>
 * Core attributes are quantity (optional), unit price, line amount, and reference to source.
 * Required many-to-one association to the owning Invoice.
 * </p>
 * <p>
 * This entity is involved in the following key flows and interactions:
 * </p>
 * <ol>
 *     <li>Invoice creation from fulfilled Order lines</li>
 *     <li>Partial invoicing or milestone billing in B2B projects</li>
 *     <li>Tax calculation and reporting per line</li>
 *     <li>Dispute / credit note generation per line</li>
 *     <li>Reconciliation with payments received</li>
 * </ol>
 */
@Entity
@Table
public class InvoiceLine {
    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Version
    private Long version;

    private int quantity;               // optional – can be 0 for flat fees

    @Embedded
    @AttributeOverride(name = "amount",   column = @Column(name = "unit_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "unit_currency"))
    @Getter @Setter
    private Money unitPrice;

    @Embedded
    @AttributeOverride(name = "amount",   column = @Column(name = "line_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "line_currency"))
    @Getter @Setter
    private Money amount;               // line total (qty × unit price, or flat)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Getter @Setter
    private Invoice invoice;

    // Optional reference to originating order line
    @ManyToOne(fetch = FetchType.LAZY)
    @Getter @Setter
    private OrderItem orderLine;

    // Optional: tax code, discount applied, etc.
}
