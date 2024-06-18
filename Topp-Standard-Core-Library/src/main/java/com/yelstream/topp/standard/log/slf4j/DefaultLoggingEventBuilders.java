package com.yelstream.topp.standard.log.slf4j;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.event.DefaultLoggingEvent;
import org.slf4j.event.Level;
import org.slf4j.spi.DefaultLoggingEventBuilder;

import java.lang.reflect.Field;

@Slf4j
@UtilityClass
public class DefaultLoggingEventBuilders {

    public static <T> T safeCast(Class<T> objectClass,
                                 Object object) {  //TO-DO: Move to ... 'Objects'?
        T result;
        if (!objectClass.isInstance(object)) {
            throw new IllegalStateException(String.format("Failure to cast object; expected class is %s, actual class is %s!",objectClass,object.getClass()));
        } else {
            result=objectClass.cast(object);
        }
        return result;
    }

    @SuppressWarnings("java:S3011")
    public static <T> T getField(Object object,
                                 String fieldName,
                                 Class<T> fieldClass) {  //TO-DO: Move to ... 'Objects'?
        T field=null;
        Class<?> objectClass=object.getClass();
        try {
            Field loggerField=object.getClass().getDeclaredField(fieldName);
            loggerField.setAccessible(true);
            field=safeCast(fieldClass,loggerField.get(object));
        } catch (NoSuchFieldException ex) {
            log.error("Failure to get field; field does not exist, object class is {}, field is {}!",objectClass,LOGGER_FIELD_NAME);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(String.format("Failure to get field; field cannot be accessed, object class is %s, field is %s!",objectClass,LOGGER_FIELD_NAME),ex);
        }
        return field;
    }

    public static final String LOGGER_FIELD_NAME="logger";

    public static final String LOGGING_EVENT_FIELD_NAME="loggingEvent";

    public static Logger getLogger(DefaultLoggingEventBuilder builder) {
        return getField(builder,LOGGER_FIELD_NAME,Logger.class);
    }

    public static DefaultLoggingEvent getLoggingEvent(DefaultLoggingEventBuilder builder) {
        return getField(builder,LOGGING_EVENT_FIELD_NAME,DefaultLoggingEvent.class);
    }

    public static Level getLevel(DefaultLoggingEventBuilder builder) {
        DefaultLoggingEvent loggingEvent=getLoggingEvent(builder);
        return loggingEvent.getLevel();
    }
}
