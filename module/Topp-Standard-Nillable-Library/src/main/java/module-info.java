/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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
 * Enables three-state nullability, distinguishing {@code null} (unset),
 * {@code nill} (explicit null), and {@code present} (actual value) for precise data transfer and action mapping.
 * <p>
 *   Inspired by XML Schema's {@code nillable}, it supports custom nil values (e.g., {@code new Date(0)},
 *   {@code BigDecimal.ZERO}) and integrates with JAXB and Jackson for XML/JSON serialization.
 *   Ideal for APIs, financial systems, and XML-based workflows.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-05-24
 */
module com.yelstream.topp.standard.nil {
    requires static lombok;
    requires org.slf4j;
    requires jakarta.xml.bind;
    requires com.fasterxml.jackson.databind;
    exports com.yelstream.topp.standard.nil;
    exports com.yelstream.topp.standard.nil.util;
    exports com.yelstream.topp.standard.nil.action;
}
