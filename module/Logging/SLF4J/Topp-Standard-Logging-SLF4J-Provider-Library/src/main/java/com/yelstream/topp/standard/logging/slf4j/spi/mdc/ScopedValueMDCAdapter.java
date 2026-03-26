package com.yelstream.topp.standard.logging.slf4j.spi.mdc;

import org.slf4j.spi.MDCAdapter;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Pure ScopedValue-based MDCAdapter – compatible with Java 25 (final Scoped Values API).
 */
public class ScopedValueMDCAdapter implements MDCAdapter {

    private static final ScopedValue<Map<String, String>> MDC_SCOPE = ScopedValue.newInstance();

    // ====================== Mutable operations not supported ======================
    @Override
    public void put(String key, String val) {
        throw new UnsupportedOperationException(
                "MDC.put() is not supported with ScopedValueMDCAdapter. " +
                        "Use runWhere(...) or runWithAdded(...) instead.");
    }

    @Override
    public void remove(String key) {
        throw new UnsupportedOperationException("remove() not supported in pure ScopedValue mode.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear() not supported in pure ScopedValue mode.");
    }

    @Override
    public void setContextMap(Map<String, String> contextMap) {
        throw new UnsupportedOperationException("setContextMap() not supported in pure ScopedValue mode.");
    }

    @Override
    public void pushByKey(String key, String value) {
        throw new UnsupportedOperationException("pushByKey not supported");
    }

    @Override
    public String popByKey(String key) {
        throw new UnsupportedOperationException("popByKey not supported");
    }

    @Override
    public Deque<String> getCopyOfDequeByKey(String key) {
        throw new UnsupportedOperationException("getCopyOfDequeByKey not supported");
    }

    @Override
    public void clearDequeByKey(String key) {
        throw new UnsupportedOperationException("clearDequeByKey not supported");
    }

    // ====================== Read operations ======================
    @Override
    public String get(String key) {
        Map<String, String> map = MDC_SCOPE.orElse(Collections.emptyMap());
        return map.get(key);
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        Map<String, String> map = MDC_SCOPE.orElse(Collections.emptyMap());
        return new HashMap<>(map);
    }

    // ====================== Helpers (Java 25 style) ======================

    /**
     * Run a block with the given MDC context (immutable inside the scope).
     */
    public static void runWhere(Map<String, String> context, Runnable runnable) {
        if (context == null || context.isEmpty()) {
            runnable.run();
            return;
        }
        ScopedValue.where(MDC_SCOPE, Map.copyOf(context))
                .run(runnable);
    }

    /**
     * Callable version for Java 25 – uses ScopedValue.CallableOp.
     */
    public static <T> T callWhere(Map<String, String> context, ScopedValue.CallableOp<T, ? extends Exception> callableOp)
            throws Exception {
        if (context == null || context.isEmpty()) {
            return callableOp.call();
        }
        return ScopedValue.where(MDC_SCOPE, Map.copyOf(context))
                .call(callableOp);
    }

    /**
     * Add extra entries on top of the currently bound MDC.
     */
    public static void runWithAdded(Map<String, String> additional, Runnable runnable) {
        Map<String, String> current = MDC_SCOPE.orElse(Collections.emptyMap());
        Map<String, String> merged = new HashMap<>(current);
        if (additional != null) {
            merged.putAll(additional);
        }
        runWhere(merged, runnable);
    }
}