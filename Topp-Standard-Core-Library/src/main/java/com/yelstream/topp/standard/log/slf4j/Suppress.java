package com.yelstream.topp.standard.log.slf4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Slf4j
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor
public class Suppress<X> {  //TO-DO: Remove!
    private final String id;
    private final Supplier<RateLimiter> rateLimiterSupplier;
    private final Predicate<Builder<X>> predicate;
    private final X x;

    public Suppress<X> exec() {
        return this;
    }

    public Suppress<X> exec(BiConsumer<Object/*Context!*/,X> consumer) {
        return this;
    }

    public Suppress<X> exec(Consumer<X> consumer) {
        return this;
    }

/*
    public Suppress<X> exec() {
        return this;
    }
*/

/*
    public Builder<X> of() {
        return builder();
    }
*/

    public static <X> Suppress<X> of(String id, Supplier<Suppress<X>> suppressSupplier) {  //Cache under id, construct if unavailable
        return suppressSupplier.get();
    }

    public static <X,Y> Suppress<Y> of(String id, Function<Builder<X>,Builder<Y>> builderOperator) {  //Cache under id, construct if unavailable
        Builder<X> builder=builder();
        return builderOperator.apply(builder).build();
    }

    public static <X> Suppress<X> of(String id, Suppress.Builder<X> builder) {
        return builder.build();
    }

/*
    public static <X> Suppress<X> of(String id, Consumer<Builder<X>> builderConsumer) {
        Builder<X> builder=builder();
        builderConsumer.accept(builder);
        return builder.build();
    }
*/

    public static <X> Suppress.Builder<X> of(String id) {
        Builder<X> builder=builder();
        return builder();
    }

    public static <B extends LoggingEventBuilder> Builder<B> of(B loggingEventBuilder) {
        return builder().on(loggingEventBuilder);
    }



    public static class Builder<X> {
        public <B extends LoggingEventBuilder> Builder<B> on(B loggingEventBuilder) {
            Builder<B> builder=builder();
            if (LoggingEventBuilders.isLoggingEnabled(loggingEventBuilder)) {
                builder.x(loggingEventBuilder);
            }
            return builder;
        }

        public Builder<X> limit() {
            return this;
        }

        public Builder<X> proceed(BiConsumer<Object/*Context!*/,X> consumer) {
            return this;
        }

        public Suppress<X> exec() {
            Suppress<X> suppress=build();
            suppress.exec();
            return suppress;
        }

        public Suppress<X> exec(BiConsumer<Object/*Context!*/,X> consumer) {
            Suppress<X> suppress=build();
            suppress.exec(consumer);
            return suppress;
        }

        public Suppress<X> exec(Consumer<X> consumer) {
            Suppress<X> suppress=build();
            suppress.exec(consumer);
            return suppress;
        }

    }

    public static void main(String[] args) {


        Suppress.builder().on(log.atDebug()).limit().proceed((c, x)->{
            x.log("xxx!");
        }).exec();




        //If not already build and cached, then build it from scratch and cache it:
        Suppress.of("1234",()-> Suppress.builder().on(log.atDebug()).limit().build()).exec((c, x)->{
            x.log("xxx!");
        });

        //If not already build and cached, then build it (with the id preset) and cache it:
        Suppress.of("1234", builder->builder.on(log.atDebug()).limit()).exec((c, x)->{
            x.log("xxx!");
        });


        Suppress.of("1234", Suppress.builder().on(log.atDebug()).limit()).exec((c, x)->{
            x.log("xxx!");
        });


        Suppress.of("1234").on(log.atDebug()).limit().exec((c, x)->{
            x.log("xxx!");
        });


        //Simplest form; check log first, possible some named rate-limiter cached (id is the name of the rate-limiter) together with stat, execute:
        Suppress.of(log.atDebug()).limit().exec((c, x)->{
            x.log("xxx!");
        });

        //Simplest form; check log first, possible some named rate-limiter cached (id is the name of the rate-limiter), execute:
/*
        Suppress.of(log.atDebug()).limit(*/
/*add existing reate-limiter here*//*
).exec(x->{
            x.log("xxx!");
        });
*/

//        Decorators.ofFunction().decorate().apply(log.atDebug());

    }
}
