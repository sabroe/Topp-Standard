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

import lombok.ToString;
import org.w3c.dom.ls.LSInput;

import java.io.InputStream;
import java.io.Reader;

/**
 * Static proxy for instances of {@link LSInput}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@ToString
public class ImmutableLSInput extends ProxyLSInput {
    /**
     * Constructor.
     * @param input Wrapped resource.
     */
    protected ImmutableLSInput(LSInput input) {
        super(input);
    }

    @Override
    public void setCharacterStream(Reader reader) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setByteStream(InputStream in) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStringData(String stringData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSystemId(String systemId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPublicId(String publicId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBaseURI(String baseURI) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEncoding(String encoding) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCertifiedText(boolean certified) {
        throw new UnsupportedOperationException();
    }

    public static ImmutableLSInput of(LSInput input) {
        return new ImmutableLSInput(input);
    }
}
