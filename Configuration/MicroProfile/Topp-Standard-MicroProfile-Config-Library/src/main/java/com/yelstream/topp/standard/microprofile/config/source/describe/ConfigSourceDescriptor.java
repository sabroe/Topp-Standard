package com.yelstream.topp.standard.microprofile.config.source.describe;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of",access=AccessLevel.PRIVATE)
public class ConfigSourceDescriptor {
    private final String className;
    private final String name;
    private final int ordinal;

    private final Set<String> propertyNames;
    private final Map<String,String> properties;

    public static class Builder {
        private Set<String> propertyNames=new HashSet<>();
        private Map<String,String> properties=new HashMap<>();
    }
}
