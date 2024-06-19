package com.yelstream.topp.standard.log.slf4j.event;

import lombok.experimental.UtilityClass;
import org.slf4j.event.Level;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Utility addressing instances of {@link Level}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-18
 */
@UtilityClass
public class Levels {
    /**
     * All levels sorted.
     * <p>
     *     { TRACE=00, DEBUG=10, INFO=20, WARN=30, ERROR=40 }
     * </p>
     */
    private static final List<Level> SORTED_LEVELS=
        Stream.of(Level.values()).sorted(Comparator.comparingInt(Level::toInt)).toList();  //Yes, this is unmodifiable!

    /**
     * Gets all levels in sorted order.
     * @return Levels in sorted order.
     *         From {@link Level#TRACE} to {@link Level#ERROR}.
     *         This is immutable.
     */
    public static List<Level> getLevelsSorted() {
        return SORTED_LEVELS;
    }
}
