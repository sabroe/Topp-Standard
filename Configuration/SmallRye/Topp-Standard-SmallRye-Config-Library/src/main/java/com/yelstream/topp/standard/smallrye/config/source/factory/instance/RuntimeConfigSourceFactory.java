/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.smallrye.config.source.factory.instance;

import com.yelstream.topp.standard.microprofile.config.source.instance.RuntimeConfigSource;
import com.yelstream.topp.standard.smallrye.config.source.factory.ListConfigSourceFactory;
import com.yelstream.topp.standard.smallrye.config.source.factory.ProxyConfigSourceFactory;
import lombok.Getter;

import java.util.OptionalInt;

public class RuntimeConfigSourceFactory extends ProxyConfigSourceFactory {
    @Getter
    private final ListConfigSourceFactory configSourceFactory;

    public RuntimeConfigSourceFactory() {
        this(createListConfigSourceFactory());
    }

    private RuntimeConfigSourceFactory(ListConfigSourceFactory configSourceFactory) {
        super(configSourceFactory);
        this.configSourceFactory=configSourceFactory;
    }

    public static final String NAME="Runtime Config Source Factory";

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static final OptionalInt PRIORITY=OptionalInt.empty();

    private static ListConfigSourceFactory createListConfigSourceFactory() {
        ListConfigSourceFactory.Builder builder=ListConfigSourceFactory.builder();
        builder.configSource(new RuntimeConfigSource());
        return builder.build();
    }
}
