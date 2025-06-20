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

package com.yelstream.topp.standard.xml.bind.io;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.experimental.UtilityClass;
import org.w3c.dom.Node;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;

/**
 * Utilities addressing instances of {@link UnmarshalInput}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@UtilityClass
public class UnmarshalInputs {

    public static UnmarshalInput of(Node node) {
        class NodeUnmarshalInput implements UnmarshalInput {
            @Override
            public <T> JAXBElement<T> unmarshal(Unmarshaller unmarshaller,
                                                Class<T> declaredType) throws JAXBException {
                return unmarshaller.unmarshal(node,declaredType);
            }
        }
        return new NodeUnmarshalInput();
    }

    public static UnmarshalInput of(Source source) {
        class SourceUnmarshalInput implements UnmarshalInput {
            @Override
            public <T> JAXBElement<T> unmarshal(Unmarshaller unmarshaller,
                                                Class<T> declaredType) throws JAXBException {
                return unmarshaller.unmarshal(source,declaredType);
            }
        }
        return new SourceUnmarshalInput();
    }

    public static UnmarshalInput of(XMLStreamReader reader) {
        class XMLStreamReaderUnmarshalInput implements UnmarshalInput {
            @Override
            public <T> JAXBElement<T> unmarshal(Unmarshaller unmarshaller,
                                                Class<T> declaredType) throws JAXBException {
                return unmarshaller.unmarshal(reader,declaredType);
            }
        }
        return new XMLStreamReaderUnmarshalInput();
    }

    public static UnmarshalInput of(XMLEventReader reader) {
        class XMLEventReaderUnmarshalInput implements UnmarshalInput {
            @Override
            public <T> JAXBElement<T> unmarshal(Unmarshaller unmarshaller,
                                                Class<T> declaredType) throws JAXBException {
                return unmarshaller.unmarshal(reader,declaredType);
            }
        }
        return new XMLEventReaderUnmarshalInput();
    }
}
