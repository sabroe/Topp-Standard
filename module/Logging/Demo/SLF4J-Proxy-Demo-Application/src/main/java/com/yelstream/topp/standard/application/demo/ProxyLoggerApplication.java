
package com.yelstream.topp.standard.application.demo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.SLF4JServiceProvider;

import java.io.PrintStream;
import java.util.ServiceLoader;

@Slf4j
public class ProxyLoggerApplication {

    static void main() {
        PrintStream out=System.out;

        out.println("Hello World!");
        out.println("slf4j.provider: "+System.getProperty("slf4j.provider"));

//        System.setProperty("slf4j.provider","com.yelstream.topp.standard.logging.slf4j.spi.service.proxy.XXXProxySLF4JServiceProvider");

        out.println();
        out.println("Services:");
        ServiceLoader.load(SLF4JServiceProvider.class).forEach(provider->{
            out.println("    "+provider);
            out.println("        "+provider.getRequestedApiVersion());
        });
        out.println();


        out.println("Logger: "+log);
        out.println("Logger: "+log.getName());
        out.println("Logger: "+log.getClass());

        log.info("Hello, logger! You are class '{}' and named '{}'.",log.getClass().getName(),log.getName());
        log.atInfo().log("Hello, logger! You are class '{}' and named '{}'.",log.getClass().getName(),log.getName());
    }
}
