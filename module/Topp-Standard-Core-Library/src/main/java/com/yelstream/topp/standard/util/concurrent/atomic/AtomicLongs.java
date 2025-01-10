/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.util.concurrent.atomic;

import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility addressing instances of {@link AtomicLong}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-04
 */
@UtilityClass
public class AtomicLongs {
    /**
     * Updates a holder of a minimum value.
     * @param minValue Holder of minimum value.
     * @param currentValue Current value.
     * @return Resulting minimum value.
     */
    public static long updateMin(AtomicLong minValue,
                                 long currentValue) {
        return minValue.updateAndGet(currentMin->Math.min(currentMin,currentValue));
    }

    /**
     * Updates a holder of a minimum value.
     * @param minValue Holder of minimum value.
     * @param currentValue Current value.
     * @return Resulting minimum value.
     */
    public static long updateMin(AtomicLong minValue,
                                 AtomicLong currentValue) {
        return updateMin(minValue,currentValue.get());
    }

    /**
     * Updates a holder of a maximum value.
     * @param maxValue Holder of maximum value.
     * @param currentValue Current value.
     * @return Resulting maximum value.
     */
    public static long updateMax(AtomicLong maxValue,
                                 long currentValue) {
        return maxValue.updateAndGet(currentMax->Math.max(currentMax,currentValue));
    }

    /**
     * Updates a holder of a maximum value.
     * @param maxValue Holder of maximum value.
     * @param currentValue Current value.
     * @return Resulting maximum value.
     */
    public static long updateMax(AtomicLong maxValue,
                                 AtomicLong currentValue) {
        return updateMax(maxValue,currentValue.get());
    }
}
