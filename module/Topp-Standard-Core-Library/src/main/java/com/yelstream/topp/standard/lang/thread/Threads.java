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

package com.yelstream.topp.standard.lang.thread;

import lombok.experimental.UtilityClass;

import java.time.Duration;

/**
 * Addresses instances of {@link Thread}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-01-23
 */
@UtilityClass
@SuppressWarnings({"unused","UnusedReturnValue"})
public class Threads {
    /**
     * Causes the currently executing thread to sleep for the specified duration.
     * @param durationInMillis Sleep duration in ms.
     * @return Indicates, if the currently executing thread slept as requested implying that it is not interrupted.
     */
    public static boolean sleep(long durationInMillis) {
        try {
            Thread.sleep(durationInMillis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();  //Yes, propagate the signal send by the exception to the state of the current thread
        }
        return !Thread.currentThread().isInterrupted();
    }

    /**
     * Causes the currently executing thread to sleep for the specified duration.
     * @param durationPartMillis Sleep duration in ms.
     * @param durationPartNanos Sleep duration in ns.
     *                          Valid range is {@code 0-999999}.
     * @return Indicates, if the currently executing thread slept as requested implying that it is not interrupted.
     */
    public static boolean sleep(long durationPartMillis,
                                int durationPartNanos) {
        try {
            Thread.sleep(durationPartMillis,durationPartNanos);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();  //Yes, propagate the signal send by the exception to the state of the current thread
        }
        return !Thread.currentThread().isInterrupted();
    }

    /**
     * Causes the currently executing thread to sleep for the specified duration.
     * @param duration Sleep duration.
     * @return Indicates, if the currently executing thread slept as requested implying that it is not interrupted.
     */
    public static boolean sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();  //Yes, propagate the signal send by the exception to the state of the current thread
        }
        return !Thread.currentThread().isInterrupted();
    }

    /**
     * Waits for a specific thread to die.
     * @param thread Thread to wait for to die.
     * @return Indicates, if the wait is a success.
     *         If not, the calling thread was interrupted.
     */
    public static boolean join(Thread thread) {
        boolean success=false;
        try {
            thread.join();
            success=true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();  //Yes, propagate the signal send by the exception to the state of the current thread
        }
        return success;
    }

    /**
     * Waits for a specific thread to die.
     * @param thread Thread to wait for to die.
     * @param durationInMillis Time to wait in ms.
     * @return Indicates, if the wait is a success.
     *         If not, the calling thread was interrupted.
     */
    public static boolean join(Thread thread,
                               long durationInMillis) {
        boolean success=false;
        try {
            thread.join(durationInMillis);
            success=thread.getState()==Thread.State.TERMINATED;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();  //Yes, propagate the signal send by the exception to the state of the current thread
        }
        return success;
    }

    /**
     * Waits for a specific thread to die.
     * @param thread Thread to wait for to die.
     * @param durationPartMillis Time to wait in ms.
     * @param durationPartNanos Time to wait in ns.
     *                          Valid range is {@code 0-999999}.
     * @return Indicates, if the wait is a success.
     *         If not, the calling thread was interrupted.
     */
    public static boolean join(Thread thread,
                               long durationPartMillis,
                               int durationPartNanos) {
        boolean success=false;
        try {
            thread.join(durationPartMillis,durationPartNanos);
            success=thread.getState()==Thread.State.TERMINATED;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();  //Yes, propagate the signal send by the exception to the state of the current thread
        }
        return success;
    }

    /**
     * Waits for a specific thread to die.
     * @param thread Thread to wait for.
     * @param duration Time to wait.
     * @return Indicates, if the wait is a success.
     *         If not, the calling thread was interrupted.
     */
    public static boolean join(Thread thread,
                               Duration duration) {
        boolean success=false;
        try {
            success=thread.join(duration);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();  //Yes, propagate the signal send by the exception to the state of the current thread
        }
        return success;
    }
}
