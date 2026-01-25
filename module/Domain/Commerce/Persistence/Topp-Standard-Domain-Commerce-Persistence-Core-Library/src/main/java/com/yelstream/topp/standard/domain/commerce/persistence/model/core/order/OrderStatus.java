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

package com.yelstream.topp.standard.domain.commerce.persistence.model.core.order;

/**
 * Represents the current lifecycle state of an {@link Order}.
 * <p>
 * This enum defines a simple, linear(ish) state machine for order processing that works
 * reasonably well for both B2C (fast fulfillment) and B2B (more complex approval/credit flows).
 * States are intentionally coarse-grained so they remain meaningful across different fulfillment
 * models (own warehouse, dropship, marketplace, subscription, digital goods, etc.).
 * </p>
 * <p>
 * Typical state transitions (most common happy path):
 * <pre>
 * NEW → PLACED → PAID → SHIPPED → DELIVERED
 *                     ↳ CANCELLED (at any point before DELIVERED)
 *                     ↳ RETURNED (after DELIVERED)
 * </pre>
 * </p>
 * <p>
 * Business rules usually enforce:
 * <ul>
 *     <li>Only move forward (except to CANCELLED or RETURNED)</li>
 *     <li>Certain actions only allowed in specific states (e.g. capture payment only in PLACED)</li>
 *     <li>Some states trigger side effects (email, inventory allocation, invoice creation)</li>
 * </ul>
 * </p>
 * <p>
 * This set covers ~90% of real-world order flows. If your business requires more granularity
 * (e.g. PARTIALLY_SHIPPED, AWAITING_APPROVAL, IN_PRODUCTION, READY_FOR_PICKUP),
 * consider moving to a separate {@code OrderState} entity or adding sub-states/flags later.
 * </p>
 */
public enum OrderStatus {

    /**
     * Order has been created in the system but not yet confirmed / placed by the customer
     * (e.g. still in checkout, payment pending, awaiting approval in B2B).
     */
    NEW,

    /**
     * Order has been confirmed and committed by the customer (checkout completed).
     * Payment may still be authorized but not captured; this is the normal "open order" state.
     */
    PLACED,

    /**
     * Payment has been successfully captured / received (or payment terms accepted in B2B).
     * Usually the trigger for starting fulfillment.
     */
    PAID,

    /**
     * Goods have been handed over to the carrier / shipped (or digital access granted).
     * May be partial in complex orders.
     */
    SHIPPED,

    /**
     * Customer has received / acknowledged receipt of the goods or service.
     * End of the normal fulfillment lifecycle (unless returns occur).
     */
    DELIVERED,

    /**
     * Order was cancelled before fulfillment completed (by customer, merchant, or system).
     * Usually prevents further state changes except possibly refund processing.
     */
    CANCELLED,

    /**
     * Customer has returned (some or all) items after delivery.
     * Often leads to refund, credit note, or replacement processes.
     */
    RETURNED
}
