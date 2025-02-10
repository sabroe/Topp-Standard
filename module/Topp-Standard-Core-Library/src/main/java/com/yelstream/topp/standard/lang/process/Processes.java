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

package com.yelstream.topp.standard.lang.process;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Utility addressing instances of {@link Process}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-10
 */
@UtilityClass
public class Processes {

    public static boolean waitFor(Process process,
                                  Duration duration) throws InterruptedException {
        return process.waitFor(duration.toMillis(),TimeUnit.MILLISECONDS);
    }

    @FunctionalInterface
    public interface Interaction {
        void apply(Process process) throws IOException;
    }

    @FunctionalInterface
    public interface Completion {
        CompletableFuture<Process> apply(Process process);
    }

    public static CompletableFuture<Process> interact(Process process,
                                                      Interaction interaction,
                                                      Completion completion) throws IOException {
        try {
            interaction.apply(process);

            return completion.apply(process);

//TO-DO: Fix this!

/*
            boolean b = false;
            try {
                b = waitFor(process, Duration.ofSeconds(5));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (!b) {
                throw new IllegalStateException("Timeout");
            }
*/
        } finally {
            if (process!=null) {
                process.destroy();
            }
        }
    }
}
