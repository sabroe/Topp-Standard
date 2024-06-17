package com.yelstream.topp.standard.log.slf4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Slf4j
public class Chains {  //TO-DO: Remove!??? Hm.


/*
    static <T> Decorators.DecorateSupplier<T> ofSupplier(Supplier<T> supplier) {
        return new Decorators.DecorateSupplier(supplier);
    }

    static <T, R> Decorators.DecorateFunction<T, R> ofFunction(Function<T, R> function) {
        return new Decorators.DecorateFunction(function);
    }

    static Decorators.DecorateRunnable ofRunnable(Runnable runnable) {
        return new Decorators.DecorateRunnable(runnable);
    }

    static <T> Decorators.DecorateCallable<T> ofCallable(Callable<T> callable) {
        return new Decorators.DecorateCallable(callable);
    }

    static <T> Decorators.DecorateCheckedSupplier<T> ofCheckedSupplier(CheckedSupplier<T> supplier) {
        return new Decorators.DecorateCheckedSupplier(supplier);
    }

    static <T, R> Decorators.DecorateCheckedFunction<T, R> ofCheckedFunction(CheckedFunction<T, R> function) {
        return new Decorators.DecorateCheckedFunction(function);
    }

    static Decorators.DecorateCheckedRunnable ofCheckedRunnable(CheckedRunnable supplier) {
        return new Decorators.DecorateCheckedRunnable(supplier);
    }

    static <T> Decorators.DecorateCompletionStage<T> ofCompletionStage(Supplier<CompletionStage<T>> stageSupplier) {
        return new Decorators.DecorateCompletionStage(stageSupplier);
    }

    static <T> Decorators.DecorateConsumer<T> ofConsumer(Consumer<T> consumer) {
        return new Decorators.DecorateConsumer(consumer);
    }
*/

    public static class LinkSupplier<T> {
        private Supplier<T> supplier;

        private LinkSupplier(Supplier<T> supplier) {
            this.supplier = supplier;
        }

/*
        public Decorators.DecorateSupplier<T> withTimer(Timer timer) {
            this.supplier = Timer.decorateSupplier(timer, this.supplier);
            return this;
        }
*/

/*
        public Decorators.DecorateSupplier<T> withCircuitBreaker(CircuitBreaker circuitBreaker) {
            this.supplier = CircuitBreaker.decorateSupplier(circuitBreaker, this.supplier);
            return this;
        }
*/

/*
        public Decorators.DecorateSupplier<T> withRetry(Retry retryContext) {
            this.supplier = Retry.decorateSupplier(retryContext, this.supplier);
            return this;
        }
*/

/*
        public <K> Decorators.DecorateFunction<K, T> withCache(Cache<K, T> cache) {
            return Decorators.ofFunction(Cache.decorateSupplier(cache, this.supplier));
        }
*/

        public LinkSupplier<T> withRateLimiter(RateLimiter rateLimiter) {
            return this.withRateLimiter(rateLimiter, 1);
        }

        public LinkSupplier<T> withRateLimiter(RateLimiter rateLimiter, int permits) {
            this.supplier = RateLimiter.decorateSupplier(rateLimiter, permits, this.supplier);
            return this;
        }

/*
        public LinkSupplier<T> withBulkhead(Bulkhead bulkhead) {
            this.supplier = Bulkhead.decorateSupplier(bulkhead, this.supplier);
            return this;
        }
*/

/*
        public LinkSupplier<T> withFallback(Function<Throwable, T> exceptionHandler) {
            this.supplier = SupplierUtils.recover(this.supplier, exceptionHandler);
            return this;
        }
*/

/*
        public LinkSupplier<T> withFallback(Predicate<T> resultPredicate, UnaryOperator<T> resultHandler) {
            this.supplier = SupplierUtils.recover(this.supplier, resultPredicate, resultHandler);
            return this;
        }
*/

/*
        public LinkSupplier<T> withFallback(BiFunction<T, Throwable, T> handler) {
            this.supplier = SupplierUtils.andThen(this.supplier, handler);
            return this;
        }
*/

/*
        public LinkSupplier<T> withFallback(List<Class<? extends Throwable>> exceptionTypes, Function<Throwable, T> exceptionHandler) {
            this.supplier = SupplierUtils.recover(this.supplier, exceptionTypes, exceptionHandler);
            return this;
        }
*/

/*
        public DecorateCompletionStage<T> withThreadPoolBulkhead(ThreadPoolBulkhead threadPoolBulkhead) {
            return Decorators.ofCompletionStage(this.getCompletionStageSupplier(threadPoolBulkhead));
        }
*/

/*
        private Supplier<CompletionStage<T>> getCompletionStageSupplier(ThreadPoolBulkhead threadPoolBulkhead) {
            return () -> {
                try {
                    return threadPoolBulkhead.executeSupplier(this.supplier);
                } catch (BulkheadFullException var4) {
                    CompletableFuture<T> future = new CompletableFuture();
                    future.completeExceptionally(var4);
                    return future;
                }
            };
        }
*/

        public Supplier<T> decorate() {
            return this.supplier;
        }

        public T get() {
            return this.supplier.get();
        }


        public T exec() {
            return supplier.get();
        }

        public void exec(Consumer<T> consumer) {
            consumer.accept(supplier.get());
        }

    }

    /*
        Suppress.of(log.atDebug())            //Supplier<LoggingEventBuilder>, Function<LoggingEventBuilder,LoggingEventBuilder> (is logging enabled, apply predicate)
                .limit(rateLimiter)           //Function<LoggingEventBuilder,LoggingEventBuilder> (is logging enabled, apply predicate)
                .log("Logging event number: {}", i);


        Suppress.of(log.atDebug())
                .limit(rateLimiter)
                .exec()
                .log("Logging");

        Suppress.of(log.atDebug())   <- source
                .limit(rateLimiter)  <- add to a predicate
                .exec(x->{           <- obtain the result, possible return the neutral result
                    x.log("Logging");
                });
     */

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        LoggingEventBuilder leb=log.atDebug();

        Predicate<LoggingEventBuilder> logEnabled=LoggingEventBuilders::isLoggingEnabled;
        Supplier<LoggingEventBuilder> neutralLebSupplier=NOPLoggingEventBuilder::singleton;

        BooleanSupplier rateLimited=()->false;

        UnaryOperator<LoggingEventBuilder> map=leb2->{
            if (rateLimited.getAsBoolean()) {
                return neutralLebSupplier.get();
            } else {
                return leb2;
            }
        };

        Supplier<LoggingEventBuilder> result=()->null;  //Returning EITHER the original LoggingEventBuilder from log.atDebug OR NOPLoggingEventBuilder.singleton()!

//        LinkSupplier<LoggingEventBuilder> s0=Chains.of(log.atDebug());
//        s0.after
    }
}
