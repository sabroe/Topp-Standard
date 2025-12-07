/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

import com.yelstream.topp.standard.util.function.Suppliers;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.Map;
import java.util.UUID;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Utilities addressing instances of {@link ConfigSource}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-15
 */
@Slf4j
@UtilityClass
public class ConfigSources {
    /**
     * Default factory of suppliers of the name of config sources.
     * <p>
     *     To create a new name supplier of the default kind, do {@code DEFAULT_NAME_SUPPLIER_FACTORY.get()}.
     * </p>
     */
    public static final Supplier<Supplier<String>> DEFAULT_NAME_SUPPLIER_FACTORY=()->Suppliers.fix(ConfigSources::createName);

    /**
     * Creates a random name for a config source.
     * Multiple invocations results in different names.
     * @return Created name.
     */
    public static String createName() {
        return createName(null);
    }

    /**
     * Creates a random name for a config source.
     * Multiple invocations results in different names.
     * @param namePrefix Fixed name prefix.
     * @return Created name.
     */
    public static String createName(String namePrefix) {
        namePrefix=namePrefix!=null?namePrefix:"Config source";
        return String.format("%s // %s",namePrefix,UUID.randomUUID());
    }

    /**
     * Default property name for looking up a local property value specifying the ordinal value.
     */
    public static final String DEFAULT_CONFIG_ORDINAL=ConfigSource.CONFIG_ORDINAL;

    /**
     * Default supplier of the property name for looking up a local property value specifying the ordinal value.
     */
    public static final Supplier<String> DEFAULT_CONFIG_ORDINAL_SUPPLIER=()->DEFAULT_CONFIG_ORDINAL;

    /**
     * Default ordinal value.
     */
    public static final int DEFAULT_ORDINAL=ConfigSource.DEFAULT_ORDINAL;

    /**
     * Default supplier of an ordinal value.
     */
    public static final IntSupplier DEFAULT_ORDINAL_SUPPLIER=()->DEFAULT_ORDINAL;

    public static int getOrdinal(ConfigSource configSource) {
        return getOrdinal(configSource::getValue,null,null);
    }

    public static int getOrdinal(ConfigSource configSource,
                                 Supplier<String> configOrdinalSupplier,
                                 IntSupplier ordinalSupplier) {
        return getOrdinal(configSource::getValue,configOrdinalSupplier,ordinalSupplier);
    }

    public static int getOrdinal(Map<String,String> properties) {
        return getOrdinal(properties::get,null,null);
    }

    public static int getOrdinal(Map<String,String> properties,
                                 Supplier<String> configOrdinalSupplier,
                                 IntSupplier ordinalSupplier) {
        return getOrdinal(properties::get,configOrdinalSupplier,ordinalSupplier);
    }

    public static int getOrdinal(UnaryOperator<String> propertyAccessor,
                                 Supplier<String> configOrdinalSupplier,
                                 IntSupplier ordinalSupplier) {

        if (configOrdinalSupplier==null) {
            configOrdinalSupplier=DEFAULT_CONFIG_ORDINAL_SUPPLIER;
        }
        if (ordinalSupplier==null) {
            ordinalSupplier=DEFAULT_ORDINAL_SUPPLIER;
        }
        String configOrdinal=propertyAccessor.apply(configOrdinalSupplier.get());
        if (configOrdinal!=null) {
            try {
                return Integer.parseInt(configOrdinal);
            } catch (NumberFormatException ex) {
                log.error("Failure to get ordinal; property value {} is not a number!",configOrdinal);
            }
        }
        return ordinalSupplier.getAsInt();
    }

    public static ConfigSource createEmptyConfigSource(String name,
                                                       int ordinal) {
        return FixedMapConfigSource.of(name,ordinal,null);
    }

    public static ConfigSource createEmptyConfigSource() {
        return FixedMapConfigSource.of(createName(),DEFAULT_ORDINAL,null);
    }
}
