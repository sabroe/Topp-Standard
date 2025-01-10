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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * Test of {@link DynamicMapConfigSource}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-02-20
 */
@Slf4j
class DynamicMapConfigSourceTest {
    @Test
    void create() {
        {
            DynamicMapConfigSource configSource=DynamicMapConfigSource.of("name-1",100,null);

            ConfigSourceTests.verifyConfigSourceBasics(configSource);
        }
        {
            DynamicMapConfigSource configSource=DynamicMapConfigSource.of("name-1",100,new HashMap<>());

            ConfigSourceTests.verifyConfigSourceBasics(configSource);

            Assertions.assertEquals("name-1",configSource.getName());
            Assertions.assertEquals(100,configSource.getOrdinal());
            Assertions.assertEquals(0,configSource.getProperties().size());
        }
    }

    @Test
    void createByBuilder() {
        DynamicMapConfigSource.Builder builder=DynamicMapConfigSource.builder();
        DynamicMapConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertEquals(0,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithAllSet() {
        DynamicMapConfigSource.Builder builder=DynamicMapConfigSource.builder();
        builder.name("name-1").ordinal(100).properties(new HashMap<>());
        DynamicMapConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals("name-1",configSource.getName());
        Assertions.assertEquals(100,configSource.getOrdinal());
        Assertions.assertEquals(0,configSource.getProperties().size());
    }

    @Test
    void createByBuilderWithPropertyMethods() {
        DynamicMapConfigSource.Builder builder=DynamicMapConfigSource.builder();
        builder.clearProperties().property("name-1","value-1").properties(new HashMap<>());
        DynamicMapConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals(ConfigSources.DEFAULT_ORDINAL,configSource.getOrdinal());
        Assertions.assertEquals("value-1",configSource.getValue("name-1"));
    }

    @Test
    void mutableName() {
        AtomicReference<String> name=new AtomicReference<>("X");
        Supplier<String> nameSupplier=name::get;
        DynamicMapConfigSource.Builder builder=DynamicMapConfigSource.builder();
        builder.nameSupplier(nameSupplier);
        DynamicMapConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals("X",configSource.getName());

        name.set("Y");
        Assertions.assertEquals("Y",configSource.getName());

        name.set("Z");
        Assertions.assertEquals("Z",configSource.getName());
    }

    @Test
    void mutableOrdinal() {
        AtomicInteger ordinal=new AtomicInteger(100);
        IntSupplier ordinalSupplier=ordinal::get;
        DynamicMapConfigSource.Builder builder=DynamicMapConfigSource.builder();
        builder.ordinalSupplier(ordinalSupplier);
        DynamicMapConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals(100,configSource.getOrdinal());

        ordinal.incrementAndGet();
        Assertions.assertEquals(100+1,configSource.getOrdinal());

        ordinal.incrementAndGet();
        Assertions.assertEquals(100+2,configSource.getOrdinal());
    }

    @Test
    void mutableProperties() {
        Map<String,String> properties1=new HashMap<>();
        DynamicMapConfigSource.Builder builder=DynamicMapConfigSource.builder();
        builder.properties(properties1);
        DynamicMapConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertNotSame(properties1,configSource.getProperties());
        Assertions.assertEquals(properties1,configSource.getProperties());

        Map<String,String> properties2=new HashMap<>();
        configSource.replaceProperties(properties2);
        Assertions.assertNotSame(properties1,configSource.getProperties());
        Assertions.assertSame(properties2,configSource.getProperties());
    }

    @Test
    void carriesNullPropertyValues() {
        DynamicMapConfigSource.Builder builder=DynamicMapConfigSource.builder();
        builder.property("name-1",null);
        DynamicMapConfigSource configSource=builder.build();

        ConfigSourceTests.verifyConfigSourceBasics(configSource);

        Assertions.assertEquals(1,configSource.getProperties().size());
        Assertions.assertEquals(1,configSource.getPropertyNames().size());
        Assertions.assertNull(configSource.getValue("name-1"));
    }
}
