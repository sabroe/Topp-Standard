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

package com.yelstream.topp.standard.domain.commerce.persistence.money;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

/**
 * Simple embeddable value object representing a monetary amount in a specific currency.
 * <p>
 * This class replaces separate {@code BigDecimal} amount + {@code String} currency fields
 * to enforce that amount and currency always travel together and to improve type safety
 * and readability in the domain model.
 * </p>
 * <p>
 * Key characteristics:
 * <ul>
 *     <li>Immutable by design (no public setters — construct via constructor or factory)</li>
 *     <li>Uses {@code BigDecimal} for precise decimal arithmetic (avoids floating-point issues)</li>
 *     <li>Currency stored as ISO 4217 alpha-3 code (e.g. "DKK", "EUR", "USD")</li>
 *     <li>JPA-mapped as an {@code @Embeddable} component with overridden column names when needed</li>
 * </ul>
 * </p>
 * <p>
 * Important notes for production use:
 * <ul>
 *     <li>Arithmetic operations (add, subtract, multiply, compare) should <strong>not</strong> be
 *         implemented directly on this class — use a proper money library (JSR-354 Moneta,
 *         java-money, or your own utility) to handle currency matching, rounding modes, etc.</li>
 *     <li>Equality is based on both amount <strong>and</strong> currency (1.00 DKK ≠ 1.00 EUR)</li>
 *     <li>No built-in formatting — use {@code NumberFormat.getCurrencyInstance()} or similar
 *         when displaying to users</li>
 * </ul>
 * </p>
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@lombok.Builder(builderClassName = "Builder", toBuilder = true)
@EqualsAndHashCode
public class Money {  //TO-DO: Consider using a Java record; applicable from JPA 3.2.
    /**
     *
     */
    @Getter
    @Column(nullable = false, precision = 19, scale = 4)
    private final BigDecimal amount;

    /**
     *
     */
    @Getter
    @Column(length = 3, nullable = false)
    private final String currency;

    public MonetaryAmount toMonetaryAmount() {
        return null;  //TO-DO: Fix!
    }

    public static Money from(MonetaryAmount monetaryAmound) {
        return null;  //TO-DO: Fix!
    }
}
