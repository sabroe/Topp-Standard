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

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.event.LoggingEvent;
import org.slf4j.spi.LoggingEventAware;

@AllArgsConstructor(staticName = "of")
public class FluentEventBinding implements EventBinding {
    @Override
    public boolean log(LoggingEvent event,
                       Logger logger) {
        if (logger instanceof LoggingEventAware lea) {
            lea.log(event);
            return true;
        }
        return false;
    }
}
