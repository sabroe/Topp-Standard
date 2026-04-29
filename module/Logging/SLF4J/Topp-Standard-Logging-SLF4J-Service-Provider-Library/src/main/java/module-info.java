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
 * Topp Standard Logging SLF4J Service Provider offers service provider functionality specific for SLF4J.
 *
 * @author Morten Sabroe Mortensen
 * @since 2026-03-24
 */
module com.yelstream.topp.standard.logging.slf4j.provider {  //TODO: provider->spi ?
    requires static lombok;
    requires org.slf4j;
    requires com.yelstream.topp.standard.annotation.intention;
    requires com.yelstream.topp.standard.logging.slf4j.base;
    requires org.slf4j.simple;
//    requires com.yelstream.topp.standard.core;
    requires com.yelstream.topp.standard.operation.reflection;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.enable;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.event.bind;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.factory;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.proxy;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.route;
    exports com.yelstream.topp.standard.logging.slf4j.spi.marker.factory;
    exports com.yelstream.topp.standard.logging.slf4j.spi.mdc;
    exports com.yelstream.topp.standard.logging.slf4j.spi.provider;
    exports com.yelstream.topp.standard.logging.slf4j.spi.event.builder;
    exports com.yelstream.topp.standard.logging.slf4j.spi.version;
    exports com.yelstream.topp.standard.logging.slf4j.spi.message;
    exports com.yelstream.topp.standard.logging.slf4j.spi.logger.helpers;
    exports com.yelstream.topp.standard.logging.slf4j.spi.event;
}
