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
 * Utility dedicated to help create throwables to be thrown.
 * <p>
 *     Examples:
 * </p>
 * <pre>
 *     throw Ex.n(IOException::new).build();
 *     throw Ex.m(IOException::new).message("Failure to XXX!").build();
 *     throw Ex.c(IOException::new).cause(ex).build();
 *     throw Ex.mc(IOException::new).message("Failure to XXX!").cause(ex).build();
 * </pre>
 * <p>
 *     Note that this construction is an experiment into possible benefits!
 * </p>
 * <p>
 *     Current benefits:
 * </p>
 * <ol>
 *     <li>The cause can always be set.</li>
 *     <li>Creation-and-throw can be done in one line, always.</li>
 * </ol>
 * <p>
 *     Up for consideration:
 * </p>
 * <ol>
 *     <li>As of now, no reflection is involved - possible parameters are so very limited to the most common { message, cause }.</li>
 *     <li>Any ideas how to integrate exceptions taking additional arguments like e.g. (line-number,column-number)?</li>
 *     <li>Could there be any benefit to handle message formating with separate format and arguments? Usage of 'Formatter', 'Locale'?</li>
 *     <li>Could there be a possible benefit to have messages created logged consistently at the point of creation?</li>
 *     <li>Could there be any way to generate this type of builder and specific to each exception type, without sacrificing compile-time type checking?</li>
 *     <li>Similarities between fluent logging APIs and fluent exception-throwing?</li>
 *     <li>Matching constructors or methods dynamically, may involve some more advanced techniques:
 *         <ol>
 *             <li>Optional Parameters: Use Integer instead of int or Optional to handle missing parameters.</li>
 *             <li>Varargs Matching: Match arbitrary-length argument lists using varargs.</li>
 *             <li>Wildcard Type Matching: Handle covariant/contravariant types with instanceof and isAssignableFrom.</li>
 *             <li>Named Parameters: Use -parameters flag to retain and match parameter names dynamically.</li>
 *             <li>Primitive Boxing: Consider primitive-to-wrapper conversion (autoboxing/unboxing).</li>
 *             <li>Method Overloads: Match method signatures with different overloads based on types and argument count.</li>
 *             <li>Exception Handling: Use fallback constructors/methods if no exact match is found.</li>
 *         </ol>
 *     </li>
 * </ol>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-12
 */
@UtilityClass
public class Ex {
    public static <T extends Throwable> Builder<T> n(Supplier<T> constructor) {
        Builder<T> builder=Ex.<T>builder();
        return builder.creator(Throwables.creatorNoArgs(constructor));
    }

    public static <T extends Throwable> Builder<T> m(Function<String,T> c) {
        Builder<T> builder=Ex.<T>builder();
        return builder.creator(Throwables.creatorWithMessage(c));
    }

    public static <T extends Throwable> Builder<T> c(Function<Throwable,T> constructor) {
        Builder<T> builder=Ex.<T>builder();
        return builder.creator(Throwables.creatorWithCause(constructor));
    }

    public static <T extends Throwable> Builder<T> mc(BiFunction<String,Throwable,T> constructor) {
        Builder<T> builder=Ex.<T>builder();
        return builder.creator(Throwables.creatorWithMessageAndCause(constructor));
    }

    @lombok.Builder(builderClassName="Builder")
    private static <T extends Throwable> T createException(BiFunction<String,Throwable,T> creator,
                                                           String message,
                                                           Throwable cause) {
        //TO-DO: Consider logging messages created! The possible, central point for it is right here!
        return creator.apply(message,cause);
    }

    public static class Builder<T extends Throwable> {
        public Builder<T> format(String format, Object... args) {
            return message(String.format(format,args));
        }
    }
}
