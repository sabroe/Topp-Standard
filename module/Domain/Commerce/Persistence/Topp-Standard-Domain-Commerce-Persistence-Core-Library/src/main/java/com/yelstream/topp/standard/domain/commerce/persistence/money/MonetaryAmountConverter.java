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

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, String> {

    private static final String SEPARATOR = " ";

    // Cache the default factory (thread-safe, initialized once)
    private static final MonetaryAmountFactory<?> FACTORY = Monetary.getDefaultAmountFactory();

    @Override
    public String convertToDatabaseColumn(MonetaryAmount amount) {
        if (amount == null) return null;
        return amount.getNumber().numberValueExact(BigDecimal.class).toPlainString()
                + SEPARATOR
                + amount.getCurrency().getCurrencyCode();
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        String[] parts = dbData.split(SEPARATOR);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid money format: " + dbData);
        }
        return FACTORY
                .setNumber(Double.parseDouble(parts[0]))
                .setCurrency(parts[1])
                .create();
    }
}
