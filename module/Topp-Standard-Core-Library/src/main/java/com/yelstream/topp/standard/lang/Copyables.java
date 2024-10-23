package com.yelstream.topp.standard.lang;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.function.Consumer;

@UtilityClass
public class Copyables {

/*
    @AllArgsConstructor
    public static class DefaultCopyable<T extends Copyable<T,T>> implements Copyable<T,T> {
        @Getter
        private T object;

        @Override
        public T copy(Consumer<T> consumer) {
            return object;
        }
    }
*/


/*
    public static <T extends Copyable<T,T>> Copyable<T,T> create(T object) {

        return object.copy();
    }
*/

/*
    public static <T> T copy(T object) {
        return create(object).copy();
    }
*/

}
