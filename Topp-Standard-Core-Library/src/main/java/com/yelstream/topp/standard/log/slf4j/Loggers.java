package com.yelstream.topp.standard.log.slf4j;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.Comparator;
import java.util.List;

@Slf4j
@UtilityClass
public class Loggers {

    public static Logger getLogger(String loggerName) {
        return LoggerFactory.getLogger(loggerName);
    }

    public static Level getLevel(Logger logger) {
        List<Level> levels=List.of(Level.values());
        List<Level> activeLevels=levels.stream().filter(log::isEnabledForLevel).sorted(Comparator.comparingInt(Level::toInt)).toList();  //E.g. {INFO=20,WARN=30, ERROR=40}, deepest level is the lowest number
        log.atDebug().setMessage("Active levels are {}.").addArgument(activeLevels).log();
        return activeLevels.getFirst();
    }
}
