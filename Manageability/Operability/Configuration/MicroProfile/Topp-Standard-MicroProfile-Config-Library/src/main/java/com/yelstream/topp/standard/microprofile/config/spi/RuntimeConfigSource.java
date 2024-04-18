package com.yelstream.topp.standard.microprofile.config.spi;

import lombok.Getter;

@SuppressWarnings("unused")
public final class RuntimeConfigSource extends ProxyConfigSource {
    @Getter
    private final MemoryConfigSource configSource;

    public RuntimeConfigSource() {
        this(createMemoryConfigSource());
    }

    private RuntimeConfigSource(MemoryConfigSource configSource) {
        super(configSource);
        this.configSource=configSource;
    }

    public static final String NAME="Runtime Config Source";

    public static final int ORDINAL=RuntimeConfigSource.DEFAULT_ORDINAL+1000;

    private static MemoryConfigSource createMemoryConfigSource() {
        MemoryConfigSource.Builder builder=MemoryConfigSource.builder();
        builder.name(NAME).ordinal(ORDINAL);
        return builder.build();
    }
}
