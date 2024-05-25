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

package com.yelstream.topp.standard.time;

/**
 * Provides access to machine time.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-18
 */
@FunctionalInterface
public interface NanoTimeSource {
    /**
     * Gets the current time of the machine in nanoseconds.
     * @return Machine time in nanoseconds.
     */
    long nanoTime();

    /**
     * Gets the provider of machine time for the system.
     * @return Machine time for the system.
     */
    static NanoTimeSource system() {
        return System::nanoTime;
    }
}
