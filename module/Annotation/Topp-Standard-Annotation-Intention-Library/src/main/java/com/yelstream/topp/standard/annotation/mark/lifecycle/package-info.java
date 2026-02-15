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
 * Maturity and lifecycle stage annotations for indicating the stability,
 * production-readiness, and expected change behavior of modules, packages,
 * types, methods, and other elements.
 * <p>
 *   These annotations communicate how mature and stable a component is,
 *   helping consumers decide how confidently they can depend on it and
 *   what level of risk/change they should anticipate.
 * </p>
 * <p>
 *     Stages:
 * </p>
 * <ul>
 *   <li>
 *     {@link com.yelstream.topp.standard.annotation.mark.lifecycle.Provisional @Provisional}:
 *     Released but provisional / evolving.
 *     </br>
 *     Breaking changes or refinements are still possible.
 *     Use with caution; expect potential adjustments.</li>
 *   <li>
 *     {@link com.yelstream.topp.standard.annotation.mark.lifecycle.Stabilized @Stabilized}:
 *     Mostly frozen / settling.
 *     </br>
 *     Only non-breaking changes, bug fixes, or minor improvements expected.
 *   </li>
 *   <li>
 *     {@link com.yelstream.topp.standard.annotation.mark.lifecycle.Published @Published}:
 *     Fully committed and production-grade.
 *     </br>
 *     Follows semantic versioning; breaking changes only on major version bumps with proper deprecation periods.
 *   </li>
 * </ul>
 * <p>
 *     Recommended usage:
 * </p>
 * <ul>
 *   <li>
 *     Apply primarily at package level (in {@code package-info.java})
 *     to describe the maturity of an entire API surface or module.
 *   </li>
 *   <li>
 *     Use on individual types/methods when maturity varies within a package.
 *   </li>
 *   <li>
 *     Progress should be one-way: never downgrade a stage without very strong justification.
 *   </li>
 *   <li>
 *     Consumers should apply extra scrutiny and migration planning to anything marked {@code @Provisional}.</li>
 * </ul>
 */
@Provisional
package com.yelstream.topp.standard.annotation.mark.lifecycle;
