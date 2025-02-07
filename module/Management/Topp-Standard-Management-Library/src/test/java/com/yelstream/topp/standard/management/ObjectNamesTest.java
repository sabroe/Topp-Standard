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

package com.yelstream.topp.standard.management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * Tests {@link ObjectNames}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-07
 */
class ObjectNamesTest {
    /**
     * Verifies that a property-value pattern and a compatible non-pattern can be joined.
     */
    @Test
    void joinPropertyValuePatternWithCompatibleNonPattern() throws MalformedObjectNameException {
        ObjectName a=new ObjectName("org.apache.activemq:type=Broker,brokerName=*");
        ObjectName b=new ObjectName("org.apache.activemq:type=Broker,brokerName=myBroker");

        Assertions.assertTrue(a.isPropertyValuePattern());
        Assertions.assertFalse(b.isPattern());

        ObjectName c1=ObjectNames.join(a,b);
        Assertions.assertEquals(ObjectName.getInstance("org.apache.activemq:type=Broker,brokerName=myBroker"),c1);

        ObjectName c2=ObjectNames.join(b,a);
        Assertions.assertEquals(c1,c2);
    }

    /**
     * Verifies that a property-list pattern and a compatible non-pattern can be joined.
     */
    @Test
    void joinPropertyListPatternWithCompatibleNonPattern() throws MalformedObjectNameException {
        ObjectName a=new ObjectName("org.apache.activemq:type=Broker,*");
        ObjectName b=new ObjectName("org.apache.activemq:type=Broker,destinationType=Queue");

        Assertions.assertTrue(a.isPropertyListPattern());
        Assertions.assertFalse(b.isPattern());

        ObjectName c1=ObjectNames.join(a,b);
        Assertions.assertEquals(ObjectName.getInstance("org.apache.activemq:type=Broker,destinationType=Queue"),c1);

        ObjectName c2=ObjectNames.join(b,a);
        Assertions.assertEquals(c1,c2);
    }

    /**
     * Verifies that a property-list pattern and an incompatible non-pattern can be joined.
     */
    @Test
    void joinPropertyListPatternWithIncompatibleNonPattern() throws MalformedObjectNameException {
        ObjectName a=new ObjectName("org.apache.activemq:type=Broker,*");
        ObjectName b=new ObjectName("org.apache.activemq:type=NonBroker,destinationType=Queue");

        Assertions.assertTrue(a.isPropertyListPattern());
        Assertions.assertFalse(b.isPattern());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ObjectNames.join(a,b);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ObjectNames.join(b,a);
        });
    }

    /**
     * Verifies that a non-pattern and a non-pattern cannot be joined.
     */
    @Test
    void joinNonPatternWithNonPattern() throws MalformedObjectNameException {
        ObjectName a=new ObjectName("org.apache.activemq:type=Broker,brokerName=anotherBroker");
        ObjectName b=new ObjectName("org.apache.activemq:type=Broker,brokerName=myBroker");

        Assertions.assertFalse(a.isPattern());
        Assertions.assertFalse(b.isPattern());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ObjectNames.join(a,b);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ObjectNames.join(b,a);
        });
    }
}
