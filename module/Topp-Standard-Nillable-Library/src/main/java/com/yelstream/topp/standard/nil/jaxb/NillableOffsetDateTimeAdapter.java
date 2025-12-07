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

package com.yelstream.topp.standard.nil.jaxb;

import com.yelstream.topp.standard.nil.Nillable;
import com.yelstream.topp.standard.nil.Nil;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-05-24
 */
public class NillableOffsetDateTimeAdapter extends XmlAdapter<NillableOffsetDateTimeAdapter.AdaptedOffsetDateTime,
                                                              Nillable<JAXBElement<OffsetDateTime>>> {
    /**
     * Adapted type for XML serialization, including value and nil flag.
     */
    public static class AdaptedOffsetDateTime {
        @XmlValue
        public OffsetDateTime value;

        @XmlAttribute(namespace = "http://www.w3.org/2001/XMLSchema-instance")
        public Boolean nil;

        @XmlAttribute
        public String priority; // Example attribute
    }

    private static final Nil<JAXBElement<OffsetDateTime>> NIL = Nil.of(
            new JAXBElement<>(
                    new QName("eventTime"),
                    OffsetDateTime.class,
                    OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
            )
    );

    @Override
    public Nillable<JAXBElement<OffsetDateTime>> unmarshal(AdaptedOffsetDateTime adapted) {
        if (adapted.nil != null && adapted.nil) {
            JAXBElement<OffsetDateTime> jaxbElement = new JAXBElement<>(
                    new QName("eventTime"),
                    OffsetDateTime.class,
                    null
            );
            jaxbElement.setNil(true);
            // Set attributes (e.g., priority) on jaxbElement if needed
            // In practice, JAXB context handles attribute mapping
            return Nillable.nil(NIL);
        }
        if (adapted.value != null) {
            JAXBElement<OffsetDateTime> jaxbElement = new JAXBElement<>(
                    new QName("eventTime"),
                    OffsetDateTime.class,
                    adapted.value
            );
            // Set attributes
            return Nillable.of(jaxbElement, NIL);
        }
        return Nillable.nullValue(NIL);
    }

    @Override
    public AdaptedOffsetDateTime marshal(Nillable<JAXBElement<OffsetDateTime>> nillable) {
        if (nillable.isNull()) {
            return null;
        }
        AdaptedOffsetDateTime adapted = new AdaptedOffsetDateTime();
        if (nillable.isNil()) {
            adapted.nil = true;
            adapted.value = null;
            // Set attributes from nillable.getValue() if available
            // Example: adapted.priority = extractPriority(nillable.getValue());
        } else {
            adapted.value = nillable.getValue().getValue();
            // Set attributes
        }
        return adapted;
    }
}
