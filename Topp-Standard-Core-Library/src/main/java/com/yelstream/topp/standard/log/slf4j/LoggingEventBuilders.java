package com.yelstream.topp.standard.log.slf4j;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.slf4j.spi.DefaultLoggingEventBuilder;
import org.slf4j.spi.LoggingEventBuilder;
import org.slf4j.spi.NOPLoggingEventBuilder;

@UtilityClass
public class LoggingEventBuilders {
    @Getter
    @SuppressWarnings("java:S115")
    @AllArgsConstructor
    public enum Enablement {  //TO-DO: Remove!
        Disabled(Boolean.FALSE),
        Enabled(Boolean.TRUE),
        Unknown(null);

        private final Boolean enabled;
    }

    public static Enablement getEnablement(LoggingEventBuilder loggingEventBuilder) {  //TO-DO: Remove!
        return switch (loggingEventBuilder) {
            case NOPLoggingEventBuilder ignored -> Enablement.Disabled;
            case DefaultLoggingEventBuilder ignored -> Enablement.Enabled;
            default -> Enablement.Unknown;
        };
    }

    public static boolean isLoggingEnabled(LoggingEventBuilder loggingEventBuilder) {
        return loggingEventBuilder!=NOPLoggingEventBuilder.singleton();
    }
}
