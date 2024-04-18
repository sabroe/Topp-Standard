package com.yelstream.topp.standard.quarkus.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.event.Level;

import java.util.Optional;

@Slf4j
@Getter
@AllArgsConstructor
public class OptionReader {
    private final Enum<?> enumeration;

    private final Config config;

    private final String propertyName;

    private final Class<?> propertyType;

    public String getValue() {
        return config.getValue(propertyName,String.class);
    }

    public Optional<String> getOptionalValue() {
        return config.getOptionalValue(propertyName,String.class);
    }

    public <T> T getValue(Class<T> propertyType) {
        return config.getValue(propertyName,propertyType);
    }

    public <T> Optional<T> getOptionalValue(Class<T> propertyType) {
        return config.getOptionalValue(propertyName,propertyType);
    }

    public void log(org.slf4j.Logger log,
                    org.slf4j.event.Level level) {
        log.atLevel(level).setMessage("Configuration option '{}' = {}").addArgument(()->propertyName).addArgument(()->getOptionalValue().orElse(null)).log();
    }

    public void log(org.slf4j.Logger log) {
        log(log,Level.DEBUG);
    }
}
