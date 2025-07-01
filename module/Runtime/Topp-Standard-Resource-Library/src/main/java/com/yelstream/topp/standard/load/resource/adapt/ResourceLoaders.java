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

package com.yelstream.topp.standard.load.resource.adapt;

import com.yelstream.topp.standard.resource.clazz.ClassLoaders;
import com.yelstream.topp.standard.resource.clazz.Modules;
import com.yelstream.topp.standard.resource.io.source.InputSource;
import lombok.experimental.UtilityClass;

import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Utility addressing instances of {@link ResourceLoader}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-26
 */
@UtilityClass
public class ResourceLoaders {
    public static ResourceLoader createResourceLookup(ClassLoader classLoader) {
        return FilteredClassLoaderResourceLoader.of(classLoader);
    }

    public static ResourceLoader createResourceLookup(ClassLoader classLoader,
                                                      Predicate<URL> filter) {
        return FilteredClassLoaderResourceLoader.of(classLoader,filter);
    }

    public static ResourceLoader createClassLoaderFilteredByCodeSource(Class<?> clazz) {
        return createResourceLookup(ClassLoaders.getClassLoader(clazz),createFilterByCodeSource(clazz));
    }

    public static ResourceLoader createResourceLookup(Class<?> clazz) {
        if (Modules.isInNamedModule(clazz)) {
            return createResourceLookup(ClassLoaders.getModuleClassLoader(clazz));
        } else {
            return createClassLoaderFilteredByCodeSource(clazz);
        }
    }

    private static Predicate<URL> createFilterByCodeSource(Class<?> clazz) {  //TO-DO: Move to "URLs"?
        return createFilterByCodeSource(clazz.getProtectionDomain().getCodeSource());
    }

    private static Predicate<URL> createFilterByCodeSource(CodeSource codeSource) {  //TO-DO: Move to "URLs"?
        return createFilterByBaseURL(codeSource.getLocation());
    }

    private static Predicate<URL> createFilterByBaseURL(URL baseURL) {  //TO-DO: Move to ??
        String prefix=baseURL.toString();
        return resource -> resource.toString().startsWith(prefix);
    }

    @lombok.Builder(builderClassName="Builder")
    private ResourceLoader createResourceLoaderByBuilder(ClassLoader classLoader,
                                                             ResourceLoader resourceLoader,
                                                             String name,
                                                             UnaryOperator<URI> uriTransformer,
                                                             UnaryOperator<URL> urlTransformer,
                                                             Supplier<InputSource> readableSupplier) {
        ;
        return null;
    }
}
