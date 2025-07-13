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

package com.yelstream.topp.standard.net.resource.location.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Simple URL connection.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-13
 */
final class SimpleURLConnection extends URLConnection {
    /**
     *
     */
    public final Supplier<InputStream> inputStreamSupplier;

    /**
     *
     */
    public final Supplier<OutputStream> outputStreamSupplier;

    /**
     * Constructs a URL connection to the specified URL. A connection to
     * @param url the specified URL.
     */
    public SimpleURLConnection(URL url,
                               Supplier<InputStream> inputStreamSupplier,
                               Supplier<OutputStream> outputStreamSupplier) {
        super(url);
        this.inputStreamSupplier=inputStreamSupplier;
        this.outputStreamSupplier=outputStreamSupplier;
    }

    @Override
    public void connect() throws IOException {
        //Empty!
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try {
            return Objects.requireNonNull(inputStreamSupplier.get());
        } catch (UncheckedIOException ex) {
            throw new IOException("Failure to open input stream; got I/O error, URL is '%s'!".formatted(url),ex.getCause());
        } catch (Exception ex) {
            throw new IOException("Failure to open input stream; URL is '%s'!".formatted(url), ex);
        }
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        try {
            return Objects.requireNonNull(outputStreamSupplier.get());
        } catch (UncheckedIOException ex) {
            throw new IOException("Failure to open output stream; got I/O error, URL is '%s'!".formatted(url),ex.getCause());
        } catch (Exception ex) {
            throw new IOException("Failure to open output stream; URL is '%s'!".formatted(url), ex);
        }
    }
}
