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

package com.yelstream.topp.standard.stream.gatherer;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Gatherer;

/**
 * Utilities addressing instances of {@link java.util.stream.Gatherer}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-10-07
 */
@Slf4j
@UtilityClass
public class Gatherers {
    /**
     *
     */
    public static <T> Gatherer<T, List<T>, List<T>> createPredicateGatherer(Predicate<T> predicate) {
        return Gatherer.of(
                ArrayList::new, // Initializer: Empty list
                (list, element, downstream) -> { // Integrator: Add if predicate passes
                    if (predicate.test(element)) {
                        list.add(element);
                    }
                    return true; // Continue processing
                },
                (left, right) -> { // Combiner: Merge lists
                    left.addAll(right);
                    return left;
                },
                (list, downstream) -> { // Finisher: Push collected list
                    downstream.push(list);
                }
        );
    }

    public static void main(String[] args) {
        log.info("Hello, Gatherers!");

        List<String> input = List.of("a", "abc", "abcd", "abcdef");

        Gatherer<String, ?, List<String>> gatherer = createPredicateGatherer(s -> s.length() > 3);

        List<List<String>> result = input.stream().gather(gatherer).toList();
        System.out.println(result); // Output: [[abcd, abcdef]]
    }
}
