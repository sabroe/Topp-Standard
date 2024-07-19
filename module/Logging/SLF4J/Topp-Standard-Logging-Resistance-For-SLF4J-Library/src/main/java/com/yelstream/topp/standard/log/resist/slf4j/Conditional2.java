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

package com.yelstream.topp.standard.log.resist.slf4j;

import com.yelstream.topp.standard.util.function.Suppliers;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Conditional transformation as part of logging.
 * <p>
 *     This may help implement e.g. rate-limiting.
 * </p>
 * @param <C> Type of context.
 * @param <X> Source to transform.
 * @param <R> Result of transformation.
 */
@lombok.Builder(builderClassName="Builder")
@RequiredArgsConstructor(staticName="of")
@AllArgsConstructor(staticName="of")
@SuppressWarnings({"java:S1117","java:S1192"})
public class Conditional2<C,X,R> {
    /**
     *  Identifier, used for tracking purposes.
     */
    private String id;

    /**
     *
     */
    private final Supplier<C> contextSupplier;

    /**
     *
     */
    private final BiFunction<Supplier<C>,X,R> transformation;

    /**
     *
     */
    public FilterResult<C,R> evaluate(X source) {
        R result=transformation.apply(contextSupplier,source);
        return FilterResult.of(contextSupplier,result);
    }

    public static <C,X> Conditional2<C,X,X> identity() {
        return Conditional2.of(()->null,(c,x)->x);
    }

    public static <C,X> Conditional2<C,X,X> identity(Supplier<C> contextSupplier,
                                                     Consumer<C> contextUpdate) {
        return Conditional2.of(contextSupplier,(cs,x)->{
            C context=Suppliers.get(contextSupplier);
            if (context!=null) {
                contextUpdate.accept(context);
            }
            return x;
        });
    }

    private static <C,X,R> BiFunction<Supplier<C>,X,R> createTransformation(Function<X,R> transformation,
                                                                            Predicate<X> predicate,
                                                                            Consumer<C> onAccept,
                                                                            Consumer<C> onReject,
                                                                            Supplier<R> neutralSourceSupplier) {
        return (cs,x)->{
            R result;
            boolean pass=predicate!=null && predicate.test(x);
            C context=cs==null?null:cs.get();
            if (context!=null) {
                if (!pass) {
                    if (onReject!=null) {
                        onReject.accept(context);
                    }
                } else {
                    if (onAccept!=null) {
                        onAccept.accept(context);
                    }
                }
            }
            if (!pass) {
                result=neutralSourceSupplier==null?null:neutralSourceSupplier.get();
            } else {
                result=transformation.apply(x);
            }
            return result;
        };
    }

    public static class Builder<C,X,R> {
        private String id;

        private Supplier<C> contextSupplier;

//        private BiFunction<Supplier<C>,X,R> transformation;

        private Predicate<X> predicate;

        private Function<X,R> transformation;

        private Consumer<C> onAccept;

        private Consumer<C> onReject;

        private Supplier<R> neutralSourceSupplier;


        public Conditional2<C,X,R> build() {
            BiFunction<Supplier<C>,X,R> _transformation=
                createTransformation(transformation,
                                     predicate,
                                     onAccept,
                                     onReject,
                                     neutralSourceSupplier);
            return Conditional2.of(id,contextSupplier,_transformation);
        }

        public Builder<C,X,R> limit(Predicate<X> limit) {
            if (predicate==null) {
                predicate=limit;
            } else {
                predicate=predicate.and(limit);
            }
            return this;
        }

        public Builder<C,X,R> limit(BooleanSupplier limit) {
            Predicate<X> p=x->limit.getAsBoolean();
            return limit(p);
        }

        private static class ContextHolder {
            private static final Map<String,Object> contextRegistry=new ConcurrentHashMap<>();
        }

        private static class RateLimiterHolder {
            private static final RateLimiterRegistry registry=RateLimiterRegistry.ofDefaults();
        }

        private RateLimiterConfig defaultRateLimiterConfig(int perPeriod, Duration period) {
            return RateLimiterConfig.custom()
                    .timeoutDuration(Duration.ZERO)
                    .limitRefreshPeriod(period)
                    .limitForPeriod(perPeriod)
                    .build();
        }

        public Builder<C,X,R> limit(int frequency) {
            return limit(id,frequency,Duration.ofSeconds(1));
        }

        public Builder<C,X,R> limit(String rateLimiterName, int perPeriod, Duration period) {
            Predicate<X> p=x->{

                RateLimiter rateLimiter=Builder.RateLimiterHolder.registry.rateLimiter(rateLimiterName,()->defaultRateLimiterConfig(perPeriod,period));
//                System.out.println("rateLimiter: "+rateLimiter);
                boolean pass=rateLimiter.acquirePermission();

                return pass;
            };
            return limit(p);
        }

        public Builder<C,X,R> transformation(Function<X,R> transformation) {
            this.transformation=transformation;
            return this;
        }

        public Builder<C,X,R> onAccept(Consumer<C> onAccept) {
            this.onAccept=onAccept;
            return this;
        }

        public Builder<C,X,R> onReject(Consumer<C> onReject) {
            this.onReject=onReject;
            return this;
        }

        public Builder<C,X,R> neutralSourceSupplier(Supplier<R> neutralSourceSupplier) {
            this.neutralSourceSupplier=neutralSourceSupplier;
            return this;
        }
    }
}
