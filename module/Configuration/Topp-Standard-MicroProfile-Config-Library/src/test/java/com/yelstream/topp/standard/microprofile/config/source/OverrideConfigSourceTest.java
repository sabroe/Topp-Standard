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

package com.yelstream.topp.standard.microprofile.config.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * Test of {@link OverrideConfigSource}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-20
 */
@Slf4j
class OverrideConfigSourceTest {

    private FixedMapConfigSource createProxy() {
        FixedMapConfigSource.Builder builder=FixedMapConfigSource.builder();
        builder.property("name-1","value-1000");
        builder.property("name-2","value-2000");
        return builder.build();
    }

    @Test
    void create() {
        FixedMapConfigSource proxy=createProxy();

        {
            OverrideConfigSource configSource=OverrideConfigSource.of(proxy,"name-1",100,null);

            ConfigSourceTests.verifyConfigSourceBasics(configSource);
        }
        {
            Map<String,String> properties=new HashMap<>();
            properties.put("name-10","value-10");
            OverrideConfigSource configSource=OverrideConfigSource.of(proxy,"name-1",100,properties);

            ConfigSourceTests.verifyConfigSourceBasics(configSource);

            Assertions.assertEquals("name-1",configSource.getName());
            Assertions.assertEquals(100,configSource.getOrdinal());
            Assertions.assertEquals(3,configSource.getProperties().size());
        }
    }

    @Test
    void createByBuilder() {
        FixedMapConfigSource proxy=createProxy();

        OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
        builder.property("name-10","value-10");
        OverrideConfigSource configSource=builder.configSource(proxy).build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertEquals(3,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithAllSet() {
        FixedMapConfigSource proxy=createProxy();

        OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
        builder.configSource(proxy);
        builder.name("name-1").ordinal(101).properties(new HashMap<>());
        OverrideConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals("name-1",configSource.getName());
        Assertions.assertEquals(101,configSource.getOrdinal());
        Assertions.assertEquals(2,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithPropertyMethods() {
        FixedMapConfigSource proxy=createProxy();

        OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
        builder.configSource(proxy);
        builder.clearProperties().property("name-10","value-10").properties(new HashMap<>());
        OverrideConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertEquals("value-10",configSource.getValue("name-10"));
        Assertions.assertEquals("value-1000",configSource.getValue("name-1"));
        Assertions.assertEquals("value-2000",configSource.getValue("name-2"));
    }

    @Test
    void createOverrideOfName() {
        FixedMapConfigSource proxy=createProxy();

        {
            OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
            builder.configSource(proxy);
            OverrideConfigSource configSource=builder.build();

            ConfigSourceTests.verifyConfigSourceBasics(configSource);

            Assertions.assertEquals(proxy.getName(),configSource.getName());  //Yes, per default, name comes from the wrapped configuration-source!
        }
        {
            OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
            builder.configSource(proxy);
            builder.name("Override-Name-1");
            OverrideConfigSource configSource=builder.build();

            ConfigSourceTests.verifyConfigSourceBasics(configSource);

            Assertions.assertNotEquals(proxy.getName(),configSource.getName());
            Assertions.assertEquals("Override-Name-1",configSource.getName());  //Yes, override of name works!
        }
        {
            OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
            builder.configSource(proxy);
            builder.nameSupplier(()->null);  //Non-null supplier, but gives 'null' as a result!
            OverrideConfigSource configSource=builder.build();

            /*
             * Note: A 'null' name is not really valid and according to the MicroProfile Config specification!
             * ConfigSourceTests.assertConfigSourceSemantics(configSource);
             */

            Assertions.assertNotEquals(proxy.getName(),configSource.getName());
            Assertions.assertNull(configSource.getName());  //Yes, override of name with 'null' works!
        }
    }

    @Test
    void createOverrideOfOrdinal() {
        FixedMapConfigSource proxy = createProxy();

        {
            OverrideConfigSource.Builder builder = OverrideConfigSource.builder();
            builder.configSource(proxy);
            OverrideConfigSource configSource = builder.build();

            ConfigSourceTests.verifyConfigSourceBasics(configSource);

            Assertions.assertEquals(proxy.getOrdinal(), configSource.getOrdinal());  //Yes, per default, ordinal comes from the wrapped configuration-source!
        }
        {
            OverrideConfigSource.Builder builder = OverrideConfigSource.builder();
            builder.configSource(proxy);
            builder.ordinal(999);
            OverrideConfigSource configSource = builder.build();

            ConfigSourceTests.verifyConfigSourceBasics(configSource);

            Assertions.assertNotEquals(proxy.getOrdinal(),configSource.getOrdinal());
            Assertions.assertEquals(999, configSource.getOrdinal());  //Yes, override of ordinal works!
        }
    }

    @Test
    void mutableName() {
        FixedMapConfigSource proxy=createProxy();

        AtomicReference<String> name=new AtomicReference<>("X");
        Supplier<String> nameSupplier=name::get;
        OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
        builder.configSource(proxy);
        builder.nameSupplier(nameSupplier);
        OverrideConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals("X",configSource.getName());

        name.set("Y");
        Assertions.assertEquals("Y",configSource.getName());

        name.set("Z");
        Assertions.assertEquals("Z",configSource.getName());
    }

    @Test
    void mutableOrdinal() {
        FixedMapConfigSource proxy=createProxy();

        AtomicInteger ordinal=new AtomicInteger(100);
        IntSupplier ordinalSupplier=ordinal::get;
        OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
        builder.configSource(proxy);
        builder.ordinalSupplier(ordinalSupplier);
        OverrideConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals(100,configSource.getOrdinal());

        ordinal.incrementAndGet();
        Assertions.assertEquals(100+1,configSource.getOrdinal());

        ordinal.incrementAndGet();
        Assertions.assertEquals(100+2,configSource.getOrdinal());
    }

    @Test
    void mutableProperties() {
        FixedMapConfigSource proxy=createProxy();

        Map<String,String> properties1=new HashMap<>();
        OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
        builder.configSource(proxy);
        builder.properties(properties1);
        OverrideConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertNotSame(properties1,configSource.getProperties());
        Map<String,String> map=new HashMap<>();
        map.put("name-1","value-1000");
        map.put("name-2","value-2000");
        Assertions.assertEquals(map,configSource.getProperties());

        Map<String,String> properties2=new HashMap<>();
        properties2.put("name-3","value-3");
        configSource.replaceProperties(properties2);
        Assertions.assertNotSame(properties2,configSource.getProperties());
        map.put("name-3","value-3");
        Assertions.assertEquals(map,configSource.getProperties());
    }

    @Test
    void carriesNullPropertyValues() {
        FixedMapConfigSource proxy=createProxy();

        OverrideConfigSource.Builder builder=OverrideConfigSource.builder();
        builder.configSource(proxy);
        builder.property("name-1",null);
        builder.property("name-3","value-3");
        OverrideConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals(3,configSource.getProperties().size());
        Map<String,String> properties=new HashMap<>();
        properties.put("name-1",null);
        properties.put("name-2","value-2000");
        properties.put("name-3","value-3");
        Assertions.assertEquals(properties,configSource.getProperties());

        Assertions.assertEquals(3,configSource.getPropertyNames().size());
        Assertions.assertEquals(Set.of("name-1","name-2","name-3"),configSource.getPropertyNames());

        Assertions.assertNull(configSource.getValue("name-1"));
        Assertions.assertEquals("value-2000",configSource.getValue("name-2"));
        Assertions.assertEquals("value-3",configSource.getValue("name-3"));
    }
}
