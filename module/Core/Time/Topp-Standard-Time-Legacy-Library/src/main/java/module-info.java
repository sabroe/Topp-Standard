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
 * Adapts legacy time/date functionalities to the current {@link java.time} API.
 * <p>
 *     While many applications continue to depend upon {@link java.util.Date},
 *     {@link java.util.Calendar} and {@link java.text.SimpleDateFormat},
 *     this may help to adapt to a solid, stable foundation based on Java SE 8 Date-and-Time functionalities.
 * </p>
 * <p>
 *     Boundary adaptation of legacy dates {@link com.yelstream.topp.standard.time.legacy.util.Dates},
 *     while otherwise keeping all internal date-and-time handling on a solid foundation,
 *     using this module as a bridge between two worlds,
 *     may be of key value.
 * </p>
 * <p>
 *     About this module:
 * </p>
 * <ul>
 *     <li>
 *         Available as a Maven artifact on the
 *         <a href="https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-time-legacy"
 *         >Maven Central Repository</a>!
 *     </li>
 *     <li>
 *         Available API documentation is present on the
 *         <a href="https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-time-legacy"
 *         >Maven JavaDoc site</a>!
 *     </li>
 * </ul>
 * <p>
 *     Related:
 * </p>
 * <ul>
 *     <li>
 *         <a href="https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-time"
 *         >Module {@code com.yelstream.topp.standard.time}</a>
 *     </li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @since 2024-06-18
 */
module com.yelstream.topp.standard.time.legacy {
    requires static lombok;
    requires org.slf4j;
    requires com.yelstream.topp.standard.time;
    exports com.yelstream.topp.standard.time.legacy.text;
    exports com.yelstream.topp.standard.time.legacy.util;
}
