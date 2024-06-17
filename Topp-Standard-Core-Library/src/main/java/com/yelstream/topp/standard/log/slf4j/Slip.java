package com.yelstream.topp.standard.log.slf4j;

import com.yelstream.topp.standard.lang.thread.Threads;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@AllArgsConstructor(staticName="of")
public class Slip {
    private final LoggingEventBuilder source;

    public static class Context {

        @Getter
        @NoArgsConstructor(force=true)
        @AllArgsConstructor
        @ToString
        private static class State {
            private final long index;
            private final long rejectCount;
            private final long acceptCount;
            private final long suppressedCount;
            private final long nextSuppressedCount;
        }

        private final AtomicReference<State> stateRef = new AtomicReference<>(
            new State()
        );

        private State onAccept() {
            while (true) {
                State currentState = stateRef.get();
                State newState = new State(
                    currentState.index+1,
                    currentState.rejectCount,
                    currentState.acceptCount+1,
                    currentState.nextSuppressedCount,
                    0
                );
                if (stateRef.compareAndSet(currentState, newState)) {
                    return newState;
                }
            }
        }

        private State onReject() {
            while (true) {
                State currentState = stateRef.get();
                State newState = new State(
                    currentState.index+1,
                    currentState.rejectCount+1,
                    currentState.acceptCount,
                    currentState.nextSuppressedCount,
                    currentState.nextSuppressedCount+1
                );
                if (stateRef.compareAndSet(currentState, newState)) {
                    return newState;
                }
            }
        }

        public long accepted() {
            return stateRef.get().acceptCount;
        }

        public long rejected() {
            return stateRef.get().rejectCount;
        }

        public long suppressed() {
            return stateRef.get().suppressedCount;
        }
    }

    public Exec<Context,LoggingEventBuilder> nop() {
        Conditional2<Context,LoggingEventBuilder,LoggingEventBuilder> conditional=Conditional2.identity();
        return conditional.evaluate(source);
    }

    private static class ConditionalHolder {
        private static final Map<String,Conditional2<Context,LoggingEventBuilder,LoggingEventBuilder>> registry=new ConcurrentHashMap<>();
    }

    public Exec<Context,LoggingEventBuilder> id(String id, Consumer<Conditional2.Builder<Context,LoggingEventBuilder,LoggingEventBuilder>> builderConsumer) {
//log.info("Registry: {}",ConditionalHolder.registry);
        Conditional2<Context,LoggingEventBuilder,LoggingEventBuilder> conditional=ConditionalHolder.registry.get(id);
        if (conditional==null) {
            Conditional2.Builder<Context,LoggingEventBuilder,LoggingEventBuilder> builder=createPresetBuilder(id);
            builderConsumer.accept(builder);
            conditional=builder.build();
            ConditionalHolder.registry.put(id,conditional);
        }

        return conditional.evaluate(source);
    }

    private static Conditional2.Builder<Context,LoggingEventBuilder,LoggingEventBuilder> createPresetBuilder(String id) {
        Conditional2.Builder<Context,LoggingEventBuilder,LoggingEventBuilder> builder=Conditional2.builder();
        builder.id(id);
        Context context=new Context();
        builder.contextSupplier(()->context);
        builder.onAccept(Context::onAccept);
        builder.onReject(Context::onReject);
        builder.neutralSourceSupplier(NOPLoggingEventBuilder::singleton);
        builder.transformation(Function.identity());
        return builder;
    }

    public static void main(String[] args) {

        Slip.of(log.atDebug()).nop().exec().log("Logging!");
        Slip.of(log.atDebug()).nop().exec().setMessage("Logging!").log();
        Slip.of(log.atDebug()).nop().exec(leb->leb.setMessage("Logging!").log());
        Slip.of(log.atDebug()).nop().exec((c,leb)->leb.setMessage("Logging!").log());

        Conditional.of(log.atDebug()).exec().log("Logging!");

        for (int i=0; i<10; i++) {
//            Conditional2.of("3f35",b->b.limit(5)).exec(log.atInfo(),(c,leb)->leb.log("(1)Logging done; suppressed {}.",c.suppressed()));
            int finalI=i;
            Slip.of(log.atInfo()).id("3f35",b->b.limit(5)).exec((c,leb)->leb.log("(2)Logging done; index {}, suppressed {}, accepted {}, rejected {}.", finalI,c.suppressed(),c.accepted(),c.rejected()));

            Threads.sleep(Duration.ofMillis(100));
        }
    }
}
