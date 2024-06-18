package com.yelstream.topp.standard.log.slf4j;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.slf4j.helpers.NOPLogger;
import org.slf4j.spi.DefaultLoggingEventBuilder;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

@UtilityClass
public class LoggingEventBuilders {


    public static boolean isLoggingEnabled(LoggingEventBuilder loggingEventBuilder) {
        return loggingEventBuilder!=NOPLoggingEventBuilder.singleton();
    }

    public static String getLoggerName(LoggingEventBuilder loggingEventBuilder) {
        String name=null;
        Logger logger=getLogger(loggingEventBuilder);
        if (logger!=null) {
            name=logger.getName();
        }
        return name;
    }

    @SuppressWarnings("RedundantLabeledSwitchRuleCodeBlock")
    public static Logger getLogger(LoggingEventBuilder loggingEventBuilder) {
        Logger logger;
        switch (loggingEventBuilder) {
            case DefaultLoggingEventBuilder lev -> {
                logger=DefaultLoggingEventBuilders.getLogger(lev);
            }
            case NOPLoggingEventBuilder ignored -> {
                logger=NOPLogger.NOP_LOGGER;
            }
            default -> {
                logger=null;
            }
        }
        return logger;
    }

    public static Level getLevelForLogger(LoggingEventBuilder loggingEventBuilder) {
        Level level=null;
        Logger logger=getLogger(loggingEventBuilder);
        if (logger!=null) {
            level=Loggers.getLevel(logger);
        }
        return level;
    }

    @SuppressWarnings({"RedundantLabeledSwitchRuleCodeBlock","DuplicateBranchesInSwitch"})
    public static Level getLevelForStatement(LoggingEventBuilder loggingEventBuilder) {
        Level level;
        switch (loggingEventBuilder) {
            case DefaultLoggingEventBuilder lev -> {
                level=DefaultLoggingEventBuilders.getLevel(lev);
            }
            case NOPLoggingEventBuilder ignored -> {
                level=null;
            }
            default -> {
                level=null;
            }
        }
        return level;
    }
}
