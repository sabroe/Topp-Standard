package com.yelstream.topp.standard.microprofile.config.source;

import com.yelstream.topp.standard.util.MapBuilders;
import com.yelstream.topp.standard.util.function.MemoizedIntSupplier;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-20
 */
public class OverrideConfigSource extends ProxyConfigSource {
    /**
     * Name.
     * <p>
     *     This may be {@code null}.
     * </p>
     */
    private final Supplier<String> nameSupplier;

    /**
     * Ordinal.
     * <p>
     *    Note: If the values from this varies at runtime then the configuration-source possibly will not work.
     * </p>
     * <p>
     *     This may be {@code null}.
     * </p>
     */
    private final IntSupplier ordinalSupplier;

    /**
     * Properties held.
     * <p>
     *     This reference may contain the map value {@code null}.
     * </p>
     */
    private final AtomicReference<ConcurrentHashMap<String,String>> propertiesReference;

    @Override
    public String getName() {
        if (nameSupplier!=null) {
            return nameSupplier.get();
        } else {
            return super.getName();
        }
    }

    @Override
    public int getOrdinal() {
        if (ordinalSupplier!=null) {
            return ordinalSupplier.getAsInt();
        } else {
            return super.getOrdinal();
        }
    }

    @Override
    public Set<String> getPropertyNames() {
        ConcurrentHashMap<String,String> properties=propertiesReference.get();
        if (properties!=null) {

        }

        return propertiesReference.get().keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return propertiesReference.get().get(propertyName);
    }

    @Override
    public Map<String,String> getProperties() {
        return propertiesReference.get();
    }

    public void replaceProperties(ConcurrentHashMap<String,String> properties) {
        propertiesReference.set(properties);
    }

    private OverrideConfigSource(ConfigSource configSource,
                                 Supplier<String> nameSupplier,
                                 IntSupplier ordinalSupplier,
                                 HashMap<String,String> properties) {
        this(configSource,nameSupplier,ordinalSupplier,new ConcurrentHashMap<>(properties));
    }

    private OverrideConfigSource(ConfigSource configSource,
                                 Supplier<String> nameSupplier,
                                 IntSupplier ordinalSupplier,
                                 ConcurrentHashMap<String,String> properties) {
        super(configSource);
        this.nameSupplier=nameSupplier;
        this.ordinalSupplier=ordinalSupplier;
        this.propertiesReference=new AtomicReference<>(properties);
    }

/*
    @lombok.Builder(builderClassName="Builder",toBuilder=false)  //Yes, no #toBuilder() wanted!
    private static DynamicMapConfigSource createInstance(ConfigSource configSource,
                                                         Supplier<String> nameSupplier,
                                                         IntSupplier ordinalSupplier,
                                                         @Singular Map<String,String> properties) {
        AtomicReference<Map<String,String>> propertiesReference=new AtomicReference<>(properties);
        return of(nameSupplier,ordinalSupplier,propertiesReference);
    }
*/

/*
    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue","FieldMayBeFinal"})
    public static class Builder {
        private Supplier<String> nameSupplier=ConfigSources.DEFAULT_NAME_SUPPLIER;

        private IntSupplier ordinalSupplier=ConfigSources.DEFAULT_ORDINAL_SUPPLIER;

        private Map<String,String> properties=new HashMap<>();

        public Builder name(String name) {
            return nameSupplier(()->name);
        }

        public Builder ordinal(int ordinal) {
            return ordinalSupplier(()->ordinal);
        }

        public Builder ordinalSupplierWithStrategy(IntSupplier ordinalSupplier,
                                                   MemoizedIntSupplier.Strategy strategy) {
            return ordinalSupplier(strategy.of(ordinalSupplier));
        }

        public Builder propertiesBuilder(MapBuilders.MapBuilder<String,String> mapBuilder) {
            properties=mapBuilder.build();
            return this;
        }

        @SuppressWarnings("java:S1117")
        public MapBuilders.MapBuilder<String,String> propertiesBuild() {
            MapBuilders.MapBuilder<String,String> mapBuilder=MapBuilders.mapBuilder();
            mapBuilder.consumer(properties->this.properties=properties);
            return mapBuilder;
        }
    }
*/
}
