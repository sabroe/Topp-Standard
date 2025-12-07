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

package com.yelstream.topp.standard.dom.ls;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.w3c.dom.ls.LSInput;

import java.io.InputStream;
import java.io.Reader;

/**
 * Basic, simple input source for data.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@NoArgsConstructor(staticName="of")
@AllArgsConstructor(staticName="of")
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@SuppressWarnings("LombokSetterMayBeUsed")
@ToString
public final class BasicLSInput implements LSInput {
    /**
     * Provides access to the source as text.
     */
    @Getter
    @Setter
    private Reader characterStream;

    /**
     * Provides access to the source as binary data.
     */
    @Getter
    @Setter
    private InputStream byteStream;

    /**
     * The source as explicit text.
     */
    @Getter
    @Setter
    private String stringData;

    /**
     * System id.
     */
    @Getter
    @Setter
    private String systemId;

    /**
     * Public id.
     */
    @Getter
    @Setter
    private String publicId;

    /**
     * Base URI.
     */
    @Getter
    @Setter
    private String baseURI;

    /**
     * Character encoding used to interpret the binary data.
     */
    @Getter
    @Setter
    private String encoding;

    /**
     * Indicates, if the input from the source is certified.
     */
    @Setter
    private boolean certifiedText;

    @Override
    public boolean getCertifiedText() {
        return certifiedText;
    }
}
