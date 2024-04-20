package com.yelstream.topp.standard.microprofile.config.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test of {@link FixedMapConfigSource}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-20
 */
@Slf4j
class FixedMapConfigSourceTest {
    @Test
    void create() {
        FixedMapConfigSource configSource=FixedMapConfigSource.of("name-1",100,new HashMap<>());
        Assertions.assertNotNull(configSource);
        Assertions.assertEquals("name-1",configSource.getName());
        Assertions.assertEquals(100,configSource.getOrdinal());
    }

    @Test
    void createByBuilder() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        FixedMapConfigSource configSource=builder.build();
        Assertions.assertNotNull(configSource);
        Assertions.assertNotNull(configSource.getName());
        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertNotNull(configSource.getProperties());
        Assertions.assertEquals(0,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithAllSet() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        builder.name("name-1").ordinal(100).properties(new HashMap<>());
        FixedMapConfigSource configSource=builder.build();
        Assertions.assertNotNull(configSource);
        Assertions.assertEquals("name-1",configSource.getName());
        Assertions.assertEquals(100,configSource.getOrdinal());
        Assertions.assertNotNull(configSource.getProperties());
        Assertions.assertEquals(0,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithPropertyMethods() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        builder.clearProperties().property("name-1","value-1").properties(new HashMap<>());
        FixedMapConfigSource configSource=builder.build();
        Assertions.assertNotNull(configSource);
        Assertions.assertNotNull(configSource.getName());
        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertEquals("value-1",configSource.getValue("name-1"));
    }

    @Test
    void toBuilder() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        FixedMapConfigSource configSource=builder.build();
        builder=configSource.toBuilder();
        Assertions.assertNotNull(builder);
        Assertions.assertNotNull(configSource.getName());
        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertNotNull(configSource.getProperties());
    }

    @Test
    void immutableProperties() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        builder.property("name-1","value-1");
        FixedMapConfigSource configSource=builder.build();

        Assertions.assertNotNull(configSource);

        Map<String,String> properties=configSource.getProperties();
        Assertions.assertThrows(UnsupportedOperationException.class,()-> {
            properties.put("name-1","value-2");
        });
    }
}
