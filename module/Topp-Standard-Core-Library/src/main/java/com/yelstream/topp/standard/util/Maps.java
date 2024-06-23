package com.yelstream.topp.standard.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility addressing instances of {@link Map}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-23
 */
@UtilityClass
public class Maps {
    /**
     * Converts a map of object values to a map of string values.
     * @param map Map to convert.
     * @return Created map.
     * @param <K> Type of key.
     */
    public static <K> Map<K,String> convertMapValueToString(Map<K,Object> map) {
        Map<K,String> result=null;
        if (map!=null) {
            result=new HashMap<>();  //Yes, do allow for 'null' values within resulting map; use 'HashMap'!
            for (Map.Entry<K,Object> entry: map.entrySet()) {
                K key=entry.getKey();
                Object value=entry.getValue();
                result.put(key,value==null?null:value.toString());
            }
        }
        return result;
    }

    /**
     * Converts a map of string values to a map of object values.
     * @param map Map to convert.
     * @return Created map.
     * @param <K> Type of key.
     */
    public static <K> Map<K,Object> convertMapValueToObject(Map<K,String> map) {
        Map<K,Object> result=null;
        if (map!=null) {
            result=new HashMap<>(map);  //Yes, do allow for 'null' values within resulting map; use 'HashMap'!
        }
        return result;
    }
}
