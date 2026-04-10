package com.yelstream.topp.standard.time.view;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.Instant;
import java.time.InstantSource;
import java.time.temporal.Temporal;
import java.util.Date;

@AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class TimeViewCreate {
    public TimeView from(Date date) {
        return TimeView.of(date);
    }

    public TimeView from(Instant instant) {
        return TimeView.of(instant);
    }

    public TimeView from(InstantSource instantSource) {
        return TimeView.of(instantSource);
    }

    public TimeView from(Temporal temporal) {
        return TimeView.of(Instant.from(temporal));
    }

    public TimeView now() {
        return TimeView.of(Instant.now());
    }

    public TimeView now(Clock clock) {
        return TimeView.of(clock);
    }
}
