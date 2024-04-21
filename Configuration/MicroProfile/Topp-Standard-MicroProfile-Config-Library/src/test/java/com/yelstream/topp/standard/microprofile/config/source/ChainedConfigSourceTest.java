package com.yelstream.topp.standard.microprofile.config.source;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Test of {@link ChainedConfigSource}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-20
 */
@Slf4j
class ChainedConfigSourceTest {

    @Test
    void create() {
        {
            ChainedConfigSource configSource=ChainedConfigSource.of("name-1",100,null);

            ConfigSourceTests.assertConfigSourceSemantics(configSource);

            Assertions.assertEquals("name-1", configSource.getName());
            Assertions.assertEquals(100, configSource.getOrdinal());
            Assertions.assertNull(configSource.getValue("xxx"));
        }
        {
            List<ConfigSource> emptyConfigSources=List.of();
            ChainedConfigSource configSource=ChainedConfigSource.of("name-1",100,emptyConfigSources);

            ConfigSourceTests.assertConfigSourceSemantics(configSource);

            Assertions.assertEquals("name-1", configSource.getName());
            Assertions.assertEquals(100, configSource.getOrdinal());
            Assertions.assertEquals(0, configSource.getProperties().size());
        }
        {
            List<ConfigSource> configSources=
                List.of(ConfigSources.createEmptyConfigSource(),
                        FixedMapConfigSource.builder().property("name-1","value-1").build(),
                        FixedMapConfigSource.builder().property("name-1","value-2").build());
            ChainedConfigSource configSource=ChainedConfigSource.of("name-1",100,configSources);

            ConfigSourceTests.assertConfigSourceSemantics(configSource);

            Assertions.assertEquals("name-1", configSource.getName());
            Assertions.assertEquals(100, configSource.getOrdinal());

            Assertions.assertEquals(Set.of("name-1"),configSource.getPropertyNames());
            Assertions.assertEquals(Map.ofEntries(Map.entry("name-1","value-1")),configSource.getProperties());
            Assertions.assertEquals("value-1", configSource.getValue("name-1"));
        }
        {
            List<ConfigSource> configSources=
                List.of(ConfigSources.createEmptyConfigSource(),
                        FixedMapConfigSource.builder().property("name-1","value-1").build(),
                        FixedMapConfigSource.builder().property("name-1","value-2").build(),
                        FixedMapConfigSource.builder().property("name-2",null).build(),
                        FixedMapConfigSource.builder().property("name-2","value-2").build());
            ChainedConfigSource configSource=ChainedConfigSource.of("name-1",100,configSources);

            ConfigSourceTests.assertConfigSourceSemantics(configSource);

            Assertions.assertEquals("name-1", configSource.getName());
            Assertions.assertEquals(100, configSource.getOrdinal());

            Assertions.assertEquals(Set.of("name-1","name-2"),configSource.getPropertyNames());
            Map<String,String> map=new HashMap<>();
            map.put("name-1","value-1");
            map.put("name-2",null);
            Assertions.assertEquals(map,configSource.getProperties());
            Assertions.assertEquals("value-1",configSource.getValue("name-1"));
            Assertions.assertNull(configSource.getValue("name-2"));
        }
    }

    @Test
    void createByBuilder() {
        {
            ChainedConfigSource.Builder builder=ChainedConfigSource.builder();
            builder.name("name-1").ordinal(100);
            Assertions.assertThrows(NullPointerException.class,()-> {
                builder.configSources(null);
            });
        }
        {
            List<ConfigSource> emptyConfigSources=List.of();
            ChainedConfigSource.Builder builder=ChainedConfigSource.builder();
            builder.name("name-1").ordinal(100).configSources(emptyConfigSources);
            ChainedConfigSource configSource=builder.build();

            ConfigSourceTests.assertConfigSourceSemantics(configSource);

            Assertions.assertEquals("name-1", configSource.getName());
            Assertions.assertEquals(100, configSource.getOrdinal());
            Assertions.assertEquals(0, configSource.getProperties().size());
        }
        {
            List<ConfigSource> configSources=
                    List.of(ConfigSources.createEmptyConfigSource(),
                            FixedMapConfigSource.builder().property("name-1","value-1").build(),
                            FixedMapConfigSource.builder().property("name-1","value-2").build());
            ChainedConfigSource.Builder builder=ChainedConfigSource.builder();
            builder.name("name-1").ordinal(100).configSources(configSources);
            ChainedConfigSource configSource=builder.build();

            ConfigSourceTests.assertConfigSourceSemantics(configSource);

            Assertions.assertEquals("name-1", configSource.getName());
            Assertions.assertEquals(100, configSource.getOrdinal());

            Assertions.assertEquals(Set.of("name-1"),configSource.getPropertyNames());
            Assertions.assertEquals(Map.ofEntries(Map.entry("name-1","value-1")),configSource.getProperties());
            Assertions.assertEquals("value-1", configSource.getValue("name-1"));
        }
        {
            List<ConfigSource> configSources=
                    List.of(ConfigSources.createEmptyConfigSource(),
                            FixedMapConfigSource.builder().property("name-1","value-1").build(),
                            FixedMapConfigSource.builder().property("name-1","value-2").build(),
                            FixedMapConfigSource.builder().property("name-2",null).build(),
                            FixedMapConfigSource.builder().property("name-2","value-2").build());
            ChainedConfigSource.Builder builder=ChainedConfigSource.builder();
            builder.name("name-1").ordinal(100).configSources(configSources);
            ChainedConfigSource configSource=builder.build();

            ConfigSourceTests.assertConfigSourceSemantics(configSource);

            Assertions.assertEquals("name-1", configSource.getName());
            Assertions.assertEquals(100, configSource.getOrdinal());

            Assertions.assertEquals(Set.of("name-1","name-2"),configSource.getPropertyNames());
            Map<String,String> map=new HashMap<>();
            map.put("name-1","value-1");
            map.put("name-2",null);
            Assertions.assertEquals(map,configSource.getProperties());
            Assertions.assertEquals("value-1",configSource.getValue("name-1"));
            Assertions.assertNull(configSource.getValue("name-2"));
        }
    }
}
