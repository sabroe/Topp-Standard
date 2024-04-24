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
