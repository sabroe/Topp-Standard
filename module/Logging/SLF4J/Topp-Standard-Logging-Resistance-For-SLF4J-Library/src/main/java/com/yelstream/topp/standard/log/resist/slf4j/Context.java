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

package com.yelstream.topp.standard.log.resist.slf4j;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Log filtering context.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-22
 */
@Slf4j
@AllArgsConstructor(staticName="of",access=AccessLevel.PACKAGE)
public class Context {  //TO-DO: Consider placement and typing; this is not really specific for SLF4J!
    @Getter
    @NoArgsConstructor(force=true)
    @AllArgsConstructor
    @ToString
    public static class State {
        private final long index;
        private final long rejectCount;
        private final long acceptCount;
        private final long suppressedCount;
        private final long nextSuppressedCount;
        //TO-DO: Add ... 'forced', 'forcedAccept', 'forcedReject'?
    }

    private final AtomicReference<State> stateRef=new AtomicReference<>(new State());

    public State state() {
        return stateRef.get();
    }

    State updateStateByAccept() {
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

    State updateStateByReject() {
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
