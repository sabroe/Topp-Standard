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
 * Topp Standard Logging SLF4J Provider offers service providers specific for SLF4J.
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-03-24
 */
module com.yelstream.topp.standard.logging.slf4j.provider {  //TODO: provider->spi ?
    requires static lombok;
    requires org.slf4j;
    requires com.yelstream.topp.standard.annotation.intention;
    requires com.yelstream.topp.standard.logging.slf4j.base;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.event.bind;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.factory;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.proxy;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.router;
    exports com.yelstream.topp.standard.logging.slf4j.spi.marker.factory;
    exports com.yelstream.topp.standard.logging.slf4j.spi.mdc;
    exports com.yelstream.topp.standard.logging.slf4j.spi.provider;
}
