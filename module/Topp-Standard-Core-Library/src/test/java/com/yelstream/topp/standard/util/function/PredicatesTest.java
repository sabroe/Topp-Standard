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

package com.yelstream.topp.standard.util.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Test of {@link Predicates}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-05-07
 */
@Slf4j
class PredicatesTest {

    /**
     * Test of {@link Predicates#equals(String)}.
     */
    @Test
    void equals() {
        Assertions.assertThrows(IllegalArgumentException.class,()->Predicates.equals(null));

        Predicate<String> p=Predicates.equals("xxx");
        Assertions.assertNotNull(p);
        Assertions.assertFalse(p.test(null));
        Assertions.assertFalse(p.test("yyy"));
        Assertions.assertTrue(p.test("xxx"));
    }

    /**
     * Test of {@link Predicates#equalsIgnoreCase(String)}.
     */
    @Test
    void equalsIgnoreCase() {
        Assertions.assertThrows(IllegalArgumentException.class,()->Predicates.equalsIgnoreCase(null));

        Predicate<String> p=Predicates.equalsIgnoreCase("xxx");
        Assertions.assertNotNull(p);
        Assertions.assertFalse(p.test(null));
        Assertions.assertFalse(p.test("yyy"));
        Assertions.assertTrue(p.test("xXx"));
        Assertions.assertTrue(p.test("XxX"));
    }

    /**
     * Test of {@link Predicates#contains(String)}.
     */
    @Test
    void contains() {
        Assertions.assertThrows(IllegalArgumentException.class,()->Predicates.contains(null));

        Predicate<String> p=Predicates.contains("def");
        Assertions.assertNotNull(p);
        Assertions.assertFalse(p.test(null));
        Assertions.assertFalse(p.test("xxx"));
        Assertions.assertTrue(p.test("abcdefghijklmnopqrstuvwxyz"));
    }

    /**
     * Test of {@link Predicates#matches(String)}.
     */
    @Test
    void matchesRegEx() {
        Assertions.assertThrows(IllegalArgumentException.class,()->Predicates.matches((String)null));

        Predicate<String> p=Predicates.matches("(.+)(def)([a-z]+)");
        Assertions.assertNotNull(p);
        Assertions.assertFalse(p.test(null));
        Assertions.assertFalse(p.test("xxx"));
        Assertions.assertTrue(p.test("abcdefghijklmnopqrstuvwxyz"));
    }

    /**
     * Test of {@link Predicates#matches(Pattern)}.
     */
    @Test
    void matchesPattern() {
        Assertions.assertThrows(IllegalArgumentException.class,()->Predicates.matches((Pattern)null));

        Predicate<String> p=Predicates.matches(Pattern.compile("(.+)(def)([a-z]+)"));
        Assertions.assertNotNull(p);
        Assertions.assertFalse(p.test(null));
        Assertions.assertFalse(p.test("xxx"));
        Assertions.assertTrue(p.test("abcdefghijklmnopqrstuvwxyz"));
    }
}
