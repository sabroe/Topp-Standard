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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.event.bind;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.event.LoggingEvent;

@NoArgsConstructor(staticName = "of")
public class DefaultEventBinding implements EventBinding {
    /**
     *
     */
    private final FluentEventBinding fluentEventDelegate= FluentEventBinding.of();

    /**
     *
     */
    private final FallbackEventBinding fallbackEventDelegate= FallbackEventBinding.of();

    @Override
    public boolean log(LoggingEvent event,
                       Logger logger) {
        boolean handled=fluentEventDelegate.log(event,logger);
        if (!handled) {
            fallbackEventDelegate.log(event,logger);
        }
        return handled;
    }
}
