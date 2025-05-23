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

package com.yelstream.topp.standard.xml.bind;

import jakarta.xml.bind.Unmarshaller;
import lombok.experimental.UtilityClass;
import org.w3c.dom.Node;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;

/**
 * Utilities addressing instances of {@link UnmarshalOperator}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@UtilityClass
public class UnmarshalOperators {

    public static <T> UnmarshalOperator<T> of(Node node) {
        return (Unmarshaller unmarshaller, Class<T> declaredType) -> unmarshaller.unmarshal(node,declaredType);
    }

    public static <T> UnmarshalOperator<T> of(Source source) {
        return (Unmarshaller unmarshaller, Class<T> declaredType) -> unmarshaller.unmarshal(source,declaredType);
    }

    public static <T> UnmarshalOperator<T> of(XMLStreamReader reader) {
        return (Unmarshaller unmarshaller, Class<T> declaredType) -> unmarshaller.unmarshal(reader,declaredType);
    }

    public static <T> UnmarshalOperator<T> of(XMLEventReader reader) {
        return (Unmarshaller unmarshaller, Class<T> declaredType) -> unmarshaller.unmarshal(reader,declaredType);
    }
}
