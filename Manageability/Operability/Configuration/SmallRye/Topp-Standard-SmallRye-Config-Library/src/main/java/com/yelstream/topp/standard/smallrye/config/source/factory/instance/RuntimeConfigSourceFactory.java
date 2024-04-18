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
