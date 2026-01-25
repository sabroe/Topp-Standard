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

import com.yelstream.topp.standard.domain.commerce.persistence.model.core.Product;
import com.yelstream.topp.standard.domain.commerce.persistence.money.Money;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * A single line item within a Quote, representing one product or service with proposed quantity and price.
 * <p>
 * Captures the negotiated or offered commercial details for an individual item in a quote process.
 * Allows tracking of proposed vs accepted pricing during negotiation.
 * </p>
 * <p>
 * Core attributes are quantity, proposed price, and optional accepted price after agreement.
 * It has required many-to-one associations to both the owning Quote and the referenced Product.
 * </p>
 * <p>
 * This entity is involved in the following key flows and interactions:
 * </p>
 * <ol>
 *     <li>Initial quote line creation (from cart items or manual entry)</li>
 *     <li>Price negotiation (update proposed/accepted price, quantity)</li>
 *     <li>Line-level approval or rejection during quote review</li>
 *     <li>Conversion to OrderLine when quote is accepted</li>
 *     <li>Reporting on quote performance (win rate per product, average discount)</li>
 * </ol>
 */
@Entity
@Table
public class QuoteLine {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Version
    private Long version;

    @Embedded
    @AttributeOverride(name = "amount",   column = @Column(name = "proposed_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "proposed_currency"))
    @Getter @Setter
    private Money proposedPrice;

    @Embedded
    @AttributeOverride(name = "amount",   column = @Column(name = "accepted_amount"))
    @AttributeOverride(name = "currency", column = @Column(name = "accepted_currency"))
    @Getter @Setter
    private Money acceptedPrice;        // set after agreement/negotiation

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Getter
    private Quote quote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Getter
    private Product product;

    // Package-private setter (or protected) – only Quote can call it
    void setQuote(Quote quote) {
        this.quote = quote;
    }

    void setProduct(Product product) {
        this.product = product;
    }

    // quantity is mutable → keep public @Setter
    @Getter @Setter
    private int quantity;
}
