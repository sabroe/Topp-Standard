package com.yelstream.topp.standard.log.slf4j;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class Levels {
    @SuppressWarnings("java:S2386")  //Yak!
    public static final List<Level> SORTED_LEVELS=
            Stream.of(Level.values()).sorted(Comparator.comparingInt(Level::toInt)).toList();  //Yes, this is unmodifiable!
}
