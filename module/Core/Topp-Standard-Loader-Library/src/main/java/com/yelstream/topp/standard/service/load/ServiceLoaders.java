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

package com.yelstream.topp.standard.service.load;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Utility addressing instances of {@link ServiceLoader}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-04-10
 */
@Slf4j
@UtilityClass
public class ServiceLoaders {
    public static <S> S loadService(Class<S> service) {
        return loadService(()->ServiceLoader.load(service));
    }

    public static <S> List<S> loadServices(Class<S> service) {
        return loadServices(()->ServiceLoader.load(service));
    }

    public static <S> S loadService(Supplier<ServiceLoader<S>> serviceLoaderSupplier) {
        ServiceLoader<S> serviceLoader=serviceLoaderSupplier.get();
        return loadService(serviceLoader);
    }

    public static <S> List<S> loadServices(Supplier<ServiceLoader<S>> serviceLoaderSupplier) {
        ServiceLoader<S> serviceLoader=serviceLoaderSupplier.get();
        return loadServices(serviceLoader);
    }

    public static <S> S loadService(ServiceLoader<S> serviceLoader) {
        return serviceLoader==null?null:serviceLoader.findFirst().orElse(null);
    }

    public static <S> List<S> loadServices(ServiceLoader<S> serviceLoader) {
        return serviceLoader==null?null:serviceLoader.stream().map(ServiceLoader.Provider::get).toList();
    }

    public static <S> String createDescription(ServiceLoader<S> serviceLoader) {
        StringBuilder sb=new StringBuilder();
        try (Stream<ServiceLoader.Provider<S>> providerStream=serviceLoader.stream() ) {
            sb.append("Service loader:");
            AtomicInteger i=new AtomicInteger();
            providerStream.forEach(provider->{
                sb.append(String.format("%n"));
                sb.append(String.format("    %d: %n",i.get()));
                sb.append(String.format("        Type: %s",provider.type()));
                i.incrementAndGet();
            });
        }
        return sb.toString();
    }

    public static <S> void logDescription(ServiceLoader<S> serviceLoader) {
        log.atInfo().setMessage(()->createDescription(serviceLoader)).log();
    }
}
