package com.yelstream.topp.standard.microprofile.config.source;

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
     * Default supplier of the name of config sources.
     */
    public static final Supplier<String> DEFAULT_NAME_SUPPLIER=ConfigSources::createName;

    public static String createName() {
        return "Config source "+UUID.randomUUID().toString();
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
}
