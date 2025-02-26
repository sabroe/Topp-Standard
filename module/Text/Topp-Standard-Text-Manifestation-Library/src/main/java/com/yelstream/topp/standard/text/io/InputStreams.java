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

import lombok.experimental.UtilityClass;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.LongConsumer;

/**
 * Utilities addressing instances of input-streams.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-26
 */
@UtilityClass
public class InputStreams {
    public static CountInputStream count(InputStream in,
                                         LongConsumer countConsumer) {
        return CountInputStream.builder().in(in).countConsumer(countConsumer).build();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static long countBytes(InputStream in) throws IOException {
        CountInputStream cis=count(in,null);
        byte[] buffer=new byte[8192];
        while (cis.read(buffer)!=-1) {
            //Empty!
        }
        return cis.getCount();
    }

    public static BufferedInputStream buffered(InputStream in) {
        return (in instanceof BufferedInputStream bis)?bis:new BufferedInputStream(in);
    }
}
