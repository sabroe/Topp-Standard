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
 * Classifies the intended purpose or role of an {@link Address} within the system.
 * <p>
 * This enum helps distinguish between different contexts in which the same physical/postal address
 * might be used — for example, as the default shipping location for a company, the billing address
 * for invoices, or a temporary delivery point entered during checkout.
 * </p>
 * <p>
 * Using a typed classification allows:
 * <ul>
 *     <li>Automatic selection of defaults during checkout (e.g. pick the SHIPPING default when available)</li>
 *     <li>Validation rules per type (billing addresses often require more strict formatting or tax-region data)</li>
 *     <li>User interface hints (show "Use as default billing address" checkbox only for BILLING types)</li>
 *     <li>Reporting and analytics (e.g. count how many orders use a non-default shipping address)</li>
 * </ul>
 * </p>
 * <p>
 * The set is kept intentionally small and generic so it remains useful across B2C and B2B scenarios.
 * More specialized use-cases (warehouse address, pickup point, registered office, etc.) can be handled
 * via custom fields, tags, or a separate {@code AddressPurpose} entity in the future if needed.
 * </p>
 */
public enum AddressType {

    /**
     * Address primarily intended for sending physical goods (delivery, shipping, fulfillment).
     * Most commonly used as the shipping address on orders and the default shipping option
     * in an account's address book.
     */
    SHIPPING,

    /**
     * Address used for invoicing, legal correspondence, tax reporting and payment processing.
     * Often required to match the VAT-registered or legal entity address in B2B scenarios.
     */
    BILLING,

    /**
     * Address used during account registration or for official/legal identity verification
     * (e.g. registered office, home address of an individual in strict KYC flows).
     * Rarely used directly on orders but may influence tax or compliance rules.
     */
    REGISTRATION,

    /**
     * Catch-all for any other purpose not covered by the main types (e.g. pickup location,
     * alternative delivery, branch office, vacation address).
     * Applications can decide whether to allow this type in checkout or treat it as read-only.
     */
    OTHER
}
