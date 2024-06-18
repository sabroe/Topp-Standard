package com.yelstream.topp.standard.log.slf4j;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class Loggers {

    public static List<Level> getEnabledLevels(Logger logger) {
        List<Level> enabledLevels=Levels.SORTED_LEVELS.stream().filter(logger::isEnabledForLevel).toList();  //E.g. {INFO=20,WARN=30, ERROR=40}, deepest level is the lowest number
        log.atDebug().setMessage("Enabled levels are {}.").addArgument(enabledLevels).log();
        return enabledLevels;
    }

    public static Logger getLogger(String loggerName) {
        return LoggerFactory.getLogger(loggerName);
    }

    public static Level getLevel(Logger logger) {
        return Levels.SORTED_LEVELS.stream().filter(logger::isEnabledForLevel).findFirst().orElse(null);
    }
}
