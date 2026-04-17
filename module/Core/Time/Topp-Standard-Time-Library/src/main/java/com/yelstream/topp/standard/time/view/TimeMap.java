/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.time.view;

import com.yelstream.topp.standard.time.policy.NullPolicies;
import com.yelstream.topp.standard.time.policy.NullPolicy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Capability view on transforming {@link Time} instances.
 * <p>
 *     This is a fluent helper.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-10
 */
@AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class TimeMap {
    /**
     * Absolute time.
     */
    private final Time time;

    /**
     * Null policy.
     */
    private final NullPolicy policy;

    public Time time() {
        return time;
    }

    private Time apply(UnaryOperator<Time> transformer) {
        return NullPolicies.map(policy, time, transformer, () -> null);
    }

    private Time applyInstant(UnaryOperator<Instant> transformer) {
        return NullPolicies.map(policy, time, t -> Time.of(transformer.apply(t.toInstant())), () -> null);
    }

    public TimeMap map(UnaryOperator<Time> transformer) {
        return of(apply(transformer),policy);
    }

    public TimeMap mapInstant(UnaryOperator<Instant> transformer) {
        return of(applyInstant(transformer), policy);
    }

    public <V> V from(Function<Time, V> extractor) {  //TO-DO: Use Time or Instant?
        return NullPolicies.from(policy, time, extractor, () -> null);
    }

    public void consume(Consumer<Time> consumer) {  //TO-DO: Use Time or Instant?
        NullPolicies.consume(policy, time, consumer);
    }

    public TimeMap plus(long amount,
                        TemporalUnit unit) {
        return mapInstant(t-> t.plus(amount, unit));
    }

    public TimeMap plus(TemporalAmount amount) {
        return mapInstant(t-> t.plus(amount));
    }

    public TimeMap minus(long amount,
                         TemporalUnit unit) {
        return mapInstant(t-> t.minus(amount, unit));
    }

    public TimeMap minus(TemporalAmount amount) {
        return mapInstant(t-> t.minus(amount));
    }
}
