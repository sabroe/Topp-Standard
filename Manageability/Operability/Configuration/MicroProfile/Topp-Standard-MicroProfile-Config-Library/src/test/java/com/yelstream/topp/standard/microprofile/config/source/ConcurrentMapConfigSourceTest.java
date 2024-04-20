package com.yelstream.topp.standard.microprofile.config.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Test of {@link ConcurrentMapConfigSource}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-20
 */
@Slf4j
class ConcurrentMapConfigSourceTest {
    @Test
    void create() {
        ConcurrentMapConfigSource configSource=ConcurrentMapConfigSource.of("name-1",100,new ConcurrentHashMap<>());
        Assertions.assertNotNull(configSource);
        Assertions.assertEquals("name-1",configSource.getName());
        Assertions.assertEquals(100,configSource.getOrdinal());
    }

    @Test
    void createByBuilder() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        ConcurrentMapConfigSource configSource=builder.build();
        Assertions.assertNotNull(configSource);
        Assertions.assertNotNull(configSource.getName());
        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertNotNull(configSource.getProperties());
        Assertions.assertEquals(0,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithAllSet() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        builder.name("name-1").ordinal(100).properties(new HashMap<>());
        ConcurrentMapConfigSource configSource=builder.build();
        Assertions.assertNotNull(configSource);
        Assertions.assertEquals("name-1",configSource.getName());
        Assertions.assertEquals(100,configSource.getOrdinal());
        Assertions.assertNotNull(configSource.getProperties());
        Assertions.assertEquals(0,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithPropertyMethods() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        builder.clearProperties().property("name-1","value-1").properties(new HashMap<>());
        ConcurrentMapConfigSource configSource=builder.build();
        Assertions.assertNotNull(configSource);
        Assertions.assertNotNull(configSource.getName());
        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertEquals("value-1",configSource.getValue("name-1"));
    }

    @Test
    void toBuilder() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        ConcurrentMapConfigSource configSource=builder.build();
        builder=configSource.toBuilder();
        Assertions.assertNotNull(builder);
        Assertions.assertNotNull(configSource.getName());
        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertNotNull(configSource.getProperties());
    }

    @Test
    void mutableProperties() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        builder.property("name-1","value-1");
        ConcurrentMapConfigSource configSource=builder.build();

        Assertions.assertNotNull(configSource);

        Map<String,String> properties=configSource.getProperties();
        properties.put("name-1","value-2");
        Assertions.assertEquals("value-2",configSource.getValue("name-1"));
    }
}
