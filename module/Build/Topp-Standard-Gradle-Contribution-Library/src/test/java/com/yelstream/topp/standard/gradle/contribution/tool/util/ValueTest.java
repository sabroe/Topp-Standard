/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.gradle.contribution.tool.util;

import org.junit.jupiter.api.Assertions;

import java.util.Map;

/**
 * Tests {@link Configuration.Definition}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-11-24
 */
public class ValueTest {  //TO-DO: --> 'DefinitionTest' ?

    void usage() {
//        Map<String,Object> properties=Map.of("java.encoding","UTF-8");
        Map<String,String> properties=
            Map.of("java.encoding","UTF-8",
                   "java.encoding.compile","UTF-8",
                   "java.encoding.compile-test","UTF-8");

        Configuration.Definition<String> definition=
            Configuration.Definition.<String>builder().value(properties.get("java.encoding")).value("UTF-8").build();
        String value=definition.getValue();

        Assertions.assertEquals("UTF-8",value);
    }

    void usage2() {
//        Map<String,Object> properties=Map.of("java.encoding","UTF-8");
        Map<String,String> properties=
            Map.of("java.encoding","UTF-8",
                    "java.encoding.compile","UTF-8",
                    "java.encoding.compile-test","UTF-8");

        String value=Configuration.text().value(properties.get("java.encoding")).getValue("UTF-8");
        Assertions.assertEquals("UTF-8",value);

        String value2=Configuration.text().value(properties.get("java.encoding.compile")).getValue("UTF-8");
        Assertions.assertEquals("UTF-8",value2);
    }
}
