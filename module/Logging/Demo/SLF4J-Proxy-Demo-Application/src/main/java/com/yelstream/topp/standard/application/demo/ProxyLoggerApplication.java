
package com.yelstream.topp.standard.application.demo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.SLF4JServiceProvider;

import java.util.ServiceLoader;

@Slf4j
public class ProxyLoggerApplication {

    static void main() {
        System.out.println("Hello World!");
        System.out.println("slf4j.provider: "+System.getProperty("slf4j.provider"));

//        System.setProperty("slf4j.provider","com.yelstream.topp.standard.logging.slf4j.spi.service.proxy.XXXProxySLF4JServiceProvider");

        System.out.println();
        System.out.println("Services:");
        ServiceLoader.load(SLF4JServiceProvider.class).forEach(provider->{
            System.out.println("    "+provider);
            System.out.println("        "+provider.getRequestedApiVersion());
        });
        System.out.println();


        System.out.println("Logger: "+log);
        System.out.println("Logger: "+log.getName());
        System.out.println("Logger: "+log.getClass());

        log.info("Hello, logger! You are class {} and named {}.",log.getClass().getName(),log.getName());
    }
}
