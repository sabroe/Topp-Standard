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

package com.yelstream.topp.standard.log.assist.slf4j.event;

import lombok.experimental.UtilityClass;
import org.slf4j.event.Level;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility addressing instances of {@link Level}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-18
 */
@UtilityClass
public class Levels {
    /**
     * All levels sorted.
     * <p>
     *     { TRACE=00, DEBUG=10, INFO=20, WARN=30, ERROR=40 }
     * </p>
     */
    private static final List<Level> SORTED_LEVELS=
        Stream.of(Level.values()).sorted(Comparator.comparingInt(Level::toInt)).toList();  //Yes, this is unmodifiable!

    /**
     * Gets all levels in sorted order.
     * @return Levels in sorted order.
     *         From {@link Level#TRACE} to {@link Level#ERROR}.
     *         This is immutable.
     */
    public static List<Level> getLevelsSorted() {
        return SORTED_LEVELS;
    }
}
