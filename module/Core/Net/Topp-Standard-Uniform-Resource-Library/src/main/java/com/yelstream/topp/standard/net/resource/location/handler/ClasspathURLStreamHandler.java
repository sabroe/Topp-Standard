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

package com.yelstream.topp.standard.net.resource.location.handler;

import com.yelstream.topp.standard.system.load.clazz.ClassLoaders;
import com.yelstream.topp.standard.system.load.clazz.util.StandardClassLoader;
import com.yelstream.topp.standard.net.resource.location.protocol.Protocol;
import com.yelstream.topp.standard.net.resource.location.connection.URLConnections;
import com.yelstream.topp.standard.system.load.name.Locations;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * URL stream handler for classpath resources.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-13
 */
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(staticName="of")
public final class ClasspathURLStreamHandler extends URLStreamHandler {
    /**
     * Default protocol name.
     */
    public static final Protocol DEFAULT_PROTOCOL=Protocol.of("classpath");

    @FunctionalInterface
    public interface ClassLoaderSelector {
        Supplier<ClassLoader> select(URL url);
    }

    @Getter
    @AllArgsConstructor
    @SuppressWarnings("java:S115")
    public enum StandardClassLoaderSelector {
        Simple("simple",SIMPLE_CLASSLOADER_SELECTOR),
        Authority("authority",AUTHORITY_CLASSLOADER_SELECTOR);

        private final String name;
        private final ClassLoaderSelector selector;
    }

    private static final ClassLoaderSelector SIMPLE_CLASSLOADER_SELECTOR=ignore->ClassLoaders::getContextClassLoader;

    public static final ClassLoaderSelector AUTHORITY_CLASSLOADER_SELECTOR=
        url->{
            String authority=url.getAuthority();
            if (authority==null || authority.isEmpty()) {
                return ClassLoaders::getContextClassLoader;
            } else {
                return Optional.ofNullable(StandardClassLoader.valueBySupplierName(authority)).map(e -> e.getSupplier().getClassLoaderSupplier()).orElse(null);
            }
        };

    /**
     * Actual protocol name.
     * This may be set differently than the default, possibly to mitigate overlap with other frameworks,
     * like something private "z-classpath",
     * and is intended to be determined by the {@link java.net.URLStreamHandlerFactory} providing this URL stream handler.
     */
    @lombok.Builder.Default
    @SuppressWarnings("java:S1170")
    private final Protocol protocol=DEFAULT_PROTOCOL;

    /**
     * Origin of classloader.
     * Is in the form of a supplier since this allows setting a default,
     * but without actually acquiring the classloader before actually needed.
     * <p>
     *     Note that the default has the broadest possible context.
     * </p>
     */
    @lombok.Builder.Default
    private final ClassLoaderSelector classLoaderSelector=AUTHORITY_CLASSLOADER_SELECTOR;

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        protocol.requireMatch(url);

        Supplier<ClassLoader> classLoaderSupplier=classLoaderSelector.select(url);
        if (classLoaderSupplier==null) {
            throw new IOException("Failure to open connection; cannot find a matching classloader, URL '%s'!".formatted(url));
        }

        ClassLoader classLoader=classLoaderSupplier.get();

        String path=Locations.normalizeNameAsContent(url.getPath());
        URL resourceURL=classLoader.getResource(path);
        if (resourceURL==null) {
            throw new IOException("Failure to open connection; cannot access resource, URL is '%s'!".formatted(url));
        }

        return URLConnections.builder().url(url).inputStreamSupplier(()->classLoader.getResourceAsStream(path)).build();
    }

    public static ClasspathURLStreamHandler of() {
        return builder().build();
    }
}
