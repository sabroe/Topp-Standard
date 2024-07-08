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

package com.yelstream.topp.standard.util.concurrent;

import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * An executor mapped as an auto-closable.
 * <p>
 *     Note that this has a relevance since a number of things have happened:
 * </p>
 * <ol>
 *     <li>
 *         Sometime an executor needs to be disposed and sometimes it does it cannot be disposed.
 *     <li>
 *     </li>
 *         Sometimes we do not want an executor to be closed, and sometimes we want to ensure, that it is closed.
 *     </li>
 *     <li>
 *         Recently, from Java SE 19, the {@link ExecutorService} has become an {@link AutoCloseable},
 *         while {@link Executor} continues to be a very plain interface not being an auto-closable.
 *     </li>
 *     <li>
 *         We may well want an executor-service like {@link Executors#newVirtualThreadPerTaskExecutor()},
 *         but may only need the signature of an {@link Executor} for, say,
 *         doing reactive programming like invoking {@link CompletableFuture#supplyAsync(Supplier, Executor)}
 *         with an explicit executor of our choice.
 *     </li>
 * </ol>
 * <p>
 *     With this, it is possible to control the lifecycle from the outside of a usage,
 *     while inside consistently close the executor.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-07-08
 */
public interface DisposableExecutor extends Executor, AutoCloseable {  //TO-DO: Consider moving to 'Furnace' project!
    @Override
    void close();

    /**
     * Creates an auto-closable executor.
     * @param executor Executor.
     * @return Create auto-closable executor.
     */
    static DisposableExecutor of(Executor executor) {
        if (executor instanceof DisposableExecutor disposableExecutor) {
            return disposableExecutor;
        } else {
            return NoOpDisposableExecutor.of(executor);
        }
    }

    /**
     * Creates an auto-closable executor.
     * @param executorService Executor-service.
     * @return Create auto-closable executor.
     */
    static DisposableExecutor of(ExecutorService executorService) {
        return ExecutorServiceDisposableExecutor.of(executorService);
    }

    /**
     * Creates an auto-closable executor.
     * @param executor Executor.
     * @return Create auto-closable executor.
     */
    static DisposableExecutor nop(Executor executor) {
        return NoOpDisposableExecutor.of(executor);
    }

    /**
     * Creates an auto-closable executor.
     * @param executor Executor.
     * @return Create auto-closable executor.
     */
    static DisposableExecutor auto(Executor executor) {
        if (executor instanceof DisposableExecutor disposableExecutor) {
            return disposableExecutor;
        } else {
            return AutoDisposableExecutor.of(executor);
        }
    }

    /**
     * Disposable executor with empty close.
     */
    @AllArgsConstructor(staticName="of")
    class NoOpDisposableExecutor implements DisposableExecutor {
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
     * Auto-closed executor-service.
     */
    @AllArgsConstructor(staticName="of")
    class ExecutorServiceDisposableExecutor implements DisposableExecutor {
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
     * Auto-closed executor-service.
     */
    @AllArgsConstructor(staticName="of")
    class AutoDisposableExecutor implements DisposableExecutor {
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
