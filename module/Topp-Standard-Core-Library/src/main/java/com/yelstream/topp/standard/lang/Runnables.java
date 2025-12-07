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

package com.yelstream.topp.standard.lang;

import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@link Runnable}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-03-28
 */
@UtilityClass
public class Runnables {
    /**
     * Creates a runnable by converting an auto-closable.
     * @param closeable Auto-closable.
     * @return Created runnable.
     */
    public static Runnable of(AutoCloseable closeable) {
        return () -> {
            try {
                closeable.close();
            } catch (Exception ex) {
                throw new IllegalStateException("Failure to close auto-closeable!",ex);
            }
        };
    }

    /**
     * Runs a runnable.
     * @param runnable Runnable.
     */
    public static void run(Runnable runnable) {
        if (runnable!=null) {
            runnable.run();
        }
    }
}
