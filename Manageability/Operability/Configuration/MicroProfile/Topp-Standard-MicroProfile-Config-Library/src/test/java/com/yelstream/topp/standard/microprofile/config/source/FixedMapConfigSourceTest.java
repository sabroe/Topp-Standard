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
        {
            FixedMapConfigSource configSource=FixedMapConfigSource.of("name-1",100,null);

            ConfigSourceTests.assertConfigSourceSemantics(configSource);
        }
        {
            FixedMapConfigSource configSource=FixedMapConfigSource.of("name-1",100,new HashMap<>());

            ConfigSourceTests.assertConfigSourceSemantics(configSource);

            Assertions.assertEquals("name-1",configSource.getName());
            Assertions.assertEquals(100,configSource.getOrdinal());
            Assertions.assertEquals(0,configSource.getProperties().size());
        }
    }

    @Test
    void createByBuilder() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        FixedMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertEquals(0,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithAllSet() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        builder.name("name-1").ordinal(100).properties(new HashMap<>());
        FixedMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        Assertions.assertEquals("name-1",configSource.getName());
        Assertions.assertEquals(100,configSource.getOrdinal());
        Assertions.assertEquals(0,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithPropertyMethods() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        builder.clearProperties().property("name-1","value-1").properties(new HashMap<>());
        FixedMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertEquals("value-1",configSource.getValue("name-1"));
    }

    @Test
    void toBuilder() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        builder.name("name-1").ordinal(100).property("name-1","value-1");
        FixedMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        builder=configSource.toBuilder();
        builder.name("name-2").ordinal(200).property("name-1","value-2");
        configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);
    }

    @Test
    void immutableProperties() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        builder.property("name-1","value-1");
        FixedMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        Map<String,String> properties=configSource.getProperties();
        Assertions.assertThrows(UnsupportedOperationException.class,()-> {
            properties.put("name-1","value-2");
        });
    }

    @Test
    void carriesNullPropertyValues() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        builder.property("name-1",null);
        FixedMapConfigSource configSource=builder.build();

        ConfigSourceTests.assertConfigSourceSemantics(configSource);

        Assertions.assertEquals(1,configSource.getProperties().size());
        Assertions.assertEquals(1,configSource.getPropertyNames().size());
        Assertions.assertNull(configSource.getValue("name-1"));
    }
}
