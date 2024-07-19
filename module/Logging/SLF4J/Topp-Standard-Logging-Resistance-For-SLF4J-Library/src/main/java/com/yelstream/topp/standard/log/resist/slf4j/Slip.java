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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
            //TO-DO: Add ... 'forced', 'forcedAccept', 'forcedReject'?
        }

        private final AtomicReference<State> stateRef = new AtomicReference<>(
            new State()
        );

        public State state() {
            return stateRef.get();
        }

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

    public FilterResult<Context,LoggingEventBuilder> nop() {
        Conditional2<Context,LoggingEventBuilder,LoggingEventBuilder> conditional=Conditional2.identity();
        return conditional.evaluate(source);
    }

    private static class ConditionalHolder {
        private static final Map<String,Conditional2<Context,LoggingEventBuilder,LoggingEventBuilder>> registry=new ConcurrentHashMap<>();
    }

    public FilterResult<Context,LoggingEventBuilder> id(String id, Consumer<Conditional2.Builder<Context,LoggingEventBuilder,LoggingEventBuilder>> builderConsumer) {
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
}
