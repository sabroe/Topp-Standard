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

package com.yelstream.topp.standard.text.io;

import lombok.NonNull;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongConsumer;

/**
 * Input-stream counting the bytes read.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-26
 */
public class CountInputStream extends FilterInputStream {
    /**
     * Counter of bytes.
     */
    private final AtomicLong count=new AtomicLong(0);

    /**
     * Consumer of latest count of bytes.
     * This may be {@code null}.
     */
    private final LongConsumer countConsumer;

    @lombok.Builder(builderClassName="Builder")
    protected CountInputStream(InputStream in,
                               LongConsumer countConsumer) {
        super(in);
        this.countConsumer=countConsumer;
    }

    @Override
    public int read() throws IOException {
        int b=super.read();
        if (b!=-1) count(1);
        return b;
    }

    @Override
    public int read(byte @NonNull [] buf, int off, int len) throws IOException {
        int bytesRead=super.read(buf,off,len);
        if (bytesRead>0) count(bytesRead);
        return bytesRead;
    }

    /**
     * Increments the count.
     * @param n Increment in count.
     */
    private void count(long n) {
        long newCount=count.addAndGet(n);
        if (countConsumer!=null) countConsumer.accept(newCount);
    }

    /**
     * Gets the current count of bytes.
     * @return Current count.
     */
    public long getCount() {
        return count.get();
    }
}
