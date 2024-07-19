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

package com.yelstream.topp.standard.log.resist.slf4j.filter;

import com.yelstream.topp.standard.util.function.Predicates;
import com.yelstream.topp.standard.util.function.Suppliers;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Conditional transformation as part of logging.
 * <p>
 *     This may help implement e.g. rate-limiting logging.
 * </p>
 * @param <C> Type of context.
 * @param <X> Type of source to transform.
 * @param <R> Type of result of transformation.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-22
 */
@lombok.Builder(builderClassName="Builder")
@RequiredArgsConstructor(staticName="of")
@AllArgsConstructor(staticName="of")
@SuppressWarnings({"java:S1117","java:S1192"})
public class Conditional<C,X,R> {  //TO-DO: Consider placement and typing; this is not really specific for SLF4J!
    /**
     *  Identifier, used for tracking purposes.
     */
    private String id;

    /**
     * Context maintained during evaluation.
     * <p>
     *     This may be {@code null}.
     * </p>
     */
    private final C context;

    /**
     * Transformation evaluating the overall condition.
     */
    private final BiFunction<C,X,R> transformation;

    /**
     * Performs evaluation.
     * @param source Source being evaluated.
     * @return Result of evaluation.
     */
    public FilterResult<C,R> evaluate(X source) {
        R result=transformation.apply(context,source);
        return FilterResult.of(context,result);
    }

    /**
     * Provides the identity condition.
     * This is a neutral transformation.
     * @return Identity condition.
     * @param <C> Type of context.
     * @param <X> Type of source to transform.
     */
    public static <C,X> Conditional<C,X,X> identity() {
        return Conditional.of(null,(c,x)->x);
    }

    /**
     * Create a condition transformation.
     * @param sourceTransformation Transformation of source.
     * @param sourceEvaluation Evaluation of source.
     * @param onAccept Accept handler.
     * @param onReject Reject handler.
     * @param neutralSourceSupplier Factory of a neutral source.
     * @return Condition transformation.
     * @param <C> Type of context.
     * @param <X> Type of source to transform.
     * @param <R> Type of result of transformation.
     */
    @SuppressWarnings({"java:S3776","java:S3398"})
    private static <C,X,R> BiFunction<C,X,R> createTransformation(Function<X,R> sourceTransformation,
                                                                  Predicate<X> sourceEvaluation,
                                                                  Consumer<C> onAccept,
                                                                  Consumer<C> onReject,
                                                                  Supplier<R> neutralSourceSupplier) {
        return (context,x)->{
            R result;
            boolean pass=Predicates.test(sourceEvaluation,x);  //Indicates if source passes evaluation.
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
                result=Suppliers.get(neutralSourceSupplier);
            } else {
                result=sourceTransformation.apply(x);
            }
            return result;
        };
    }

    public static class Builder<C,X,R> {
        private String id;

        private C context;

        private Predicate<X> sourceEvaluation;

        private Function<X,R> sourceTransformation;

        private Consumer<C> onAccept;

        private Consumer<C> onReject;

        private Supplier<R> neutralSourceSupplier;

        public Conditional<C,X,R> build() {
            BiFunction<C,X,R> transformation=
                createTransformation(sourceTransformation,
                                     sourceEvaluation,
                                     onAccept,
                                     onReject,
                                     neutralSourceSupplier);
            return Conditional.of(id,context,transformation);
        }

        public Builder<C,X,R> limit(Predicate<X> limit) {
            sourceEvaluation=Predicates.and(sourceEvaluation,limit);
            return this;
        }

        public Builder<C,X,R> limit(BooleanSupplier limit) {
            Predicate<X> p=x->limit.getAsBoolean();
            return limit(p);
        }

        private static class RateLimiterHolder {
            private static final RateLimiterRegistry registry=RateLimiterRegistry.ofDefaults();
        }

        private RateLimiterConfig defaultRateLimiterConfig(int perPeriod,
                                                           Duration period) {
            return RateLimiterConfig.custom()
                .timeoutDuration(Duration.ZERO)
                .limitRefreshPeriod(period)
                .limitForPeriod(perPeriod)
                .build();
        }

        public Builder<C,X,R> limit(int frequency) {
            return limit(id,frequency,Duration.ofSeconds(1));
        }

        public Builder<C,X,R> limit(String rateLimiterName,
                                    int perPeriod,
                                    Duration period) {
            Predicate<X> p=x->{
                RateLimiter rateLimiter=Builder.RateLimiterHolder.registry.rateLimiter(rateLimiterName,()->defaultRateLimiterConfig(perPeriod,period));
                return rateLimiter.acquirePermission();
            };
            return limit(p);
        }

        public Builder<C,X,R> sourceTransformation(Function<X,R> sourceTransformation) {
            this.sourceTransformation=sourceTransformation;
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
