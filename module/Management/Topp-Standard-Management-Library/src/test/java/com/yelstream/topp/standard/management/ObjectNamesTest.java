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

package com.yelstream.topp.standard.management;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests {@link ObjectNames}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-07
 */
class ObjectNamesTest {
    @Test
    void baselineAllPattern() {
        ObjectName name=ObjectName.WILDCARD;
        Assertions.assertTrue(name.isPattern());
        Assertions.assertTrue(name.isDomainPattern());
        Assertions.assertTrue(name.isPropertyPattern());
        Assertions.assertFalse(name.isPropertyValuePattern());
        Assertions.assertTrue(name.isPropertyListPattern());
    }

    @Test
    void baselineDomainPattern() throws MalformedObjectNameException {
        ObjectName name=new ObjectName("*:x=y");
        Assertions.assertTrue(name.isPattern());
        Assertions.assertTrue(name.isDomainPattern());
        Assertions.assertFalse(name.isPropertyPattern());
        Assertions.assertFalse(name.isPropertyValuePattern());
        Assertions.assertFalse(name.isPropertyListPattern());
    }

    @Test
    void baselinePropertyPattern() throws MalformedObjectNameException {
        ObjectName name=new ObjectName("domain:x=*");
        Assertions.assertTrue(name.isPattern());
        Assertions.assertFalse(name.isDomainPattern());
        Assertions.assertTrue(name.isPropertyPattern());
        Assertions.assertTrue(name.isPropertyValuePattern());
        Assertions.assertFalse(name.isPropertyListPattern());
    }

    @Test
    void baselinePropertyListPattern() throws MalformedObjectNameException {
        ObjectName name=new ObjectName("domain:x=y,*");
        Assertions.assertTrue(name.isPattern());
        Assertions.assertFalse(name.isDomainPattern());
        Assertions.assertTrue(name.isPropertyPattern());
        Assertions.assertFalse(name.isPropertyValuePattern());
        Assertions.assertTrue(name.isPropertyListPattern());
    }

    @Test
    void baselinePropertyValueAndListPattern() throws MalformedObjectNameException {
        ObjectName name=new ObjectName("domain:x=*,*");
        Assertions.assertTrue(name.isPattern());
        Assertions.assertFalse(name.isDomainPattern());
        Assertions.assertTrue(name.isPropertyPattern());
        Assertions.assertTrue(name.isPropertyValuePattern());
        Assertions.assertTrue(name.isPropertyListPattern());
    }



    @Test
    void buildAllPattern() throws MalformedObjectNameException {
        ObjectName name=ObjectNames.builder().domain("*").propertyPattern().build();
        Assertions.assertEquals(ObjectName.WILDCARD,name);
        Assertions.assertTrue(name.isPattern());
        Assertions.assertTrue(name.isDomainPattern());
        Assertions.assertTrue(name.isPropertyPattern());
        Assertions.assertFalse(name.isPropertyValuePattern());
        Assertions.assertTrue(name.isPropertyListPattern());
    }

    @Test
    void buildDomainPattern() throws MalformedObjectNameException {
        ObjectName name=ObjectNames.builder().domain("*").property("x","y").build();
        Assertions.assertEquals(ObjectName.getInstance("*:x=y"),name);
        Assertions.assertTrue(name.isPattern());
        Assertions.assertTrue(name.isDomainPattern());
        Assertions.assertFalse(name.isPropertyPattern());
        Assertions.assertFalse(name.isPropertyValuePattern());
        Assertions.assertFalse(name.isPropertyListPattern());
    }

    @Test
    void buildPropertyPattern() throws MalformedObjectNameException {
        ObjectName name=ObjectNames.builder().domain("domain").keyPattern("x").build();
        Assertions.assertEquals(ObjectName.getInstance("domain:x=*"),name);
        Assertions.assertTrue(name.isPattern());
        Assertions.assertFalse(name.isDomainPattern());
        Assertions.assertTrue(name.isPropertyPattern());
        Assertions.assertTrue(name.isPropertyValuePattern());
        Assertions.assertFalse(name.isPropertyListPattern());
    }

    @Test
    void buildPropertyListPattern() throws MalformedObjectNameException {
        ObjectName name=ObjectNames.builder().domain("domain").property("x","y").propertyPattern().build();
        Assertions.assertEquals(ObjectName.getInstance("domain:x=y,*"),name);
        Assertions.assertTrue(name.isPattern());
        Assertions.assertFalse(name.isDomainPattern());
        Assertions.assertTrue(name.isPropertyPattern());
        Assertions.assertFalse(name.isPropertyValuePattern());
        Assertions.assertTrue(name.isPropertyListPattern());
    }

    @Test
    void buildPropertyValueAndListPattern() throws MalformedObjectNameException {
        ObjectName name=ObjectNames.builder().domain("domain").keyPattern("x").propertyPattern().build();
        Assertions.assertEquals(ObjectName.getInstance("domain:x=*,*"),name);
        Assertions.assertTrue(name.isPattern());
        Assertions.assertFalse(name.isDomainPattern());
        Assertions.assertTrue(name.isPropertyPattern());
        Assertions.assertTrue(name.isPropertyValuePattern());
        Assertions.assertTrue(name.isPropertyListPattern());
    }




    /**
     * Tests {@link ObjectNames#intersect(ObjectName, ObjectName)};
     * verifies that a property-value pattern and a compatible non-pattern can be joined.
     * Verifies symmetry.
     */
    @Test
    void intersectPropertyValuePatternWithCompatibleNonPattern() throws MalformedObjectNameException {
        ObjectName a=new ObjectName("org.apache.activemq:type=Broker,brokerName=*");
        ObjectName b=new ObjectName("org.apache.activemq:type=Broker,brokerName=myBroker");

        Assertions.assertTrue(a.isPropertyValuePattern());
        Assertions.assertFalse(b.isPattern());

        ObjectName c1=ObjectNames.intersect(a,b);
        Assertions.assertEquals(ObjectName.getInstance("org.apache.activemq:type=Broker,brokerName=myBroker"),c1);
        Assertions.assertFalse(c1.isPattern());

        ObjectName c2=ObjectNames.intersect(b,a);
        Assertions.assertEquals(c1,c2);
        Assertions.assertFalse(c2.isPattern());
    }

    /**
     * Tests {@link ObjectNames#intersect(ObjectName, ObjectName)};
     * verifies that a property-list pattern and a compatible non-pattern can be joined.
     * Verifies symmetry.
     */
    @Test
    void intersectPropertyListPatternWithCompatibleNonPattern() throws MalformedObjectNameException {
        ObjectName a=new ObjectName("org.apache.activemq:type=Broker,*");
        ObjectName b=new ObjectName("org.apache.activemq:type=Broker,destinationType=Queue");

        Assertions.assertTrue(a.isPropertyListPattern());
        Assertions.assertFalse(b.isPattern());
        
        ObjectName c1=ObjectNames.intersect(a,b);
        Assertions.assertEquals(ObjectName.getInstance("org.apache.activemq:type=Broker,destinationType=Queue"),c1);
        Assertions.assertFalse(c1.isPattern());

        ObjectName c2=ObjectNames.intersect(b,a);
        Assertions.assertEquals(c1,c2);
        Assertions.assertFalse(c2.isPattern());
    }

    /**
     * Tests {@link ObjectNames#intersect(ObjectName, ObjectName)};
     * verifies that a property-list pattern and an incompatible non-pattern can be joined.
     * Verifies symmetry.
     */
    @Test
    void intersectPropertyListPatternWithIncompatibleNonPattern() throws MalformedObjectNameException {
        ObjectName a=new ObjectName("org.apache.activemq:type=Broker,*");
        ObjectName b=new ObjectName("org.apache.activemq:type=NonBroker,destinationType=Queue");

        Assertions.assertTrue(a.isPropertyListPattern());
        Assertions.assertFalse(b.isPattern());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ObjectNames.intersect(a,b);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ObjectNames.intersect(b,a);
        });
    }

    /**
     * Tests {@link ObjectNames#intersect(ObjectName, ObjectName)};
     * verifies that two non-patterns with different key-value values cannot be joined.
     * Verifies symmetry.
     */
    @Test
    void intersectNonPatternsWithDifferentValues() throws MalformedObjectNameException {
        ObjectName a=new ObjectName("org.apache.activemq:type=Broker,brokerName=anotherBroker");
        ObjectName b=new ObjectName("org.apache.activemq:type=Broker,brokerName=myBroker");

        Assertions.assertFalse(a.isPattern());
        Assertions.assertFalse(b.isPattern());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ObjectNames.intersect(a,b);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ObjectNames.intersect(b,a);
        });
    }

    /**
     * Tests {@link ObjectNames#intersect(ObjectName, ObjectName)};
     * verifies that two non-patterns with different key-value keys cannot be joined.
     * Verifies symmetry.
     */
//FIX!    @Test
    void intersectNonPatternsWithDifferentKeys() throws MalformedObjectNameException {
        ObjectName a=new ObjectName("org.apache.activemq:type=Broker,destinationType=Queue");
        ObjectName b=new ObjectName("org.apache.activemq:type=Broker,destinationName=XXX");

        Assertions.assertFalse(a.isPattern());
        Assertions.assertFalse(b.isPattern());


        ObjectName c1=ObjectNames.intersect(a,b);
        Assertions.assertEquals(ObjectName.getInstance("org.apache.activemq:type=Broker"),c1);
        Assertions.assertFalse(c1.isPattern());

        ObjectName c2=ObjectNames.intersect(b,a);
        Assertions.assertEquals(c1,c2);
        Assertions.assertFalse(c2.isPattern());
    }

    @Test
    void contract1() throws MalformedObjectNameException {
        ObjectName a = new ObjectName("domain:type=*");
        ObjectName b = new ObjectName("domain:type=Queue");

        Map<String, String> map = new HashMap<>();
        ObjectNames.contract(a, b, map, "type");
        Assertions.assertEquals(Map.of("type","Queue"),map);
    }

    @Test
    void contract2() throws MalformedObjectNameException {
        ObjectName a = new ObjectName("domain:type=Broker");
        ObjectName b = new ObjectName("domain:type=Broker");

        Map<String, String> map = new HashMap<>();
        ObjectNames.contract(a, b, map, "type");

        Assertions.assertEquals(Map.of("type","Broker"),map);
    }

    @Test
    void contract3() throws MalformedObjectNameException {
        ObjectName a = new ObjectName("domain:type=Queue");
        ObjectName b = new ObjectName("domain:type=Topic");

        Map<String, String> map = new HashMap<>();
        ObjectNames.contract(a, b, map, "type");

        Assertions.assertEquals(Map.of(),map);
    }





    @Test
    void expand1() throws MalformedObjectNameException {
        ObjectName a = new ObjectName("domain:type=*");
        ObjectName b = new ObjectName("domain:type=Queue");

        Map<String, String> map = new HashMap<>();
        ObjectNames.expand(a, b, map, "type");

        Assertions.assertEquals(Map.of("type","*"),map);
    }

    @Test
    void expand2() throws MalformedObjectNameException {
        ObjectName a = new ObjectName("domain:type=Broker");
        ObjectName b = new ObjectName("domain:type=Broker");

        Map<String, String> map = new HashMap<>();
        ObjectNames.expand(a, b, map, "type");

        Assertions.assertEquals(Map.of("type","Broker"),map);
    }

    @Test
    void expand3() throws MalformedObjectNameException {
        ObjectName a = new ObjectName("domain:type=Queue");
        ObjectName b = new ObjectName("domain:type=Topic");

        Map<String, String> map = new HashMap<>();
        ObjectNames.expand(a, b, map, "type");

        Assertions.assertEquals(Map.of("type","*"),map);
    }






}
