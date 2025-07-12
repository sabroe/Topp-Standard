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
 * Topp Standard Resource Library addressing basics of resource discovery and resource content access.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
module com.yelstream.topp.standard.resource {
    requires static lombok;
    requires org.slf4j;
    requires com.yelstream.topp.standard.load;
    uses com.yelstream.topp.standard.resource.provider.ResourceProvider;
    exports com.yelstream.topp.standard.collect.let.in;
    exports com.yelstream.topp.standard.collect.let.out;
    exports com.yelstream.topp.standard.collect.let.in.factory;
    exports com.yelstream.topp.standard.collect.let.out.factory;
    exports com.yelstream.topp.standard.io.dual.source;
    exports com.yelstream.topp.standard.io.dual.target;
    exports com.yelstream.topp.standard.load.clazz.scan;
    exports com.yelstream.topp.standard.load.clazz.scan.factory;
    exports com.yelstream.topp.standard.load.clazz.scan.impl;
    exports com.yelstream.topp.standard.net.resource.identification;
    exports com.yelstream.topp.standard.net.resource.location;
    exports com.yelstream.topp.standard.net.resource.location.handler;
    exports com.yelstream.topp.standard.net.resource.location.handler.factory;
    exports com.yelstream.topp.standard.resource;
    exports com.yelstream.topp.standard.resource.clazz;
    exports com.yelstream.topp.standard.resource.item;
    exports com.yelstream.topp.standard.resource.index;
    exports com.yelstream.topp.standard.resource.name;
    exports com.yelstream.topp.standard.resource.provider;
    exports com.yelstream.topp.standard.resource.resolve;
    exports com.yelstream.topp.standard.resource.clazz.load;
    exports com.yelstream.topp.standard.resource.net.name;
}
