/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.resource.clazz.load;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Filtered, classloader-bound resource lookup.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@AllArgsConstructor(staticName="of")
final class FilteredClassLoaderResourceLoader implements ResourceLoader {
    /**
     * Classloader.
     */
    public final ClassLoader classLoader;

    /**
     * Filters the enumeration of URLs.
     * This may be {@code null}.
     */
    public final UnaryOperator<Enumeration<URL>> enumerationFilter;

    /**
     * Filters the stream of URLs.
     * This may be {@code null}.
     */
    public final UnaryOperator<Stream<URL>> streamFilter;

    @Override
    public URL getResource(String name) {
        return classLoader.getResource(name);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return classLoader.getResourceAsStream(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        return applyFilter(classLoader.getResources(name),enumerationFilter);
    }

    @Override
    public Stream<URL> resources(String name) {
        return applyFilter(classLoader.resources(name),streamFilter);
    }

    private <X> X applyFilter(X origin,
                              UnaryOperator<X> filter) {  //TO-DO: Move to other module, utility class "UnaryOperators"?
        if (origin==null) {
            return null;
        } else {
            if (filter==null) {
                return origin;
            } else {
                return filter.apply(origin);
            }
        }
    }

    public static FilteredClassLoaderResourceLoader of(ClassLoader classLoader) {
        return of(classLoader,null,null);
    }

    public static FilteredClassLoaderResourceLoader of(ClassLoader classLoader,
                                                       Predicate<URL> filter) {
        return of(classLoader,
                  enumeration->filter(enumeration,filter),
                  stream -> filter(stream,filter)
                 );
    }

    private static <X> Enumeration<X> filter(Enumeration<X> enumeration,
                                             Predicate<X> predicate) {
        if (predicate==null) {
            return enumeration;
        } else {
            List<X> filteredElements=new ArrayList<>();
            while (enumeration.hasMoreElements()) {
                X element=enumeration.nextElement();
                if (predicate.test(element)) {
                    filteredElements.add(element);
                }
            }
            return Collections.enumeration(filteredElements);
        }
    }

    private static <X> Stream<X> filter(Stream<X> stream,
                                        Predicate<X> predicate) {
        if (predicate==null) {
            return stream;
        } else {
            return stream.filter(predicate);
        }
    }
}
