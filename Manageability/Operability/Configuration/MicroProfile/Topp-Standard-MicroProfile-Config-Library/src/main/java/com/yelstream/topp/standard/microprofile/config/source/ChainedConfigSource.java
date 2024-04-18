package com.yelstream.topp.standard.microprofile.config.source;

import lombok.AllArgsConstructor;
import lombok.Singular;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * In-memory configuration-source keeping properties as a {@link Map}.
 * <p>
 *     Actual properties may be handed over at the time of instantiation and
 *     otherwise created, read, updated or removed during runtime.
 * </p>
 * <p>
 *     This is thread safe.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-15
 */
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public class ChainedConfigSource implements ConfigSource {
    @lombok.Builder.Default
    private final Supplier<String> nameSupplier=()->UUID.randomUUID().toString();

    /**
     * Ordinal.
     */
    @lombok.Builder.Default
    private final IntSupplier ordinalSupplier=()->DEFAULT_ORDINAL;

    /**
     * Configuration-sources held.
     * In practice, this is always an instance of {@link CopyOnWriteArrayList}.
     */
    @Singular
    private final List<ConfigSource> configSources;

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
        return configSources.stream()
            .map(ConfigSource::getPropertyNames)
            .filter(Objects::nonNull)
            .flatMap(Set::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public String getValue(String propertyName) {
        return configSources.stream()
            .map(configSource->configSource.getValue(propertyName))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Map<String,String> getProperties() {
        return configSources.stream()
            .map(ConfigSource::getProperties)
            .filter(Objects::nonNull)
            .flatMap(map->map.entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(v1,v2)->v1,HashMap::new));
    }

    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue"})
    public static class Builder {
        private Supplier<String> nameSupplier=()->UUID.randomUUID().toString();
        private IntSupplier ordinalSupplier=()->DEFAULT_ORDINAL;

        //private final List<ConfigSource> configSources=new CopyOnWriteArrayList<>();

        public Builder name(String name) {
            nameSupplier=()->name;
            return this;
        }

        public Builder ordinal(int ordinal) {
            ordinalSupplier=()->ordinal;
            return this;
        }
    }
    //TO-DO: Test methods configSources(List), clearConfigSources().
}
