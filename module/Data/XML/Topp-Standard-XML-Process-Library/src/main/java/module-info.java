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
 * Topp Standard XML Process Library addressing basics of core XML processing.
 * <p>
 *     Note:
 *         Focuses on usages of
 *         {@link org.w3c.dom.ls},
 *         {@link org.xml.sax},
 *         {@link javax.xml.parsers},
 *         {@link javax.xml.transform}, and
 *         {@link javax.xml.validation}.
 *         Explicitly excludes {@link javax.xml.stream} (StAX).
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2024-06-18
 */
module com.yelstream.topp.standard.xml.process {
    uses com.yelstream.topp.standard.xml.catalog.provider.CatalogProvider;
    requires static lombok;
    requires org.slf4j;
    requires transitive java.xml;
    exports com.yelstream.topp.standard.dom.ls;
    exports com.yelstream.topp.standard.xml.catalog;
    exports com.yelstream.topp.standard.xml.catalog.provider;
    exports com.yelstream.topp.standard.xml.namespace;
    exports com.yelstream.topp.standard.xml.parsers;
    exports com.yelstream.topp.standard.xml.sax;
    exports com.yelstream.topp.standard.xml.transform;
    exports com.yelstream.topp.standard.xml.schema.provider;
    exports com.yelstream.topp.standard.xml.validation;
    exports com.yelstream.topp.standard.xml.catalog.provider.util;
}
