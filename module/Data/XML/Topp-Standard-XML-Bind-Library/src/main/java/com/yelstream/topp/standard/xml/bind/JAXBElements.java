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

import com.yelstream.topp.standard.xml.bind.io.MarshalOutput;
import com.yelstream.topp.standard.xml.bind.io.UnmarshalInput;
import com.yelstream.topp.standard.xml.validation.NewSchemaOperator;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.experimental.UtilityClass;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.validation.Schema;
import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utility addressing instances of {@link JAXBElement}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-20
 */
@UtilityClass
public class JAXBElements {
    /**
     * Creates a JAXB element.
     * @param name Binding of XML element tag name.
     * @param declaredType Binding of XML element declaration's type.
     * @param value Java instance representing XML element's value.
     * @param <T> Class representing XML element.
     * @return JAXB element.
     */
    public static <T> JAXBElement<T> createJAXBElement(QName name,
                                                       Class<T> declaredType,
                                                       T value) {
        return new JAXBElement<>(name,declaredType,value);
    }

    public static <T> JAXBElement<T> createJAXBElement(QName name,
                                                       Class<T> declaredType,
                                                       Class<?> scope,
                                                       T value) {
        return new JAXBElement<>(name,declaredType,scope,value);
    }

    private static <S,T> JAXBElement<T> createJAXBElement(S value,
                                                          Predicate<S> nilPredicate,
                                                          Function<S,T> typeTransformer,
                                                          Function<T,JAXBElement<T>> elementFactory) {
        JAXBElement<T> element;
        if (value == null) {
            element = elementFactory.apply(null);
        } else {
            if (nilPredicate.test(value)) {
                element = elementFactory.apply(null);
                element.setNil(true);
            } else {
                T transformedValue = typeTransformer.apply(value);
                element = elementFactory.apply(transformedValue);
            }
        }
        return element;
    }

    @lombok.Builder(builderClassName="Builder")
    private static <T> JAXBElement<T> createJAXBElement(JAXBElementFactory<T> factory,
                                                        T value,
                                                        boolean nil) {
        return factory.create(value,nil);
    }

    //TO-DO: Copy-constructor?

/*
    private static <S,T> JAXBElement<T> createJAXBElement(S value,
                                                          Predicate<S> nulPredicate,
                                                          Predicate<S> nilPredicate,
                                                          Function<S,T> typeTransformer,
                                                          Function<T,JAXBElement<T>> elementFactory) {
        JAXBElement<T> element;
        if (nulPredicate) {
            element = elementFactory.apply(null);
        } else {
            if (nilPredicate.test(value)) {
                element = elementFactory.apply(null);
                element.setNil(true);
            } else {
                T transformedValue = typeTransformer.apply(value);
                element = elementFactory.apply(transformedValue);
            }
        }
        return element;
    }
*/

    /**
     * Reads a JAXB element.
     * @param declaredType Binding of XML element declaration's type.
     * @param schema Schema.
     * @param input Unmarshal input.
     * @param <T> Class representing XML element.
     * @return JAXB element.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static <T> JAXBElement<T> readJAXBElement(Class<T> declaredType,
                                                     Schema schema,
                                                     UnmarshalInput input) throws JAXBException {
        JAXBContext context=JAXBContexts.createJAXBContext(declaredType);
        Unmarshaller unmarshaller=Unmarshallers.createUnmarshaller(context,schema);
        return input.unmarshal(unmarshaller,declaredType);
    }

    /**
     * Writes a JAXB element.
     * @param element JAXB element.
     * @param schema Schema.
     * @param output Marshal output.
     * @param <T> Class representing XML element.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static <T> void writeJAXBElement(JAXBElement<T> element,
                                            Schema schema,
                                            MarshalOutput output) throws JAXBException {
        Class<T> declaredType=element.getDeclaredType();
        JAXBContext context=JAXBContexts.createJAXBContext(declaredType);
        Marshaller marshaller=Marshallers.createMarshaller(context,schema);
        output.marshal(marshaller,element);
    }

    /**
     * Reads a XML element binding value.
     * @param declaredType Binding of XML element declaration's type.
     * @param schema Schema.
     * @param input Unmarshal input.
     * @param <T> Class representing XML element.
     * @return Binding of XML element.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static <T> T readValue(Class<T> declaredType,
                                  Schema schema,
                                  UnmarshalInput input) throws JAXBException {
        JAXBElement<T> element=readJAXBElement(declaredType,schema,input);
        return element.getValue();
    }

    /**
     * Writes a XML element binding value.
     * @param name Binding of XML element tag name.
     * @param declaredType Binding of XML element declaration's type.
     * @param value Binding of XML element.
     * @param schema Schema.
     * @param output Marshal output.
     * @param <T> Class representing XML element.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static <T> void writeValue(QName name,
                                      Class<T> declaredType,
                                      T value,
                                      Schema schema,
                                      MarshalOutput output) throws JAXBException {
        JAXBElement<T> element=JAXBElements.createJAXBElement(name,declaredType,value);
        writeJAXBElement(element,schema,output);
    }

    /**
     * Reads a XML element binding value.
     * @param declaredType Binding of XML element declaration's type.
     * @param newSchemaOperator New-schema operator.
     * @param input Unmarshal input.
     * @param <T> Class representing XML element.
     * @return XML element binding value.
     * @throws IOException Thrown in case of I/O error.
     */
    public static <T> T read(Class<T> declaredType,
                             NewSchemaOperator newSchemaOperator,
                             UnmarshalInput input) throws IOException {
        try {
            Schema schema=newSchemaOperator==null?null:newSchemaOperator.newSchema();
            return JAXBElements.readValue(declaredType,schema,input);
        } catch (JAXBException | SAXException ex) {
            throw new IOException("Failure to read document!",ex);
        }
    }

    /**
     * Writes a XML element binding value.
     * @param name Binding of XML element tag name.
     * @param declaredType Binding of XML element declaration's type.
     * @param value Binding of XML element.
     * @param newSchemaOperator New-schema operator.
     * @param output Marshal output.
     * @param <T> Class representing XML element.
     * @throws IOException Thrown in case of I/O error.
     */
    public static <T> void write(QName name,
                                 Class<T> declaredType,
                                 T value,
                                 NewSchemaOperator newSchemaOperator,
                                 MarshalOutput output) throws IOException {
        try {
            Schema schema=newSchemaOperator==null?null:newSchemaOperator.newSchema();
            JAXBElement<T> element=JAXBElements.createJAXBElement(name,declaredType,value);
            JAXBElements.writeJAXBElement(element,schema,output);
        } catch (JAXBException | SAXException ex) {
            throw new IOException("Failure to write document!",ex);
        }
    }
}
