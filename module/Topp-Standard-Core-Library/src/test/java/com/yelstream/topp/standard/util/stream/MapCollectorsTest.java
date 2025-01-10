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

package com.yelstream.topp.standard.util.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test of {@link MapCollectors}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-21
 */
@Slf4j
class MapCollectorsTest {
    @Test
    void basicMerge() {
        List<Map<String, String>> listOfMaps = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key-1", null);
        map1.put("key-2", "value-2");
        Map<String, String> map2 = new HashMap<>();
        map2.put("key-1", "value-1");
        map2.put("key-2", "value-3");
        listOfMaps.add(map1);
        listOfMaps.add(map2);

        Map<String, String> mergedMap=
            listOfMaps
                .stream()
                .flatMap(map -> map.entrySet().stream())
                .collect(MapCollectors.toHashMap(Map.Entry::getKey,Map.Entry::getValue));

        Assertions.assertEquals(2,mergedMap.size());
        Map<String,String> map=new HashMap<>();
        map.put("key-1",null);
        map.put("key-2","value-2");
        Assertions.assertEquals(map,mergedMap);
    }
}
