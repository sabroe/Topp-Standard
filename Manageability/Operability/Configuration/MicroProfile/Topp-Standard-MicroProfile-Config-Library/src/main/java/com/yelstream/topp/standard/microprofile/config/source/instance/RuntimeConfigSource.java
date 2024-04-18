package com.yelstream.topp.standard.microprofile.config.source.instance;

import com.yelstream.topp.standard.microprofile.config.source.MemoryConfigSource;
import com.yelstream.topp.standard.microprofile.config.source.ProxyConfigSource;
import lombok.Getter;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * Basic configuration-source open for fiddling and population at runtime.
 * <p>
 *     This may be instantiated by reference from a {@link java.util.ServiceLoader}:
 * </p>
 * <pre>
 *     /META-INF/services/org.eclipse.microprofile.config.spi.ConfigSource
 * </pre>
 * <p>
 *     The service file must have content with a line like the name of this class -
 * </p>
 * <pre>
 *     com.yelstream.topp.standard.microprofile.config.source.instance.RuntimeConfigSource
 * </pre>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-15
 */
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

    public static final int ORDINAL=ConfigSource.DEFAULT_ORDINAL+1000;

    private static MemoryConfigSource createMemoryConfigSource() {
        MemoryConfigSource.Builder builder=MemoryConfigSource.builder();
        builder.name(NAME).ordinal(ORDINAL);
        return builder.build();
    }
}
