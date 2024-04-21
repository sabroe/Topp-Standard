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
