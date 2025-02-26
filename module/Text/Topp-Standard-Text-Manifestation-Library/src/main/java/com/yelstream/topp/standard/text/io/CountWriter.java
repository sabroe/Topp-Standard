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

package com.yelstream.topp.standard.text.io;

import lombok.NonNull;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongConsumer;

/**
 * Writer counting the characters written.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-26
 */
public class CountWriter extends FilterWriter {
    /**
     * Counter of characters.
     */
    private final AtomicLong count=new AtomicLong(0);

    /**
     * Consumer of latest count of characters.
     * This may be {@code null}.
     */
    private final LongConsumer countConsumer;

    @lombok.Builder(builderClassName = "Builder")
    protected CountWriter(Writer out,
                          LongConsumer countConsumer) {
        super(out);
        this.countConsumer=countConsumer;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c);
        count(1);
    }

    @Override
    public void write(char @NonNull [] buf, int off, int len) throws IOException {
        super.write(buf,off,len);
        count(len);
    }

    @Override
    public void write(@NonNull String s, int off, int len) throws IOException {
        super.write(s,off,len);
        count(len);
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
