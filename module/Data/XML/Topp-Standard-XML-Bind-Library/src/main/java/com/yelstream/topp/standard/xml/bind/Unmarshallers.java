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

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.PropertyException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.ValidationEventHandler;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.attachment.AttachmentUnmarshaller;
import lombok.experimental.UtilityClass;

import javax.xml.validation.Schema;
import java.util.Map;

/**
 * Utility addressing instances of {@link Unmarshaller}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-20
 */
@UtilityClass
public class Unmarshallers {

    public void setProperty(Unmarshaller unmarshaller,
                            String name,
                            Object value) {
        try {
            unmarshaller.setProperty(name, value);
        } catch (PropertyException ex) {
            throw new IllegalArgumentException("Failure to set unmarshaller property; name is %s!".formatted(name),ex);
        }
    }

    public Object getProperty(Unmarshaller unmarshaller,
                              String name) {
        try {
            return unmarshaller.getProperty(name);
        } catch (PropertyException ex) {
            throw new IllegalArgumentException("Failure to get unmarshaller property; name is %s!".formatted(name),ex);
        }
    }

    /**
     * Creates an unmarshaller.
     * @param context JAXB context.
     * @return Unmarshaller.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static Unmarshaller createUnmarshaller(JAXBContext context) throws JAXBException {
        return createUnmarshaller(context,null);
    }

    /**
     * Creates an unmarshaller.
     * @param context JAXB context.
     * @param schema Schema.
     * @return Unmarshaller.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static Unmarshaller createUnmarshaller(JAXBContext context,
                                                  Schema schema) throws JAXBException {
        Unmarshaller unmarshaller=context.createUnmarshaller();
        if (schema!=null) {
            unmarshaller.setSchema(schema);
        }
        return unmarshaller;
    }

    //TO-DO: Add Lombok builder? ValidationEventHandler? Properties? Schema? XMLAdapter? AttachmentUnmarshaller? Listener?

    @lombok.Builder(builderClassName="Builder")
    private static Unmarshaller createUnmarshallerByBuilder(JAXBContext context,
                                                            Map<String,Object> properties,
                                                            Schema schema,
                                                            ValidationEventHandler eventHandler,
                                                            XmlAdapter<?,?> adapter,
                                                            Unmarshaller.Listener listener,
                                                            AttachmentUnmarshaller attachmentUnmarshaller) throws JAXBException {
        Unmarshaller unmarshaller=context.createUnmarshaller();
        if (properties!=null) {
            properties.forEach((name,value)->setProperty(unmarshaller,name,value));
        }
        if (schema!=null) {
            unmarshaller.setSchema(schema);
        }
        if (eventHandler!=null) {
            unmarshaller.setEventHandler(eventHandler);
        }
        if (adapter!=null) {
            unmarshaller.setAdapter(adapter);
        }
        if (listener!=null) {
            unmarshaller.setListener(listener);
        }
        if (attachmentUnmarshaller!=null) {
            unmarshaller.setAttachmentUnmarshaller(attachmentUnmarshaller);
        }
        return unmarshaller;
    }
}
