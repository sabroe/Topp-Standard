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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume;

import org.slf4j.event.LoggingEvent;

/**
 * Logs a SLF4J fluent API logging event to a logger.
 * <p>
 *     In essence, this is the action to be taken by {@link org.slf4j.spi.LoggingEventAware#log(LoggingEvent)}.
 * </p>
 * <p>
 *     Depending upon how this is build,
 *     this may capture copies of a logging event,
 *     it may transform a logging event and in the rare case this may be needed,
 *     and it should in all normal cases implement the default action to be taken.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-26
 */
@FunctionalInterface
public interface EventConsumer {
    /**
     * Logs an event.
     * @param event Event to delegate.
     */
    void log(LoggingEvent event);
}
