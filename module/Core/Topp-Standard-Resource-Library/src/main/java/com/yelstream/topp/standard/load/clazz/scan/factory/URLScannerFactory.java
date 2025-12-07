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

package com.yelstream.topp.standard.load.clazz.scan.factory;

import com.yelstream.topp.standard.load.clazz.scan.URLScanner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.net.URL;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-29
 */
@lombok.Builder
@AllArgsConstructor(staticName="of")
public final class URLScannerFactory {
    /**
     * URL protocol/URI scheme.
     * E.g. { "jar", "file" }.
     */
    @Getter
    private final String protocol;

    /**
     * Supplier of URL-scanners instances.
     * Technically, this may be either a factory always creating new instances,
     * or a supplier constantly handing out the same, fixed, thread-safe instance.
     */
    private final Supplier<URLScanner> supplier;

    /**
     * Non-standard testing of if a URL can be scanned.
     */
    @Builder.Default
    private final Predicate<URL> matcher=null;

    /**
     * Verifies that a specified URL can be applied to this scanner.
     * @param url URL.
     * @return Indicated, if the URL can be applied.
     */
    @SuppressWarnings({"java:S2583","ConstantConditions"})
    public boolean matches(URL url) {
        if (matcher==null) {
            return protocol.equals(url.getProtocol());
        } else {
            return matcher.test(url);
        }
    }

    public URLScanner scanner() {
        return supplier.get();
    }

    public static URLScannerFactory of(String protocol,
                                       Supplier<URLScanner> supplier) {
        return builder().protocol(protocol).supplier(supplier).build();
    }
}
