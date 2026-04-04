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

package com.yelstream.topp.standard.logging.slf4j.event;

import lombok.experimental.UtilityClass;
import org.slf4j.event.Level;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility addressing instances of {@link Level}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.1
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
     *         <p>
     *             This is immutable.
     *         </p>
     */
    public static List<Level> getLevelsSorted() {
        return SORTED_LEVELS;
    }

    @FunctionalInterface
    public interface Enabler {
        void apply(boolean traceEnabled,
                   boolean debugEnabled,
                   boolean infoEnabled,
                   boolean warnEnabled,
                   boolean errorEnabled);
    }

    public static void enable(Level level,
                              Enabler enabler) {
        enabler.apply(isLevelEnabled(Level.TRACE,level),
                      isLevelEnabled(Level.DEBUG,level),
                      isLevelEnabled(Level.INFO,level),
                      isLevelEnabled(Level.WARN,level),
                      isLevelEnabled(Level.ERROR,level));
    }

    /**
     * Indicates, if a level is enabled.
     * @param level Level tested.
     * @param actualLevel Level actually set and applied.
     * @return Indicates, if the level is enabled.
     */
    public static boolean isLevelEnabled(Level level,
                                         Level actualLevel) {
        return level.toInt()>=actualLevel.toInt();
    }
}
