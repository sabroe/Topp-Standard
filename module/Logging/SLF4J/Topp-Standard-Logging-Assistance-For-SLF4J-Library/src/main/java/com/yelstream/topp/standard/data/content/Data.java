/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.data.content;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-09-27
 */
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor
public final class Data {
    /**
     *
     */
    @lombok.Builder.Default
    @Getter
    private final Charset charset=StandardCharsets.UTF_8;

    /**
     *
     */
    private final byte[] content;

    /**
     *
     */
    public boolean isBinary() {
        return charset==null;
    }

    /**
     *
     */
    public boolean isTextual() {
        return !isBinary();
    }

    /**
     *
     */
    public byte[] getBytes() {
        return content.clone();  //Yes, return a copy, maintain immutability!
    }

    /**
     *
     */
    public String asText() {
        if (!isTextual()) {
            throw new IllegalStateException("Failure to get data as text; data is not textual!");
        } else {
            return new String(content,charset);
        }
    }
}
