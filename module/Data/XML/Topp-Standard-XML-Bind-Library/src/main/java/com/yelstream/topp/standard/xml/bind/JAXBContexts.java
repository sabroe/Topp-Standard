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
import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@link JAXBContext}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-20
 */
@UtilityClass
public class JAXBContexts {
    /**
     * Create a new JAXB context.
     * @param declaredType Class to be recognized by JAXB context.
     * @return JAXB context.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static JAXBContext createJAXBContext(Class<?> declaredType) throws JAXBException {
        return JAXBContext.newInstance(declaredType);
    }

    //TO-DO: Add builder? Binder, JAXBIntrospector, SchemaOutputResolver,

    @lombok.Builder(builderClassName="Builder")
    private static JAXBContext createJAXBContextByBuilder(Class<?> declaredType) throws JAXBException {
        JAXBContext context=JAXBContext.newInstance(declaredType);
/*
        if (properties!=null) {
            properties.forEach((name,value)->setProperty(marshaller,name,value));
        }
        if (schema!=null) {
            marshaller.setSchema(schema);
        }
        if (eventHandler!=null) {
            marshaller.setEventHandler(eventHandler);
        }
        if (adapter!=null) {
            marshaller.setAdapter(adapter);
        }
        if (listener!=null) {
            marshaller.setListener(listener);
        }
        if (attachmentMarshaller!=null) {
            marshaller.setAttachmentMarshaller(attachmentMarshaller);
        }
 */
        return context;
    }
}
