package com.yelstream.topp.standard.time.view;

import com.yelstream.topp.standard.time.policy.Policy;
import lombok.AllArgsConstructor;

import java.time.Instant;

/**
 * behavior + policy
 *
 */
@AllArgsConstructor(staticName = "of")
public class TimeFlow {
    /**
     * Absolute time.
     */
    private final Instant instant;

    /**
     * Nullability Policy.
     */
    private final Policy policy;

/*
timeFlow.ofNullable(time)
            .nullAware()
    .map(...)
    .toTime();



Time.ofNullable(date)
    .flow()
    .nullable()
    .map(...)
    .toDate();


'Time' entrypoints:
    public static TimeFlow of(Time time);
    public static TimeFlow ofNullable(Time time);


*/



}
