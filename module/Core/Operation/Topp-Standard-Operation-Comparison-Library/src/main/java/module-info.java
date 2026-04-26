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
 * Comparison utilities.
 * <p>
 *     This module provides a fluent and policy-driven approach to building, composing, and executing
 *     {@link java.util.Comparator} instances.
 *     It supports key-based comparison construction, null-handling policies, and reusable comparison facets.
 * </p>
 *
 * <p>
 * The main building blocks are:
 * </p>
 * <ul>
 *     <li>{@code Comparing} – fluent entry point for comparator construction.</li>
 *     <li>{@code ComparatorBuilder} – fluent comparator assembly.</li>
 *     <li>{@code Comparators} – static utility operations on comparators.</li>
 *     <li>{@code ComparatorFacet} – runtime comparison operations.</li>
 *     <li>{@code NullPolicy} – strategy for null-handling behavior.</li>
 * </ul>
 *
 * <h2>Example usage</h2>
 *
 * <p>
 * Basic comparator construction using {@code Comparing}:
 * </p>
 *
 * <pre>{@code
 * Comparator<Person> byName =
 *     Comparing.by(Person::name)
 *         .build();
 * }</pre>
 *
 * <p>
 * Multi-level comparison with chaining:
 * </p>
 *
 * <pre>{@code
 * Comparator<Person> byNameThenAge =
 *     Comparing.by(Person::name)
 *         .thenComparing(Person::age)
 *         .build();
 * }</pre>
 *
 * <p>
 * Full comparator with null-handling policy:
 * </p>
 *
 * <pre>{@code
 * Comparator<Person> comparator =
 *     Comparing.by(Person::name)
 *         .thenComparing(Person::age)
 *         .thenComparing(Person::score)
 *         .nullsLast()
 *         .build();
 * }</pre>
 *
 * <p>
 * Equivalent usage with explicit policy application:
 * </p>
 *
 * <pre>{@code
 * Comparator<Person> comparator =
 *     Comparators.applyNullPolicy(
 *         Comparator.comparing(Person::name),
 *         StandardNullPolicy.NullsFirst.getPolicy()
 *     );
 * }</pre>
 *
 * <p>
 * Example domain model:
 * </p>
 *
 * <pre>{@code
 * public record Person(String name, int age, double score) {}
 * }</pre>
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-04-21
 */
module com.yelstream.topp.standard.operation.comparison {
    requires static lombok;
    requires org.slf4j;
    exports com.yelstream.topp.standard.operation.comparison;
    exports com.yelstream.topp.standard.operation.comparison.build;
    exports com.yelstream.topp.standard.operation.comparison.facet;
    exports com.yelstream.topp.standard.operation.comparison.policy;
}
