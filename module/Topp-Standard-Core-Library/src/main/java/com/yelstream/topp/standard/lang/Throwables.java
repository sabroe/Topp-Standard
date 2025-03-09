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

package com.yelstream.topp.standard.lang;

import lombok.experimental.UtilityClass;

import javax.script.ScriptException;
import java.io.IOException;
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

/*
    throw Throwables.new().format("Failure to evaluate script; exception message is %s!",ex.getMessage()).cause(ex).build();
    throw Throwables.mc(IOException::new).format("Failure to evaluate script; exception message is %s!",ex.getMessage()).cause(ex).create();

    Function<String, ScriptException> f=ScriptException::new;       //m
    Function<String,IOException> f1=IOException::new;              //m
    BiFunction<String,Throwable,IOException> f2=IOException::new;  //mc
    Function<Throwable,IOException> f3=IOException::new;           //c
    Supplier<IOException> f4=IOException::new;           //c

    ScriptException x=(m,c)->ScriptException::new;

    ScriptException exception=new ScriptException(String.format("Failure to evaluate script; exception message is %s!",ex.getMessage()));
            exception.initCause(ex);
            throw exception;
*/

//    public static

    public static <T extends Throwable> BiFunction<String,Throwable,T> m(Supplier<T> constructor) {
        return (message,cause) -> {
            T t=constructor.get();
            if (cause!=null) {
                t.initCause(cause);
            }
            return t;
        };
    }

    public static <T extends Throwable> BiFunction<String,Throwable,T> m(Function<String,T> constructor) {
        return (message,cause) -> {
            T t = constructor.apply(message);
            if (cause != null) {
                t.initCause(cause);
            }
            return t;
        };
    }

    public static <T extends Throwable> BiFunction<String,Throwable,T> c(Function<Throwable,T> constructor) {
        return (message,cause) -> {
            T t=constructor.apply(cause);
            if (cause != null) {
                t.initCause(cause);
            }
            return t;
        };
    }

    public static <T extends Throwable> BiFunction<String,Throwable,T> mc(BiFunction<String,Throwable,T> constructor) {
        return (message,cause) -> {
            return constructor.apply(message,cause);
        };
    }

    @lombok.Builder(builderClassName="Builder")
    public static <T extends Throwable> T ex(BiFunction<String,Throwable,T> constructor,
                                             String message,
                                             Throwable cause){
        return constructor.apply(message,cause);
    }
    
    public static class Builder<T extends Throwable> {

        public <T2 extends Throwable> Builder<T2> m(Supplier<T2> c) {
            Builder<T2> b=builder();
            return b.constructor(Throwables.m(c));
        }

        public <T2 extends Throwable> Builder<T2> m(Function<String,T2> c) {
            Builder<T2> b=builder();
            return b.constructor(Throwables.m(c));
        }

        public <T2 extends Throwable> Builder<T2> c(Function<Throwable,T2> constructor) {
            Builder<T2> b=builder();
            return b.constructor(Throwables.c(constructor));
        }

        public <T2 extends Throwable> Builder<T2> mc(BiFunction<String,Throwable,T2> constructor) {
            Builder<T2> b=builder();
            return b.constructor(Throwables.mc(constructor));
        }

    }
}
