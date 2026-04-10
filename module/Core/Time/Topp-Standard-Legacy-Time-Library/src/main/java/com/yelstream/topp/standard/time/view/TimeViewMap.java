package com.yelstream.topp.standard.time.view;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class TimeViewMap {
    private final TimeView view;

    public TimeView map(java.util.function.UnaryOperator<java.time.Instant> operator) {
        return view.map(operator);
    }

    public TimeView plus(long amount, java.time.temporal.TemporalUnit unit) {
        return view.plus(amount, unit);
    }

    public TimeView plus(java.time.temporal.TemporalAmount amount) {
        return view.plus(amount);
    }

    public TimeView minus(long amount, java.time.temporal.TemporalUnit unit) {
        return view.minus(amount, unit);
    }

    public TimeView minus(java.time.temporal.TemporalAmount amount) {
        return view.minus(amount);
    }
}
