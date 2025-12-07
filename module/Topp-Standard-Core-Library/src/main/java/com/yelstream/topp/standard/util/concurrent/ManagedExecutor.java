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

package com.yelstream.topp.standard.util.concurrent;

import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

/**
 * An executor with a managed lifecycle.
 * <p>
 *     This addresses various scenarios where an executor might need to be closed or retained based on specific requirements.
 * </p>
 * <ul>
 *     <li>
 *         Although Java SE 19 extended {@link ExecutorService} to be {@link AutoCloseable},
 *         managing a plain {@link Executor} without close functionality remains challenging.
 *     </li>
 *     <li>
 *         There are cases where an executor needs to be disposed, and cases where it must be retained.
 *     </li>
 *     <li>
 *         Some applications, such as reactive programming with {@link CompletableFuture#supplyAsync(Supplier, Executor)},
 *         might require an {@link Executor} but benefit from an {@link ExecutorService} lifecycle management.
 *     </li>
 * </ul>
 * <p>
 *     The executor's lifecycle is managed externally while ensuring consistent closure internally.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-08
 */
public interface ManagedExecutor extends Executor, AutoCloseable {  //TO-DO: Consider moving to 'Furnace' project!
    @Override
    void close();

    /**
     * Creates a managed executor.
     * @param executor The executor.
     * @return A managed executor.
     */
    static ManagedExecutor of(Executor executor) {
        if (executor instanceof ManagedExecutor managedExecutor) {
            return managedExecutor;
        } else {
            return NoOpManagedExecutor.of(executor);
        }
    }

    /**
     * Creates a managed executor from an ExecutorService.
     * @param executorService The executor service.
     * @return A managed executor.
     */
    static ManagedExecutor of(ExecutorService executorService) {
        return ExecutorServiceManagedExecutor.of(executorService);
    }

    /**
     * Creates a no-op managed executor.
     * @param executor The executor.
     * @return A no-op managed executor.
     */
    static ManagedExecutor nop(Executor executor) {
        return NoOpManagedExecutor.of(executor);
    }

    /**
     * Creates an auto-closeable managed executor.
     * @param executor The executor.
     * @return An auto-closeable managed executor.
     */
    static ManagedExecutor auto(Executor executor) {
        if (executor instanceof ManagedExecutor managedExecutor) {
            return managedExecutor;
        } else {
            return AutoManagedExecutor.of(executor);
        }
    }

    /**
     * Managed executor with no-op close.
     */
    @AllArgsConstructor(staticName="of")
    class NoOpManagedExecutor implements ManagedExecutor {
        /**
         * Executor-service.
         * <p>
         *     This will not be closed.
         * </p>
         */
        private final Executor executor;

        @SuppressWarnings("NullableProblems")
        @Override
        public void execute(Runnable command) {
            executor.execute(command);
        }

        @Override
        public void close() {
            //Ignore; action is a no-op for the plain executor!
        }
    }

    /**
     * Managed executor service that auto-closes.
     */
    @AllArgsConstructor(staticName="of")
    class ExecutorServiceManagedExecutor implements ManagedExecutor {
        /**
         * Executor-service.
         * <p>
         *     This will be closed.
         * </p>
         */
        private final ExecutorService executorService;

        @SuppressWarnings("NullableProblems")
        @Override
        public void execute(Runnable command) {
            executorService.execute(command);
        }

        @Override
        public void close() {
            executorService.close();
        }
    }

    /**
     * Managed executor that auto-closes if it implements {@link AutoCloseable}.
     */
    @AllArgsConstructor(staticName="of")
    class AutoManagedExecutor implements ManagedExecutor {
        /**
         * Executor.
         * <p>
         *     If this is auto-closable then it will be closed.
         * </p>
         */
        private final Executor executor;

        @SuppressWarnings("NullableProblems")
        @Override
        public void execute(Runnable command) {
            executor.execute(command);
        }

        @Override
        public void close() {
            if (executor instanceof AutoCloseable autoCloseable) {
                try {
                    autoCloseable.close();
                } catch (Exception ex) {
                    throw new IllegalStateException("Failure to close!",ex);
                }
            }
        }
    }
}
