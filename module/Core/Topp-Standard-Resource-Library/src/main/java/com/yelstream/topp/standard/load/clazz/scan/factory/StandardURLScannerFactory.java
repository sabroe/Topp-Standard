/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2025 Morten Sabroe Mortensen
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

import com.yelstream.topp.standard.load.clazz.scan.impl.FileURLScanner;
import com.yelstream.topp.standard.load.clazz.scan.impl.JARURLScanner;
import com.yelstream.topp.standard.net.resource.location.StandardProtocol;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Standard URL-scanner factory.
 * <p>
 *     Enumeration of URL-scanner factories for standard URL protocols.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-29
 */
@AllArgsConstructor
@SuppressWarnings({"java:S115","LombokGetterMayBeUsed"})
public enum StandardURLScannerFactory {
    File(URLScannerFactory.of(StandardProtocol.File.getProtocol().getName(), FileURLScanner::new)),
    JAR(URLScannerFactory.of(StandardProtocol.JAR.getProtocol().getName(), JARURLScanner::new));

    //TO-DO: Consider adding scanner for ZIP file content!

    @Getter
    private final URLScannerFactory factory;


    private static Stream<StandardURLScannerFactory> matchStream(URL url) {
        return Arrays.stream(values()).filter(v->v.factory.matches(url));
    }

    public static StandardURLScannerFactory match(URL url) {
        return matchStream(url).findFirst().orElse(null);
    }

    public static List<StandardURLScannerFactory> matchAll(URL url) {
        return matchStream(url).toList();
    }
}
