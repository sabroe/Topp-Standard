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
 * Topp Standard XML Library for simplified XML reading, writing, validation, and binding.
 *
 * @author Morten Sabroe Mortensen
 * @since 2024-06-18
 */
module com.yelstream.topp.standard.xml {
    requires static lombok;
    requires org.slf4j;
    requires transitive java.xml;
    requires transitive jakarta.xml.bind;
    requires transitive com.yelstream.topp.standard.xml.process;
    requires transitive com.yelstream.topp.standard.xml.bind;
    requires transitive com.yelstream.topp.standard.xml.stream;
    exports com.yelstream.topp.standard.xml.io;
    exports com.yelstream.topp.standard.xml.util;
}
