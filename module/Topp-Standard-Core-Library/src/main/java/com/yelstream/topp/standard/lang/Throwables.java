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

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utility addressing instances of {@link Throwable}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-09
 */
@UtilityClass
public class Throwables {
    /**
     * Decorates a throwable with a cause in a type-preserving way.
     * <p>
     *    This allows for fluent, more compact,
     *    one-line throws for exceptions types not offering to set a cause in the available constructors.
     * </p>
     * <p>
     *     Example usages:
     * </p>
     * <pre>
     *    throw Throwables.ex(new ScriptException("Script!"),ex);
     * </pre>
     * @param throwable Exception to decorate.
     * @param cause Cause to set as decoration.
     * @return Decorated exception, always the argument throwable itself.
     * @param <T> Type of throwable.
     */
    public static <T extends Throwable> T cause(T throwable,
                                                Throwable cause) {
        throwable.initCause(cause);
        return throwable;
    }

    /**
     * Transforms a constructor invocation taking no arguments to create a throwable to one taking arguments (message,cause).
     * @param constructor Constructor invocation taking no arguments.
     * @return Creator of a throwable from a (message,cause).
     * @param <T> Type of throwable.
     */
    public static <T extends Throwable> BiFunction<String,Throwable,T> creatorNoArgs(Supplier<T> constructor) {  //TO-DO: Consider making access package private or move to 'Ex'!
        return (message,cause) -> {
            //TO-DO: Consider testing and logging if the message provided is non-null!
            //TO-DO: Consider testing and logging if the cause provided is non-null!
            T t=constructor.get();
            if (cause!=null) {
                //TO-DO: Consider logging that the cause is set in a special way!
                t.initCause(cause);
            }
            return t;
        };
    }

    /**
     * Transforms a constructor invocation taking arguments (message) to create a throwable to one taking arguments (message,cause).
     * @param constructor Constructor invocation taking arguments (message).
     * @return Creator of a throwable from a (message,cause).
     * @param <T> Type of throwable.
     */
    public static <T extends Throwable> BiFunction<String,Throwable,T> creatorWithMessage(Function<String,T> constructor) {  //TO-DO: Consider making access package private or move to 'Ex'!
        return (message,cause) -> {
            //TO-DO: Consider testing and logging if the message provided is null!
            //TO-DO: Consider testing and logging if the cause provided is non-null!
            T t = constructor.apply(message);
            if (cause!=null) {
                //TO-DO: Consider logging that the cause is set in a special way!
                t.initCause(cause);
            }
            return t;
        };
    }

    /**
     * Transforms a constructor invocation taking arguments (cause) to create a throwable to one taking arguments (message,cause).
     * @param constructor Constructor invocation taking arguments (cause).
     * @return Creator of a throwable from a (message,cause).
     * @param <T> Type of throwable.
     */
    @SuppressWarnings("java:S1602")
    public static <T extends Throwable> BiFunction<String,Throwable,T> creatorWithCause(Function<Throwable,T> constructor) {  //TO-DO: Consider making access package private or move to 'Ex'!
        return (message,cause) -> {
            //TO-DO: Consider testing and logging if the message provided is non-null!
            //TO-DO: Consider testing and logging if the cause provided is null!
            return constructor.apply(cause);
        };
    }

    /**
     * Transforms a constructor invocation taking arguments (message,cause) to create a throwable to one taking arguments (message,cause).
     * @param constructor Constructor invocation taking arguments (message,cause).
     * @return Creator of a throwable from a (message,cause).
     * @param <T> Type of throwable.
     */
    @SuppressWarnings({"java:S1602","java:S1612"})
    public static <T extends Throwable> BiFunction<String,Throwable,T> creatorWithMessageAndCause(BiFunction<String,Throwable,T> constructor) {  //TO-DO: Consider making access package private or move to 'Ex'!
        return (message,cause) -> {
            //TO-DO: Consider testing and logging if the message provided is null!
            //TO-DO: Consider testing and logging if the cause provided is null!
            return constructor.apply(message,cause);
        };
    }
}
