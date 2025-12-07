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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@AllArgsConstructor(access=AccessLevel.PROTECTED)
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@SuppressWarnings("LombokGetterMayBeUsed")
@ToString
public class ProxyLSInput implements LSInput {
    /**
     * Wrapped resource.
     */
    @Getter
    private final LSInput input;

    @Override
    public Reader getCharacterStream() {
        return input.getCharacterStream();
    }

    @Override
    public void setCharacterStream(Reader reader) {
        input.setCharacterStream(reader);
    }

    @Override
    public InputStream getByteStream() {
        return input.getByteStream();
    }

    @Override
    public void setByteStream(InputStream in) {
        input.setByteStream(in);
    }

    @Override
    public String getStringData() {
        return input.getStringData();
    }

    @Override
    public void setStringData(String stringData) {
        input.setStringData(stringData);
    }

    @Override
    public String getSystemId() {
        return input.getSystemId();
    }

    @Override
    public void setSystemId(String systemId) {
        input.setSystemId(systemId);
    }

    @Override
    public String getPublicId() {
        return input.getPublicId();
    }

    @Override
    public void setPublicId(String publicId) {
        input.setPublicId(publicId);
    }

    @Override
    public String getBaseURI() {
        return input.getBaseURI();
    }

    @Override
    public void setBaseURI(String baseURI) {
        input.setBaseURI(baseURI);
    }

    @Override
    public String getEncoding() {
        return input.getEncoding();
    }

    @Override
    public void setEncoding(String encoding) {
        input.setEncoding(encoding);
    }

    @Override
    public void setCertifiedText(boolean certified) {
        input.setCertifiedText(certified);
    }

    @Override
    public boolean getCertifiedText() {
        return input.getCertifiedText();
    }
}
