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

package com.yelstream.topp.standard.operation.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubjectTest {

    @Test
    void x1() {
        Subject<Integer> s1=
            Subject.of("xxx")
                .type()
                .as(String.class)
                .map()
                .to(String::length);
        Assertions.assertNotNull(s1);
        Assertions.assertEquals(Subject.of(3),s1);

        Subject<String> s2=
            Subject.of("yyy")
                .type()
                .as(String.class)
                .nulls()
                .or("fallback");
        Assertions.assertNotNull(s2);
        Assertions.assertEquals(Subject.of("yyy"),s2);

        Subject<Integer> s3=
            Subject.of("zzz")
                .type()
                .as(String.class)
                .map()
                .to(String::length)
                .nulls()
                .or(0);
        Assertions.assertNotNull(s3);
        Assertions.assertEquals(Subject.of(3),s3);
    }

}
