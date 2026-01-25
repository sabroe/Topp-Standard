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

package com.yelstream.topp.standard.domain.commerce.persistence.model.core;

/**
 * Discriminator that classifies the nature of an {@link Account} — whether it represents
 * an individual consumer (B2C-style) or a business/legal entity (B2B-style).
 * <p>
 * This simple enum allows the same {@code Account} entity to be used for both B2C and B2B
 * scenarios without introducing separate class hierarchies, while still enabling type-safe
 * conditional logic, different validation rules, pricing strategies, checkout flows,
 * permission models, and UI presentations.
 * </p>
 * <p>
 * <strong>INDIVIDUAL</strong> — Used for typical B2C situations where the account belongs
 * to a single natural person. Usually linked 1:1 (or close to 1:1) with a {@link User},
 * login often uses the account's email directly, and the focus lies on fast/personalized
 * shopping experiences.
 * </p>
 * <p>
 * <strong>COMPANY</strong> — Used for B2B / organizational accounts. Typically has multiple
 * {@link User Users} associated with it (buyers, approvers, administrators), supports
 * company-level data (VAT ID, credit terms, negotiated prices, shared addresses, approval
 * workflows), and treats the account itself as the legal / invoicing party.
 * </p>
 * <p>
 * This approach closely mirrors established patterns in leading commercial platforms:
 * </p>
 * <ul>
 *     <li><strong>SAP Commerce Cloud</strong> — separates {@code Customer} / {@code B2BCustomer}
 *         (individual buyers) from {@code B2BUnit} (company/organization units), but many
 *         implementations unify them under a common supertype with a type discriminator.</li>
 *     <li><strong>Salesforce B2B Commerce / B2C Commerce</strong> — uses {@code Account}
 *         as the central entity for both consumers and companies, differentiated by record
 *         types, custom fields or Account Type picklists, allowing shared order/quote/cart
 *         models with type-specific behavior.</li>
 *     <li><strong>Shopify (with B2B extensions / apps)</strong> — core uses {@code Customer}
 *         for B2C, while B2B is handled via company locations, customer tags/groups and
 *         custom logic — many headless implementations introduce an explicit account type
 *         to unify the model.</li>
 *     <li><strong>Commercetools / commercetools</strong> — relies heavily on {@code CustomerGroup}
 *         and custom fields/types to distinguish B2B vs B2C, but advanced projects often add
 *         a discriminator on the customer/account level for cleaner domain modeling.</li>
 * </ul>
 * <p>
 * Keeping the distinction as a simple enum rather than inheritance or many-to-many groups
 * provides good performance, clear intent in code, and easy extensibility (additional values
 * can be added later without schema changes if business needs evolve — e.g. {@code PARTNER},
 * {@code GOVERNMENT}, {@code GUEST}).
 * </p>
 */
public enum AccountType {
    /**
     * Represents an individual consumer / natural person (typical B2C scenario).
     */
    INDIVIDUAL,

    /**
     * Represents a company, organization or legal entity (typical B2B scenario).
     */
    COMPANY
}
