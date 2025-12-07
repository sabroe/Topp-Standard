/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
