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
        {
            ConcurrentMapConfigSource configSource=ConcurrentMapConfigSource.of("name-1",100,null);

            ConfigSourceTests.assertConfigSourceSemantics(configSource);
        }
        {
            ConcurrentMapConfigSource configSource=ConcurrentMapConfigSource.of("name-1",100,new ConcurrentHashMap<>());

            ConfigSourceTests.assertConfigSourceSemantics(configSource);

            Assertions.assertEquals("name-1",configSource.getName());
            Assertions.assertEquals(100,configSource.getOrdinal());
            Assertions.assertNotNull(configSource.getProperties());
            Assertions.assertEquals(0,configSource.getProperties().size());
        }
    }


    @Test
    void createByBuilder() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        ConcurrentMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertNotNull(configSource.getProperties());
        Assertions.assertEquals(0,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithAllSet() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        builder.name("name-1").ordinal(100).properties(new HashMap<>());
        ConcurrentMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

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

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertEquals("value-1",configSource.getValue("name-1"));
    }

    @Test
    void toBuilder() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        builder.name("name-1").ordinal(100).property("name-1","value-1");
        ConcurrentMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        builder=configSource.toBuilder();
        builder.name("name-2").ordinal(200).property("name-1","value-2");
        configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);
    }

    @Test
    void mutableProperties() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        builder.property("name-1","value-1");
        ConcurrentMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        Map<String,String> properties=configSource.getProperties();
        properties.put("name-1","value-2");
        Assertions.assertEquals("value-2",configSource.getValue("name-1"));
    }

    @Test
    void carriesNullPropertyValues() {
        ConcurrentMapConfigSource.Builder builder=ConcurrentMapConfigSource.builder();
        builder.property("name-1",null);

        Assertions.assertThrows(NullPointerException.class,builder::build);
    }
}
