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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.spi.LoggingEventBuilder;

@AllArgsConstructor(staticName="of",access= AccessLevel.PACKAGE)
public final class DefaultFilter<B extends LoggingEventBuilder> implements Filter {

    /**
     *
     */
    private final DefaultEntry<B> entry;  //TO-DO: Consider exposing LESS and in non-circular fashion DefaultFilter<->DefaultEntry -- the fixed context and the item being replaceable!

    @Override
    public Filter doSomething() {
        //TO-DO: Do something e.g. replace the item, add to the context.
        return this;
    }

}
