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
