package com.yelstream.topp.standard.log.assist.slf4j.spi.ex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Marker;

import java.util.function.Supplier;

/**
 * Static proxy for instances of {@link LoggingEventBuilderEx}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-01-14
 *
 * @param <S> Self-referential type.
 */
@SuppressWarnings({"LombokGetterMayBeUsed","ClassCanBeRecord"})
@AllArgsConstructor
public class ProxyLoggingEventBuilderEx<S extends LoggingEventBuilderEx<S>> implements LoggingEventBuilderEx<S> {
    /**
     * Delegate.
     */
    @Getter
    private final S delegate;

    @SuppressWarnings("unchecked")
    protected final S self() {
        return (S)this;
    }

    @Override
    public S setCause(Throwable cause) {
        delegate.setCause(cause);
        return self();
    }

    @Override
    public S addMarker(Marker marker) {
        delegate.addMarker(marker);
        return self();
    }

    @Override
    public S addArgument(Object argument) {
        delegate.addArgument(argument);
        return self();
    }

    @Override
    public S addArgument(Supplier<?> argumentSupplier) {
        delegate.addArgument(argumentSupplier);
        return self();
    }

    @Override
    public S addKeyValue(String key,
                         Object value) {
        delegate.addKeyValue(key,value);
        return self();
    }

    @Override
    public S addKeyValue(String key,
                         Supplier<Object> valueSupplier) {
        delegate.addKeyValue(key,valueSupplier);
        return self();
    }

    @Override
    public S setMessage(String message) {
        delegate.setMessage(message);
        return self();
    }

    @Override
    public S setMessage(Supplier<String> messageSupplier) {
        delegate.setMessage(messageSupplier);
        return self();
    }

    @Override
    public void log() {
        delegate.log();
    }

    @Override
    public void log(String message) {
        delegate.log(message);
    }

    @Override
    public void log(String message,
                    Object argument) {
        delegate.log(message,argument);
    }

    @Override
    public void log(String message,
                    Object argument1,
                    Object argument2) {
        delegate.log(message,argument1,argument2);
    }

    @Override
    public void log(String message,
                    Object... arguments) {
        delegate.log(message,arguments);
    }

    @Override
    public void log(Supplier<String> messageSupplier) {
        delegate.log(messageSupplier);
    }
}

abstract class ProxyLoggingEventBuilderEx2<P extends ProxyLoggingEventBuilderEx2<P,D>,D extends LoggingEventBuilderEx<D>> implements LoggingEventBuilderEx<P> {
/*
    P = proxy/self type
    D = delegate type
*/
    @SuppressWarnings("unchecked")
    protected final P self() {
        return (P)this;
    }
}

abstract class XXX extends ProxyLoggingEventBuilderEx2<XXX,XXX> {

}


interface Self0<S extends Self0<S>> {

    @SuppressWarnings("unchecked")
    default S self() {
        return (S)this;
    }
}

abstract class Proxy0<P extends Proxy0<P,D>, D>
        implements Self0<P> {
    protected abstract D delegate();
}

@AllArgsConstructor
abstract class ProxyLoggingEventBuilderEx0<
        P extends ProxyLoggingEventBuilderEx0<P,D>,
        D extends LoggingEventBuilderEx<D>>
        extends Proxy0<P,D>
        implements LoggingEventBuilderEx<P> {

    @Getter()
    private final D delegate;

/*
    @Override
    protected final D delegate() {
        return delegate;
    }
*/

    @Override
    public P setMessage(String message) {
        delegate.setMessage(message);
        return self();
    }
}

abstract class YYY extends ProxyLoggingEventBuilderEx0<YYY,YYY> {
/*
    P = proxy/self type
    D = delegate type
*/
    public YYY(YYY delegate) {
        super(delegate);
    }
}
