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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.function.LongConsumer;

/**
 * Utilities addressing instances of writers.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-26
 */
@UtilityClass
public class Readers {
    public static CountReader count(Reader in,
                                    LongConsumer countConsumer) {
        return CountReader.builder().in(in).countConsumer(countConsumer).build();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static long countCharacters(Reader in) throws IOException {
        CountReader cr=count(in, null);
        while (cr.read()!=-1) {
            //Empty!
        }
        return cr.getCount();
    }

    public static BufferedReader buffered(Reader in) {
        return (in instanceof BufferedReader br)?br:new BufferedReader(in);
    }

    public static BufferedReader buffered(InputStream in) {
        return new BufferedReader(new InputStreamReader(in));
    }

    public static BufferedReader buffered(InputStream in,
                                          Charset charset) {
        return new BufferedReader(new InputStreamReader(in,charset));
    }

    public static BufferedReader buffered(InputStream in,
                                          CharsetDecoder dec) {
        return new BufferedReader(new InputStreamReader(in,dec));
    }
}
