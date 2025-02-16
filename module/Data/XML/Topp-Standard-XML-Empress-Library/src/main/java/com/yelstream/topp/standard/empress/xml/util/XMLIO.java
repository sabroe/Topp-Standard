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

package com.yelstream.topp.standard.empress.xml.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Special I/O of XML.
 * <p>
 *     This can e.g. read XML from a binary source without knowing the character encoding in advance and without reformatting the XML.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-14
 */
@UtilityClass
public class XMLIO {

    /**
     * XML declaration, or XML processing instruction.
     * <p>
     *     This can carry the information content of e.g this:
     * </p>
     * <ol>
     *     <li>
     *         <pre><?xml version="1.0" encoding="UTF-8" standalone="yes"?></pre>
     *     </li>
     *     <li>
     *       <pre><?xml-stylesheet type="text/xsl" href="style.xsl"?></pre>
     *     </li>
     * </ol>
     */
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    @Getter
    public static class Declaration {
        private final String target;
        private final Map<String,String> fields;
    }

    private static final Pattern ENCODING_PATTERN =
        Pattern.compile("<\\?xml.*encoding=[\"'](.*?)[\"'].*\\?>", Pattern.CASE_INSENSITIVE);

    public static String readXmlAsString(InputStream inputStream) throws IOException {
        // Read the first few bytes to check for BOM & encoding
        ByteArrayOutputStream headerBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            headerBuffer.write(buffer, 0, bytesRead);
            if (headerBuffer.size() >= 1024) break; // Read enough to capture prolog
        }

        byte[] xmlBytes = headerBuffer.toByteArray();
        Charset encoding = detectEncoding(xmlBytes);

        // Now read the full XML document with the correct encoding
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(xmlBytes), encoding);
             StringWriter writer = new StringWriter()) {
            char[] charBuffer = new char[1024];
            int length;
            while ((length = reader.read(charBuffer)) != -1) {
                writer.write(charBuffer, 0, length);
            }
            return writer.toString();
        }
    }

    private static Charset detectEncoding(byte[] xmlBytes) {
        // Check for BOM (Byte Order Mark)
        if (xmlBytes.length >= 3 && xmlBytes[0] == (byte) 0xEF && xmlBytes[1] == (byte) 0xBB && xmlBytes[2] == (byte) 0xBF) {
            return StandardCharsets.UTF_8;  // UTF-8 BOM
        } else if (xmlBytes.length >= 2) {
            if (xmlBytes[0] == (byte) 0xFE && xmlBytes[1] == (byte) 0xFF) return StandardCharsets.UTF_16BE; // UTF-16 BE BOM
            if (xmlBytes[0] == (byte) 0xFF && xmlBytes[1] == (byte) 0xFE) return StandardCharsets.UTF_16LE; // UTF-16 LE BOM
        }

        // Convert bytes to string (ISO-8859-1 ensures no character loss)
        String header = new String(xmlBytes, StandardCharsets.ISO_8859_1);

        // Extract encoding from XML prolog
        Matcher matcher = ENCODING_PATTERN.matcher(header);
        if (matcher.find()) {
            try {
                return Charset.forName(matcher.group(1));
            } catch (Exception e) {
                // Fallback in case of an unknown encoding
            }
        }

        // Default to UTF-8 (per XML standard)
        return StandardCharsets.UTF_8;
    }
}
