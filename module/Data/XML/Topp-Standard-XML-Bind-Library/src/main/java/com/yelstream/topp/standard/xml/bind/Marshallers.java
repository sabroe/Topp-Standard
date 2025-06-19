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
import jakarta.xml.bind.Marshaller;
import lombok.experimental.UtilityClass;

import javax.xml.validation.Schema;

/**
 * Utility addressing instances of {@link Marshaller}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-20
 */
@UtilityClass
public class Marshallers {
    /**
     * Creates a marshaller.
     * @param context JAXB context.
     * @return Marshaller.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static Marshaller createMarshaller(JAXBContext context) throws JAXBException {
        return createMarshaller(context, null);
    }

    /**
     * Creates a marshaller.
     * @param context JAXB context.
     * @param schema Schema.
     * @return Marshaller.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static Marshaller createMarshaller(JAXBContext context,
                                              Schema schema) throws JAXBException {
        Marshaller marshaller=context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);  //TO-DO: Make this conditional?
        if (schema!=null) {
            marshaller.setSchema(schema);
        }
        return marshaller;
    }

    //TO-DO: Add enabling of basic pretty-print!
    //TO-DO: Add NamespacePrefixMapper! Note that 'org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper' is glassfish-specific!
    //TO-DO: Add Lombok builder of Marshaller
    //TO-DO: Add properties!
    //TO-DO: Add event-handler ValidationEventHandler !
    //TO-DO: Add adapter !
    //TO-DO: Add attachment marshaller AttachmentMarshaller !
    //TO-DO: Add schema!
    //TO-DO: Add listener!
}
