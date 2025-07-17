/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.net.resource.identification.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-09
 */
public class MappedQuery {
    /**
     * Map of query parameters.
     * <p>
     *     Note that this keeps the insertion order!
     * </p>
     */
    private final Map<String,List<String>> multiMap=new LinkedHashMap<>();

    private MappedQuery() {
    }

    private MappedQuery(String query) {
        if (query!=null && !query.isEmpty()) {
            Map<String,List<String>> multiMap=parseToMultiMap(query);
            this.multiMap.putAll(multiMap);
        }
    }

    public void add(String key,
                    String value) {
        multiMap.computeIfAbsent(key,k->new ArrayList<>()).add(value);
    }

    public void add(String key,
                    List<String> values) {
        multiMap.computeIfAbsent(key,k->new ArrayList<>()).addAll(values);
    }

    public String value(String key) {
        return multiMap.get(key).getFirst();  //TO-DO: Check for presence of a first element, else exception!
    }

    public List<String> values(String key) {
        return multiMap.get(key);  //TO-DO: Immutable?
    }

    public void remove(String key) {
        //TO-DO: Fix!
    }

    public void remove(String key,
                       String value) {
        //TO-DO: Fix!
    }

    public void remove(String key,
                       List<String> values) {
        //TO-DO: Fix!
    }

    public static Map<String, List<String>> parseToMultiMap(String query) {
        Map<String, List<String>> map = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key=pair.substring(0, idx);
            String value=pair.substring(idx + 1);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }
        return map;
    }

    public static String formatFromMultiMap(Map<String, List<String>> map) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            for (String value : entry.getValue()) {
                if (!result.isEmpty()) {
                    result.append("&");
                }
                result.append(entry.getKey())
                        .append("=")
                        .append(value);
            }
        }
        return result.toString();
    }

    public String formatAsString() {
        String queryString=null;
        if (!multiMap.isEmpty()) {
            queryString = multiMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(value -> entry.getKey() + "=" + value))
                .collect(Collectors.joining("&"));
        }
        return queryString;
    }

    public static MappedQuery of(String query) {
        return new MappedQuery(query);
    }
}
