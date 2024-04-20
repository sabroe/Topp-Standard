package com.yelstream.topp.standard.microprofile.config.source;

import com.yelstream.topp.standard.util.MapBuilders;
import com.yelstream.topp.standard.util.function.MemoizedIntSupplier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * In-memory configuration-source keeping properties as a {@link Map}.
 * <p>
 *     Actual properties may be handed over at the time of instantiation and
 *     otherwise created, read, updated or removed during runtime.
 * </p>
 * <p>
 *     This may be immutable depending upon construction.
 * </p>
 * <p>
 *     This may be thread-safe depending upon construction.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-15
 */
@AllArgsConstructor(staticName="of",access=AccessLevel.PRIVATE)
public class DynamicMapConfigSource implements ConfigSource {
    /**
     * Name.
     */
    private final Supplier<String> nameSupplier;

    /**
     * Ordinal.
     * <p>
     *    Note: If the values from this varies at runtime then the configuration-source possibly will not work.
     * </p>
     */
    private final IntSupplier ordinalSupplier;

    /**
     * Properties held.
     */
    private final AtomicReference<Map<String,String>> propertiesReference;

    @Override
    public String getName() {
        return nameSupplier.get();
    }

    @Override
    public int getOrdinal() {
        return ordinalSupplier.getAsInt();
    }

    @Override
    public Set<String> getPropertyNames() {
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

    public void replaceProperties(Map<String,String> properties) {
        propertiesReference.set(properties);
    }

    public static DynamicMapConfigSource of(String name,
                                            int ordinal,
                                            Map<String,String> properties) {
        AtomicReference<Map<String,String>> propertiesReference=new AtomicReference<>(properties);
        return of(()->name,()->ordinal,propertiesReference);
    }

    @lombok.Builder(builderClassName="Builder",toBuilder=false)  //Yes, no #toBuilder() wanted!
    private static DynamicMapConfigSource createInstance(Supplier<String> nameSupplier,
                                                         IntSupplier ordinalSupplier,
                                                         @Singular Map<String,String> properties) {
        AtomicReference<Map<String,String>> propertiesReference=new AtomicReference<>(properties);
        return of(nameSupplier,ordinalSupplier,propertiesReference);
    }

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
}
