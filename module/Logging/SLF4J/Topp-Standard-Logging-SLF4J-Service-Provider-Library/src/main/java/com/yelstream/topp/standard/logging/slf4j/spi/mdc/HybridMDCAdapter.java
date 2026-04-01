/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.logging.slf4j.spi.mdc;

import org.slf4j.spi.MDCAdapter;

import java.lang.ScopedValue;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Hybrid MDCAdapter:
 * - Uses ConsistentMultiMDCAdapter (or BasicMDCAdapter) for classic mutable MDC + broadcasting.
 * - Additionally binds the current map to ScopedValue for automatic inheritance in StructuredTaskScope / virtual threads.
 * - Best of both worlds.
 */
public class HybridMDCAdapter implements MDCAdapter {

    private final ConsistentMultiMDCAdapter delegate;   // your existing one (or new BasicMDCAdapter())

    private static final ScopedValue<Map<String, String>> MDC_SCOPE = ScopedValue.newInstance();

    public HybridMDCAdapter(ConsistentMultiMDCAdapter consistentAdapter) {
        this.delegate = consistentAdapter;
    }

    // ====================== WRITE (delegate + update ScopedValue) ======================

    @Override
    public void put(String key, String val) {
        delegate.put(key, val);
        updateScopedValue();
    }

    @Override
    public void remove(String key) {
        delegate.remove(key);
        updateScopedValue();
    }

    @Override
    public void clear() {
        delegate.clear();
        ScopedValue.where(MDC_SCOPE, null).run(() -> {}); // unbind
    }

    @Override
    public void setContextMap(Map<String, String> contextMap) {
        delegate.setContextMap(contextMap);
        updateScopedValue();
    }

    @Override
    public void pushByKey(String key, String value) {
        delegate.pushByKey(key, value);
        updateScopedValue();
    }

    @Override
    public void clearDequeByKey(String key) {
        delegate.clearDequeByKey(key);
        updateScopedValue();
    }

    private void updateScopedValue() {
        Map<String, String> copy = delegate.getCopyOfContextMap();
        if (copy == null || copy.isEmpty()) {
            ScopedValue.where(MDC_SCOPE, null).run(() -> {});
        } else {
            ScopedValue.where(MDC_SCOPE, Collections.unmodifiableMap(copy)).run(() -> {});
        }
    }

    // ====================== READ (prefer ScopedValue when available) ======================

    @Override
    public String get(String key) {
        Map<String, String> scopedMap = MDC_SCOPE.orElse(null);
        if (scopedMap != null) {
            return scopedMap.get(key);
        }
        return delegate.get(key);
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        Map<String, String> scopedMap = MDC_SCOPE.orElse(null);
        if (scopedMap != null) {
            return new HashMap<>(scopedMap);
        }
        return delegate.getCopyOfContextMap();
    }

    @Override
    public String popByKey(String key) {
        return delegate.popByKey(key);
    }

    @Override
    public Deque<String> getCopyOfDequeByKey(String key) {
        return delegate.getCopyOfDequeByKey(key);
    }

    // Helper to get the underlying delegate if needed
    public ConsistentMultiMDCAdapter getDelegate() {
        return delegate;
    }
}
