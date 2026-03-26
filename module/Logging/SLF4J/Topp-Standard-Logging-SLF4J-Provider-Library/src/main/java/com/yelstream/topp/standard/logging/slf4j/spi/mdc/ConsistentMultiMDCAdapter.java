package com.yelstream.topp.standard.logging.slf4j.spi.mdc;

import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.spi.MDCAdapter;

import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Consistent multi-destination MDCAdapter for virtual threads (Java 21+).
 *
 * - Reads always come from the internal BasicMDCAdapter (single source of truth).
 * - Writes are broadcast to the master + all real backend adapters.
 * - Fully compatible with virtual threads (ThreadLocal transfer works).
 * - Supports the full SLF4J 2.x MDC API including stack/deque methods.
 * - Compiles and runs on Java 21+.
 */
@SuppressWarnings("java:S1117")
public class ConsistentMultiMDCAdapter implements MDCAdapter {

    private final MDCAdapter adapter = new BasicMDCAdapter();

    /**
     * MDCAdapters from your real logging backends
     * (e.g. LogbackMDCAdapter, Log4j's adapter, etc.).
     * Can be empty if you only want the master.
     */
    private final List<MDCAdapter> adapters;

    public ConsistentMultiMDCAdapter(List<MDCAdapter> adapters) {
        this.adapters = adapters;
    }

    protected void broadcast(Consumer<MDCAdapter> adapterOp) {
        adapterOp.accept(adapter);
        adapters.forEach(adapterOp);
    }

    protected <T> T read(Function<MDCAdapter, T> adapterOp) {
        return adapterOp.apply(adapter);
    }

    @Override
    public void put(String key, String val) {
        broadcast(adapter -> adapter.put(key, val));
    }

    @Override
    public void remove(String key) {
        broadcast(adapter -> adapter.remove(key));
    }

    @Override
    public void clear() {
        broadcast(MDCAdapter::clear);
    }

    @Override
    public void setContextMap(Map<String, String> contextMap) {
        broadcast(adapter -> adapter.setContextMap(contextMap));
    }

    @Override
    public void pushByKey(String key, String value) {
        broadcast(adapter -> adapter.pushByKey(key, value));
    }

    @Override
    public void clearDequeByKey(String key) {
        broadcast(adapter -> adapter.clearDequeByKey(key));
    }

    @Override
    public String get(String key) {
        return read(adapter -> adapter.get(key));
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        return read(MDCAdapter::getCopyOfContextMap);
    }

    @Override
    public String popByKey(String key) {
        return read(adapter -> adapter.popByKey(key));
    }

    @Override
    public Deque<String> getCopyOfDequeByKey(String key) {
        return read(adapter -> adapter.getCopyOfDequeByKey(key));
    }
}
