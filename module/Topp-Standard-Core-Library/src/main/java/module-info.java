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

/**
 * Topp Standard Core addressing basics of Java SE.
 */
module com.yelstream.topp.standard.core {
    requires static lombok;
    requires org.slf4j;
//    requires jdk.net;
//    requires jdk.sctp;
    exports com.yelstream.topp.standard.lang;
    exports com.yelstream.topp.standard.lang.reflect;
    exports com.yelstream.topp.standard.lang.thread;
    exports com.yelstream.topp.standard.io;
    exports com.yelstream.topp.standard.net;
    exports com.yelstream.topp.standard.time;
    exports com.yelstream.topp.standard.time.watch;
    exports com.yelstream.topp.standard.util;
    exports com.yelstream.topp.standard.util.concurrent;
    exports com.yelstream.topp.standard.util.concurrent.atomic;
    exports com.yelstream.topp.standard.util.function;
    exports com.yelstream.topp.standard.util.regex;
    exports com.yelstream.topp.standard.util.stream;
}
