package com.yelstream.topp.standard.time.view;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class TimeViewTo {
    private final TimeView view;

    public Instant instant() {
        return view.toInstant();
    }

    public Date date() {
        return view.toDate();
    }

    public ZonedView zone(ZoneId zone) {
        return view.at(zone);
    }
}
