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
import jakarta.xml.bind.Unmarshaller;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Utility addressing JAXB objects.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-06-20
 */
@UtilityClass
public class JAXBObjects {


    public static <T> T deepCopy(T object, Class<T> clazz) throws JAXBException {  //TO-DO: Fix this!
        JAXBContext context = JAXBContext.newInstance(clazz);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(object, baos);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return clazz.cast(unmarshaller.unmarshal(bais));
    }

    //TO-DO: Add Lombok builder for copying!
    //TO-DO: Add CopyOperator!
    //TO-DO: Consider 'Kryo' as an alternative to re-marshalling!
    //TO-DO: Consider 'Apache Commons Lang' as an alternative to re-marshalling!
    //TO-DO: Add EqualsOperator!

/* Kryo
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoDeepCopy {
    public static <T> T deepCopy(T object) {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.write(object, output);
        output.close();
        Input input = new Input(new ByteArrayInputStream(baos.toByteArray()));
        T copy = (T) kryo.readClassAndObject(input);
        input.close();
        return copy;
    }
}

<dependency>
    <groupId>com.esotericsoftware</groupId>
    <artifactId>kryo</artifactId>
    <version>5.5.0</version>
</dependency>
 */

    /* Apache Commons Lang

import org.apache.commons.lang3.SerializationUtils;

Person copy = SerializationUtils.clone(original);

<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.12.0</version>
</dependency>
     */
}
