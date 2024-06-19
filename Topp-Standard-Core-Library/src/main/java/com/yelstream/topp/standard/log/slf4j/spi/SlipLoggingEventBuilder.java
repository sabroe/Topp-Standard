package com.yelstream.topp.standard.log.slf4j.spi;

import lombok.AllArgsConstructor;
import org.slf4j.Marker;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@AllArgsConstructor
public class SlipLoggingEventBuilder implements LoggingEventBuilder {  //TO-DO: Consider the existence of this!
    /**
     *
     */
    private final ProxyLoggingEventBuilder<LoggingEventBuilder> loggingEventBuilder;

    private Supplier<String> messageSupplier;

    private Throwable throwable;

    private List<Marker> markers=new ArrayList<>();

    private List<Supplier<?>> argumentSuppliers=new ArrayList<>();

    @Override
    public LoggingEventBuilder setMessage(String s) {
        return loggingEventBuilder.setMessage(s);
    }

    @Override
    public LoggingEventBuilder setMessage(Supplier<String> supplier) {
        return loggingEventBuilder.setMessage(supplier);
    }

    @Override
    public LoggingEventBuilder setCause(Throwable throwable) {
        this.throwable=throwable;
        return this;
    }

    @Override
    public LoggingEventBuilder addMarker(Marker marker) {
        this.markers.add(marker);
        return this;
    }

    @Override
    public LoggingEventBuilder addArgument(Object o) {
        this.argumentSuppliers.add(()->o);
        return this;
    }

    @Override
    public LoggingEventBuilder addArgument(Supplier<?> supplier) {
        this.argumentSuppliers.add(supplier);
        return this;
    }

    @Override
    public LoggingEventBuilder addKeyValue(String s, Object o) {
//        return loggingEventBuilder.addKeyValue(s,o);
        return this;
    }

    @Override
    public LoggingEventBuilder addKeyValue(String s, Supplier<Object> supplier) {
//        return loggingEventBuilder.addKeyValue(s,supplier);
        return this;
    }

    @Override
    public void log() {
        loggingEventBuilder.log();
    }

    @Override
    public void log(String s) {
        loggingEventBuilder.log(s);
    }

    @Override
    public void log(String s, Object o) {
        loggingEventBuilder.log(s,o);
    }

    @Override
    public void log(String s, Object o, Object o1) {
        loggingEventBuilder.log(s,o,o1);
    }

    @Override
    public void log(String s, Object... objects) {
        loggingEventBuilder.log(s,objects);
    }

    @Override
    public void log(Supplier<String> supplier) {
        loggingEventBuilder.log(supplier);
    }

/*
    public static void main(String[] args) {

        Limit.at("1234").delay(Duration.ofSeconds(60)).resolve(x->
            XX
        );

    }
*/

}
