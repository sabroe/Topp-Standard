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

package com.yelstream.topp.standard.logging.slf4j.spi.message;

import org.slf4j.event.LoggingEvent;

/**
 * Renders the final, formatted message to be logged.
 * <p>
 *     This merges the message-pattern with the arguments.
 *     It does not format a full log-entry with, say, key-pair values, exceptions, markers, any
 *     context beyond the message-pattern and its arguments etc.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-04
 */
@FunctionalInterface
public interface MessageRenderer {

    String render(LoggingEvent event);

}
