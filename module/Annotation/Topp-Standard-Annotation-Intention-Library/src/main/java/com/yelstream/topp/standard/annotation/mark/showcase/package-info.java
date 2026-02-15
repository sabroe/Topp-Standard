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

/**
 * Annotations for marking code whose primary purpose is demonstration,
 * illustration, example, or educational/showcase usage rather than
 * production deployment.
 * <p>
 *   These markers help distinguish illustrative or learning-oriented code from
 *   code intended for real-world, production, or shared library usage.
 *   They signal that consumers should expect simplifications, hard-coded values,
 *   incomplete error handling, demo-specific security, or other non-production characteristics.
 * </p>
 * <p>
 *   Markers:
 * </p>
 * <ul>
 *   <li>
 *     {@link com.yelstream.topp.standard.annotation.mark.showcase.Demonstration @Demonstration}:
 *     For runnable or near-complete demonstration applications,
 *     modules, packages, or components that show end-to-end usage,
 *     integration, or realistic scenarios (quickstarts, feature showcases,
 *     proof-of-concepts).
 *   </li>
 *   <li>
 *     {@link com.yelstream.topp.standard.annotation.mark.showcase.Illustration @Illustration}
 *     For focused code snippets, classes, methods, or patterns that illustrate a specific technique,
 *     mechanism, idiom, or API usage purely for explanatory or reference purposes
 *     (often partial or non-runnable standalone).
*    </li>
 * </ul>
 * <p>
 *   Recommended usage:
 * </p>
 * <ul>
 *   <li>
 *     Use {@code @Demonstration} at package or module level for complete example applications or quickstarts.
 *   </li>
 *   <li>
 *     Use {@code @Illustration} on individual types, methods,
 *     or fields when highlighting a particular pattern or solution.
 *   </li>
 *   <li>
 *     Code carrying these annotations should <strong>not</strong> be
 *     deployed to production without careful review and adaptation.
 *   </li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-02-14
 */
@Provisional
package com.yelstream.topp.standard.annotation.mark.showcase;

import com.yelstream.topp.standard.annotation.mark.lifecycle.Provisional;
