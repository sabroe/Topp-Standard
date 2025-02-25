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
 * Topp Standard Logging Assistance For SLF4J provides utilities specific for SLF4J.
 *
 * @author Morten Sabroe Mortensen
 * @since 2024-06-18
 */
module com.yelstream.topp.standard.log.assistance.slf4j {
    requires static lombok;
    requires org.slf4j;
    requires com.yelstream.topp.standard.core;
    requires com.yelstream.topp.standard.annotator;
    exports com.yelstream.topp.standard.log.assist.slf4j.event;
    exports com.yelstream.topp.standard.log.assist.slf4j.logger;
    exports com.yelstream.topp.standard.log.assist.slf4j.scribe;
    exports com.yelstream.topp.standard.log.assist.slf4j.scribe.factory;
    exports com.yelstream.topp.standard.log.assist.slf4j.spi;
    exports com.yelstream.topp.standard.log.assist.slf4j.spi.ex;
}
