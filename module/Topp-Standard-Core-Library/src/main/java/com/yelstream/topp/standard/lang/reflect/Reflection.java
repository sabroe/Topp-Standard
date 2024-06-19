package com.yelstream.topp.standard.lang.reflect;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * Utility addressing selected operations depending upon reflection.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-18
 */
@Slf4j
@UtilityClass
public class Reflection {
    /**
     * Safe cast of an object.
     * @param objectClass Class to cast to.
     * @param object Object to cast.
     * @return Down-casted object.
     * @param <T> Type of object type down-casted to.
     */
    public static <T> T safeCast(Class<T> objectClass,
                                 Object object) {  //TO-DO: Consider this; technically it has nothing to do with reflection!
        T result;
        if (!objectClass.isInstance(object)) {
            throw new IllegalStateException(String.format("Failure to cast object; expected class is %s, actual class is %s!",objectClass,object.getClass()));
        } else {
            result=objectClass.cast(object);
        }
        return result;
    }

    /**
     * Gets access to an otherwise un-accessible field.
     * @param object Object to access field within.
     * @param fieldName Name of field.
     * @param fieldClass Class of field.
     * @return Field.
     * @param <T> Type of field object.
     */
    @SuppressWarnings("java:S3011")
    public static <T> T getField(Object object,
                                 String fieldName,
                                 Class<T> fieldClass) {
        T field=null;
        Class<?> objectClass=object.getClass();
        try {
            Field loggerField=object.getClass().getDeclaredField(fieldName);
            loggerField.setAccessible(true);
            field=safeCast(fieldClass,loggerField.get(object));
        } catch (NoSuchFieldException ex) {
            log.error("Failure to get field; field does not exist, object class is {}, field is {}!",objectClass,fieldName);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException(String.format("Failure to get field; field cannot be accessed, object class is %s, field is %s!",objectClass,fieldName),ex);
        }
        return field;
    }
}
