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

package com.yelstream.topp.standard.log.resist.slf4j;

import lombok.experimental.UtilityClass;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-17
 */
@UtilityClass
public final class Log {


    public static <B extends LoggingEventBuilder> LogAnvil<B> of(B loggingEventBuilder) {
        return DefaultLogAnvil.of(loggingEventBuilder);
    }

    public static LogAnvil<LoggingEventBuilder> nop() {
        return of(NOPLoggingEventBuilder.singleton());
    }

/*
    public static LogAnvil<LoggingEventBuilder> nop(BooleanSupplier predicate) {
        return predicate.getAsBoolean()?nop():of(NOPLoggingEventBuilder.singleton());
    }
*/
}
