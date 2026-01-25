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

/**
 * Represents the current lifecycle state of an {@link Invoice}.
 * <p>
 * This enum defines a simple, linear state machine for invoice processing that covers both B2C (simple consumer billing)
 * and B2B (more complex credit terms, partial payments, reminders, and credit notes). The states are kept coarse-grained
 * to remain meaningful across different billing models (one-time orders, subscriptions, milestone billing, service contracts).
 * </p>
 * <p>
 * Typical state transitions (most common happy path):
 * <pre>
 * DRAFT → ISSUED → PARTIALLY_PAID → PAID
 *                     ↳ OVERDUE (if due date passed without full payment)
 *                     ↳ CANCELLED (before payment, e.g. order cancelled)
 *                     ↳ CREDITED (after credit note issuance)
 * </pre>
 * </p>
 * <p>
 * Business rules usually enforce:
 * <ul>
 *     <li>Forward-only movement in normal cases (except to CANCELLED or CREDITED)</li>
 *     <li>Payment-related actions only allowed in certain states (e.g. capture only after ISSUED)</li>
 *     <li>State changes trigger side effects (email reminders, accounting postings, credit limit updates)</li>
 *     <li>OVERDUE is often calculated dynamically (based on dueDate + current time), but can be stored explicitly for performance</li>
 * </ul>
 * </p>
 * <p>
 * This set covers ~95% of real-world invoice flows in mixed B2B/B2C systems. If your business requires more granularity
 * (e.g. SENT_TO_COLLECTION, DISPUTED, WRITTEN_OFF, PARTIALLY_CREDITED), consider adding those values or using sub-states/flags later.
 * </p>
 */
public enum InvoiceStatus {

    /**
     * Invoice has been created in the system but not yet sent to the customer
     * (e.g. still in draft, awaiting approval, data verification, or final calculation).
     */
    DRAFT,

    /**
     * Invoice has been finalized and issued/sent to the customer (email, portal, PDF, e-invoice).
     * This is the normal "open invoice" state where payment is expected.
     */
    ISSUED,

    /**
     * Partial payment has been received (e.g. customer paid 50% upfront, rest on delivery).
     * Full payment still outstanding.
     */
    PARTIALLY_PAID,

    /**
     * Full amount has been successfully paid / received (or credit terms accepted in B2B).
     * End of the normal payment lifecycle (unless credit notes occur later).
     */
    PAID,

    /**
     * Invoice is past due date and full payment has not been received.
     * Often triggers automated reminders, interest calculation, or escalation.
     */
    OVERDUE,

    /**
     * Invoice was cancelled before payment was completed
     * (e.g. order cancelled, duplicate created, error discovered).
     * Usually prevents further payment attempts.
     */
    CANCELLED,

    /**
     * A credit note (or multiple) has been issued against this invoice, fully or partially offsetting it.
     * Often used after returns, disputes, goodwill gestures, or pricing corrections.
     */
    CREDITED
}
