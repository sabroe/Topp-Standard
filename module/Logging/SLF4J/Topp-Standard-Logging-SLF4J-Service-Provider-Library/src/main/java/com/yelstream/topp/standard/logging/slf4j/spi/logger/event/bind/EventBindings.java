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

import lombok.experimental.UtilityClass;

/**
 * Utilities for instances of {@link EventBinding}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-29
 */
@UtilityClass
public class EventBindings {

    public static final EventBinding DEFAULT_EVENT_BINDING=DefaultEventBinding.of();

    public static final EventBinding FLUENT_EVENT_BINDING=FluentEventBinding.of();

    public static final EventBinding FALLBACK_EVENT_BINDING=FallbackEventBinding.of();

    public static final EventBinding NOP_EVENT_BINDING=EmptyEventBinding.of();


}
