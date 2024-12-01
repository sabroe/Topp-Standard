package com.yelstream.topp.standard.gradle.contribution.tool.util;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Utilities addressing configuration by Gradle properties.
 * <p>
 *     Provides methods to resolve configuration values by evaluating
 *     multiple sources in order of priority.
 * </p>
 * @author Morten Sabroe Mortensen
 * @version 1.1
 * @since 2024-11-26
 */
@UtilityClass
public class Configuration {

    @AllArgsConstructor
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    public static class Definition<T> {
        @lombok.Singular
        public final List<Supplier<T>> sources;

    }

    @AllArgsConstructor
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    public static class Feature {
        @lombok.Singular
        private final List<Supplier<Boolean>> sources;

        private final boolean defaultValue;

        @SuppressWarnings("java:S4276")
        public Boolean resolve() {
            Boolean resolved=null;
            for (var source: sources) {
                Boolean value=source.get();
                if (value!=null) {
                    resolved=value;
                    break;
                }
            }
            return resolved;
        }

        public boolean isEnabled() {
            Boolean resolved=resolve();
            return resolved!=null?resolved:defaultValue;
        }

        public boolean isEnabled(boolean defaultValue) {
            Boolean resolved=resolve();
            return resolved!=null?resolved:defaultValue;
        }

        public static class Builder {
            private Builder append(Supplier<Boolean> enableSupplier) {
                this.source(enableSupplier);
                return this;
            }

            private static Boolean valueOf(String text) {
                Boolean value=null;
                if (text!=null) {
                    String trimmedText=text.trim();
                    if (!trimmedText.isEmpty()) {
                        value=Boolean.valueOf(trimmedText);
                    }
                }
                return value;
            }

            public boolean isEnabled() {
                return build().isEnabled();
            }

            public boolean isEnabled(boolean defaultValue) {
                return build().isEnabled(defaultValue);
            }

            public Builder enable(BooleanSupplier enableSupplier) {
                return append(enableSupplier::getAsBoolean);
            }

            public Builder enable(Boolean enable) {
                return append(()->enable);
            }

            public Builder enable(boolean enable) {
                return append(()->enable);
            }

            public Builder enable(String enable) {
                return append(()->valueOf(enable));
            }

            public Builder enable(Object enable) {
                return append(()->valueOf(enable==null?null:enable.toString()));
            }

            public Builder enable(Supplier<Object> enableSupplier) {
                return append(()->{
                    Object enable=enableSupplier.get();
                    return valueOf(enable==null?null:enable.toString());
                });
            }
        }
    }

    public static Feature.Builder feature() {
        return Feature.builder();
    }

    public static Feature.Builder feature(boolean defaultValue) {
        return Feature.builder().defaultValue(defaultValue);
    }
}
