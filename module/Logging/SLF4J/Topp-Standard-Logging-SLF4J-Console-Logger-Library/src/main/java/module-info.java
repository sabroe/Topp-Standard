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
 * Topp Standard Logging SLF4J Console Logger offers a basic SLF4J console logger as a service-provider.
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-04-01
 */
module com.yelstream.topp.standard.logging.slf4j.spi.service.console {
    requires static lombok;
    requires org.slf4j;
    requires org.slf4j.simple;
    requires com.yelstream.topp.standard.annotation.intention;
    requires com.yelstream.topp.standard.logging.slf4j.base;
    requires com.yelstream.topp.standard.logging.slf4j.provider;
    exports com.yelstream.topp.standard.logging.slf4j.spi.service.console;
    provides org.slf4j.spi.SLF4JServiceProvider with com.yelstream.topp.standard.logging.slf4j.spi.service.console.ConsoleSLF4JServiceProvider;
}
