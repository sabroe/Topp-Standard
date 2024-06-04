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

package com.yelstream.topp.standard.time;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.function.LongFunction;
import java.util.function.ToLongFunction;
import java.util.random.RandomGenerator;

/**
 * Generator of random durations.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-26
 */
public class RandomDurationGenerator implements DurationGenerator {
    /**
     * Generator of random numbers.
     */
    private final RandomGenerator random;

    /**
     * Minimum duration.
     */
    @SuppressWarnings({"java:S1068","unused","FieldCanBeLocal"})
    private final Duration minDuration;

    /**
     * Maximum duration.
     */
    @SuppressWarnings({"java:S1068","unused","FieldCanBeLocal"})
    private final Duration maxDuration;

    @SuppressWarnings({"FieldCanBeLocal"})
    private final ToLongFunction<Duration> toAmount;

    private final LongFunction<Duration> fromAmount;

    private final long minAmount;

    @SuppressWarnings({"FieldCanBeLocal"})
    private final long maxAmount;

    private final long spanAmount;

    private RandomDurationGenerator(RandomGenerator random,
                                    Duration minDuration,
                                    Duration maxDuration) {
        if (minDuration.compareTo(maxDuration)>0) {
            throw new IllegalArgumentException(String.format("Failure to create random duration generator; minimum duration '%s' must be less than maximum duration '%s'!", minDuration,maxDuration));
        }

        this.random=random;
        this.minDuration=minDuration;
        this.maxDuration=maxDuration;

        Duration span=maxDuration.minus(minDuration);  //Note that this is signed!
        Duration unsignedSpan=span.abs();
        Duration maxSpanForComputationInNanos=Duration.ofNanos(Long.MAX_VALUE);
        if (unsignedSpan.compareTo(maxSpanForComputationInNanos)<0) {
            toAmount=Duration::toNanos;
            fromAmount=Duration::ofNanos;
        } else {
            toAmount=Duration::toSeconds;
            fromAmount=Duration::ofSeconds;
        }

        minAmount=toAmount.applyAsLong(minDuration);
        maxAmount=toAmount.applyAsLong(maxDuration);
        spanAmount=maxAmount-minAmount+1;
    }

    @Override
    public Duration nextDuration() {
        long randomInSpanAmount=random.nextLong(spanAmount);  //[0..span[
        long inRangeAmount=minAmount+randomInSpanAmount;  //[min..min+span[ = [min..min+(max-min+1)[ = [min..max+1[  = [min..max]
        return fromAmount.apply(inRangeAmount);
    }

    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    public static RandomDurationGenerator of(RandomGenerator random,
                                             Duration minDuration,
                                             Duration maxDuration) {
        return new RandomDurationGenerator(random,minDuration,maxDuration);
    }

    public static RandomDurationGenerator of(Duration minDuration,
                                             Duration maxDuration) {
        return builder().durationRange(minDuration,maxDuration).build();
    }

    @SuppressWarnings({"FieldMayBeFinal","FieldCanBeLocal","unused","java:S1450"})
    public static class Builder {
        private RandomGenerator random=new SecureRandom();

        private Duration minDuration;

        private Duration maxDuration;

        public Builder durationRange(Duration minDuration,
                                     Duration maxDuration) {
            this.minDuration=minDuration;
            this.maxDuration=maxDuration;
            return this;
        }
    }
}
