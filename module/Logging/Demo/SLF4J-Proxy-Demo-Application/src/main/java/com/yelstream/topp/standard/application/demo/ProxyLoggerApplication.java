
package com.yelstream.topp.standard.application.demo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
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

        Marker marker=MarkerFactory.getMarker("xxx");
        MDC.put("aaa","bbb");

        System.out.println();
        log.info("(A)Hello, logger! You are class '{}' and named '{}'.",log.getClass().getName(),log.getName());

        System.out.println();
        log.atInfo().log("(B)Hello, logger! You are class '{}' and named '{}'.",log.getClass().getName(),log.getName());

        System.out.println();
        log.atInfo().setMessage("(C)Hello, logger! You are class '{}' and named '{}'.").addArgument(log.getClass().getName()).addArgument(log.getName()).addMarker(marker).log();
    }
}
