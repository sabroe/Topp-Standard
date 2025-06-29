package com.yelstream.topp.standard.load.clazz.scan.factory;

import com.yelstream.topp.standard.load.clazz.scan.URLScanner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.net.URL;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
     * Indicated, if a URL can be applied to this scanner.
     * @param url URL.
     * @return
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
