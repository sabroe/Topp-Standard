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

import jakarta.xml.bind.Marshaller;
import lombok.experimental.UtilityClass;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import java.io.File;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Utilities addressing instances of {@link MarshalOutput}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-13
 */
@UtilityClass
public class MarshalOutputs {

    public static MarshalOutput of(Result result) {
        return (Marshaller marshaller, Object element) -> marshaller.marshal(element,result);
    }

    public static MarshalOutput of(OutputStream os) {
        return (Marshaller marshaller, Object element) -> marshaller.marshal(element,os);
    }

    public static MarshalOutput of(File output) {
        return (Marshaller marshaller, Object element) -> marshaller.marshal(element,output);
    }

    public static MarshalOutput of(Writer writer) {
        return (Marshaller marshaller, Object element) -> marshaller.marshal(element,writer);
    }

    public static MarshalOutput of(ContentHandler handler) {
        return (Marshaller marshaller, Object element) -> marshaller.marshal(element,handler);
    }

    public static MarshalOutput of(Node node) {
        return (Marshaller marshaller, Object element) -> marshaller.marshal(element,node);
    }

    public static MarshalOutput of(XMLStreamWriter writer) {
        return (Marshaller marshaller, Object element) -> marshaller.marshal(element,writer);
    }

    public static MarshalOutput of(XMLEventWriter writer) {
        return (Marshaller marshaller, Object element) -> marshaller.marshal(element,writer);
    }
}
