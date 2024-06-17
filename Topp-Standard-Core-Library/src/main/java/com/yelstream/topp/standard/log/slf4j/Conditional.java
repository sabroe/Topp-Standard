package com.yelstream.topp.standard.log.slf4j;

import com.yelstream.topp.standard.lang.thread.Threads;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Slf4j
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor
@SuppressWarnings({"java:S1117","java:S1192"})
public class Conditional<C,X> {  //TO-DO: Remove!

    private final String id;
    private final C context;
    private final X source;
    private final Supplier<X> neutralSourceSupplier;

    private final BiPredicate<C,X> predicate;
    private final Consumer<C> onAccept;
    private final Consumer<C> onReject;

//    private final Supplier<RateLimiter> rateLimiterSupplier;
//    LoggingEventBuilder leb=log.atDebug();
//    Predicate<LoggingEventBuilder> logEnabled=LoggingEventBuilders::isLoggingEnabled;
//    Supplier<LoggingEventBuilder> neutralLebSupplier= NOPLoggingEventBuilder::singleton;
//    BooleanSupplier rateLimited=()->false;

    private X finalSource() {
        X source=this.source;
        if (predicate!=null && !predicate.test(context,source)) {
            source=neutralSourceSupplier.get();
            if (onReject!=null) {
                onReject.accept(context);
            }
        } else {
            if (onAccept!=null) {
                onAccept.accept(context);
            }
        }
        return source;
    }

    public X exec() {
        return finalSource();
    }

    public Conditional<C,X> exec(BiConsumer<C,X> consumer) {
        X source=finalSource();
        consumer.accept(context,source);
        return this;
    }

    public Conditional<C,X> exec(Consumer<X> consumer) {
        X source=this.source;
        consumer.accept(source);
        return this;
    }

/*
    public Builder<X> of() {
        return builder();
    }
*/



    public static class Builder<C,X> {
        private String id;
        private BiPredicate<C,X> predicate;
        private Consumer<C> onAccept;
        private Consumer<C> onReject;

        public Builder<C,X> limit(BiPredicate<C,X> limit) {
            if (predicate==null) {
                predicate=limit;
            } else {
                predicate=predicate.and(limit);
            }
            return this;
        }

        public Builder<C,X> limit(BooleanSupplier limit) {
            BiPredicate<C,X> p=(c,x)->{
                boolean b=limit.getAsBoolean();
                //TO-DO: Change suppressedCounter!
                return b;
            };
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

        public Builder<C,X> limit(int frequency) {
            return limit(id,frequency,Duration.ofSeconds(1));
        }

        public Builder<C,X> limit(String rateLimiterName, int perPeriod, Duration period) {
            BiPredicate<C,X> p=(c,x)->{

                RateLimiter rateLimiter=RateLimiterHolder.registry.rateLimiter(rateLimiterName,()->defaultRateLimiterConfig(perPeriod,period));
System.out.println("rateLimiter: "+rateLimiter);
                boolean b=rateLimiter.acquirePermission();

                if (!b) {
System.out.println("Suppressed!");
                    //c.
                } else {
System.out.println("Accepted.");
                }

                return b;
            };
            return limit(p);
        }

        public X exec() {
            return build().exec();
        }

        public Conditional<C,X> exec(BiConsumer<C,X> consumer) {
            return build().exec(consumer);
        }

        public Conditional<C,X> exec(Consumer<X> consumer) {
            return build().exec(consumer);
        }
    }

    public static class LoggingEventBuilderContext {
        @Getter(AccessLevel.PRIVATE)
        private final AtomicLong suppressedCount=new AtomicLong();

        public long suppressedCount() {
            return suppressedCount.get();
        }
    }

/*
    public static <C,X> Builder<C,X> of(String id) {
        Builder<C,X> builder=builder();

        builder.context(new LoggingEventBuilderContext());  //TO-DO: Lookup by id!
        builder.onAccept(c->c.suppressedCount.incrementAndGet());
        builder.onReject(c->c.suppressedCount.set(0));
        builder.source(loggingEventBuilder).neutralSourceSupplier(NOPLoggingEventBuilder::singleton);
        return builder;
    }
*/

    public static Builder<LoggingEventBuilderContext,LoggingEventBuilder> of(LoggingEventBuilder loggingEventBuilder) {
        Builder<LoggingEventBuilderContext,LoggingEventBuilder> builder=builder();
        builder.context(new LoggingEventBuilderContext());  //TO-DO: Lookup by id!
        builder.onAccept(c->c.suppressedCount.incrementAndGet());
        builder.onReject(c->c.suppressedCount.set(0));
        builder.source(loggingEventBuilder).neutralSourceSupplier(NOPLoggingEventBuilder::singleton);
        return builder;
    }

    public static void main(String[] args) {

        Conditional.of(log.atDebug()).exec().log("Logging!");
        Conditional.of(log.atDebug()).exec().setMessage("Logging!").log();
        Conditional.of(log.atDebug()).exec(log->log.setMessage("Logging!").log());
        Conditional.of(log.atDebug()).exec((c,log)->log.setMessage("Logging!").log());

        Conditional.of(log.atDebug()).exec().log("Logging!");
//        Conditional.of(log.atDebug()).limit(bs).exec().setMessage("Logging!").log();
//        Conditional.of(log.atDebug()).limit(bs).exec((c,log)->log.setMessage("Logging!").log());

//        Conditional.of(log.atDebug()).id("d5aa9586-3f35-423a-be3b-3c5b88b4824e").limit(Duration.ofSeconds(5),1).exec((c,log)->log.setMessage("Logging done; suppressed {}.").addArgument(()->c.getSuppressedCount()).setCause(ex).log());

        for (int i=0; i<10; i++) {
            Conditional.of(log.atInfo()).id("d5aa9586-3f35-423a-be3b-3c5b88b4824e").limit("xxx", 1, Duration.ofSeconds(5)).exec((c, log) -> log.setMessage("(2)Logging done; suppressed {}.").addArgument(c::suppressedCount).log());
            Conditional.of(log.atInfo()).id("3f35").limit(5).exec((c, log) -> log.log("(1)Logging done; suppressed {}.", c.suppressedCount()));

            Threads.sleep(Duration.ofMillis(100));
        }

        for (int i=0; i<10; i++) {
//            Conditional.of("3f35",b->b.limit(5)).exec(log.atInfo(),(c,log) -> log.log("(1)Logging done; suppressed {}.",c.suppressedCount()));
            Slip.of(log.atInfo()).id("3f35",b->b.limit(5)).exec((c,log) -> log.log("(2)Logging done; suppressed {}.",c.suppressed()));

            Threads.sleep(Duration.ofMillis(100));
        }
    }


}
