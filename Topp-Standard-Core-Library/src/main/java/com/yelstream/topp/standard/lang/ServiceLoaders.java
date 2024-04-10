package com.yelstream.topp.standard.lang;

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
