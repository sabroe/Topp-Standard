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

import lombok.Singular;
import lombok.experimental.UtilityClass;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

/**
 * Utilities for object names for MBeans.
 * <p>
 *     Note: Beware that functionalities beyond basic creation and builder is WIP!
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-07
 */
@UtilityClass
public class ObjectNames {
    /**
     * Creates a new object name as a property-list pattern.
     * @param domain Domain.
     *               <p>
     *                   Must not be {@code null}.
     *               </p>
     * @return Created object name.
     * @throws MalformedObjectNameException Thrown in case of a malformed object name.
     */
    public static ObjectName createObjectNameByDomain(String domain) throws MalformedObjectNameException {
        Objects.requireNonNull(domain,"Failure to create object name; domain must be set!");
        return new ObjectName(domain+":*");
    }

    /**
     * Creates a new object name.
     * @param domain Domain.
     *               <p>
     *                   Must not be {@code null}.
     *               </p>
     * @param properties Properties.
     *                   <p>
     *                     This may be {@code null}.
     *                   </p>
     * @return Created object name.
     * @throws MalformedObjectNameException Thrown in case of a malformed object name.
     */
    public static ObjectName createObjectName(String domain,
                                              Map<String,String> properties) throws MalformedObjectNameException {
        Objects.requireNonNull(domain,"Failure to create object name; domain must be set!");
        ObjectName name;
        if (properties==null || properties.isEmpty()) {
            name=createObjectNameByDomain(domain);
        } else {
            name=new ObjectName(domain,new Hashtable<>(properties));
        }
        return name;
    }

    /**
     * Creates a new object name.
     * @param domain Domain.
     *               <p>
     *                   Must not be {@code null}.
     *               </p>
     * @param properties Properties.
     *                   <p>
     *                     This may be {@code null}.
     *                   </p>
     * @param propertyListPattern Indicates, if created object name should be a property list pattern.
     * @return Created object name.
     * @throws MalformedObjectNameException Thrown in case of a malformed object name.
     */
    @lombok.Builder(builderClassName="Builder")
    public static ObjectName createObjectName(String domain,
                                              @Singular Map<String,String> properties,
                                              boolean propertyListPattern) throws MalformedObjectNameException {
        ObjectName name=createObjectName(domain,properties);
        if (propertyListPattern) {
            name=ensurePropertyListPattern(name);
        }
        return name;
    }

    /**
     * Ensures that an object name is a property-list pattern.
     * @param name Object name.
     *             This may or may not be a property-list pattern.
     * @return Object name as a property-list pattern.
     * @throws MalformedObjectNameException Thrown in case of a malformed object name.
     */
    public static ObjectName ensurePropertyListPattern(ObjectName name) throws MalformedObjectNameException {
        if (!name.isPropertyListPattern()) {
            StringBuilder sb=new StringBuilder();
            sb.append(name.getKeyPropertyListString());
            if (!sb.isEmpty()) {
                sb.append(",");
            }
            sb.append("*");
            name=new ObjectName(name.getDomain()+":"+sb);
        }
        return name;
    }

    public static class Builder {
        public Builder domainPattern() {
            return domain("*");
        }

        public Builder keyPattern(String propertyKey) {
            return property(propertyKey,"*");
        }

        public Builder valuePattern(String propertyKey) {
            return property(propertyKey,"*");
        }

        public Builder propertyPattern() {
            return propertyListPattern(true);
        }
    }

    /**
     * Find the most specific subset of key-value pairs common to two maps.
     * Keep entries where both maps have the same key and value, ignoring pattern values.
     * <p>
     * Strict and symmetric.
     * </p>
     * @param a First name.
     *          <p>
     *              Must not be {@code null}.
     *          </p>
     * @param b Second name.
     *          <p>
     *              Must not be {@code null}.
     *          </p>
     * @return A new ObjectName containing merged properties.
     * @throws MalformedObjectNameException If the resulting ObjectName is invalid.
     * @throws IllegalArgumentException     If the ObjectNames cannot be joined due to conflicting rules.
     */
    public static ObjectName intersect(ObjectName a, ObjectName b) throws MalformedObjectNameException {
        Objects.requireNonNull(a,"Failure to join; first name must be set!");
        Objects.requireNonNull(b,"Failure to join; second name must be set!");

        String domain = chooseDomain(a.getDomain(), b.getDomain());

        Hashtable<String, String> properties = new Hashtable<>();

        Map<String, String> aProps = a.getKeyPropertyList();
        Map<String, String> bProps = b.getKeyPropertyList();

        for (String key : aProps.keySet()) {
            String aValue = aProps.get(key);
            String bValue = bProps.get(key);

            if (bValue == null) {
                if (b.isPropertyListPattern()) {
                    properties.put(key, aValue);
                } else {
                    properties.put(key, aValue);
                }
            } else if (aValue.equals("*")) {
                properties.put(key, bValue);
            } else if (bValue.equals("*")) {
                properties.put(key, aValue);
            } else if (aValue.equals(bValue)) {
                properties.put(key, aValue);
            } else {
                throw new IllegalArgumentException("Conflicting values for key '" + key + "': "
                        + aValue + " vs " + bValue);
            }
        }

/*
        for (String key : bProps.keySet()) {
            if (!properties.containsKey(key)) {
                if (a.isPropertyListPattern()) {
                    properties.put(key, bProps.get(key));
                }
            }
        }
*/
        for (String key : bProps.keySet()) {
            if (!aProps.containsKey(key)) {
                if (a.isPropertyListPattern()) {
                    properties.put(key, bProps.get(key));
                }
            }
        }

        return new ObjectName(domain,properties);
    }

    /**
     * Chooses the best domain based on the priority rule:
     * - Specific domain (non-null, non-"*", non-"")
     * - "*" (pattern domain)
     * - "" (empty domain)
     * - null (only if both are null)
     *
     * @param domainA The first domain.
     * @param domainB The second domain.
     * @return The chosen domain based on priority.
     */
    private static String chooseDomain(String domainA, String domainB) {
        if (Objects.equals(domainA, domainB)) {
            return domainA;
        }
        if (isSpecificDomain(domainA)) return domainA;
        if (isSpecificDomain(domainB)) return domainB;
        if ("*".equals(domainA)) return domainA;
        if ("*".equals(domainB)) return domainB;
        if ("".equals(domainA)) return domainA;
        if ("".equals(domainB)) return domainB;
        return null;
    }

    /**
     * Checks if a domain is specific (non-null, non-"*", non-"").
     *
     * @param domain The domain to check.
     * @return True if the domain is specific.
     */
    private static boolean isSpecificDomain(String domain) {
        return domain != null && !domain.equals("*") && !domain.isEmpty();
    }





/* Types of "Join-ish" Operations
Intersection (Strict Join, Symmetric)
	"org.apache.activemq:type=Broker,*" + "org.apache.activemq:type=Broker,destinationType=Queue" → "org.apache.activemq:type=Broker,destinationType=Queue"
		Produces the most specific non-pattern possible from both names. Strict and symmetric.


Generalized Composition (Non-Symmetric Expansion)
	"org.apache.activemq:type=Broker,*" + "org.apache.activemq:type=Broker,destinationType=Queue" → "org.apache.activemq:type=Broker,destinationType=Queue,*"
		Expands an existing pattern to accommodate a more specific name while keeping it a pattern.


Cumulative Composition (Pattern Accumulation)
	"org.apache.activemq:type=Broker,*" → "org.apache.activemq:type=Broker,destinationType=Queue,*" → "org.apache.activemq:type=Broker,destinationType=Queue,destinationName=XXX,*"
		A series of compositions where each new key-value pair gets added while preserving wildcard expandability.


Finalization (Closing the Pattern)
	"org.apache.activemq:type=Broker,destinationType=Queue,destinationName=XXX,*" → "org.apache.activemq:type=Broker,destinationType=Queue,destinationName=XXX"
		Removes all pattern symbols (*), finalizing a fully specific name.


Subsetting (Partial Projection)
	"org.apache.activemq:type=Broker,destinationType=Queue,destinationName=XXX" → "org.apache.activemq:type=Broker,destinationType=Queue"
		Removes some keys to get a less specific but still valid name.


Generalization (Pattern Relaxation)
	"org.apache.activemq:type=Broker,destinationType=Queue,destinationName=XXX" → "org.apache.activemq:type=Broker,destinationType=Queue,*"
		Expands an existing specific name back into a pattern by reintroducing *.


Merging (Union-like Combination)
	"org.apache.activemq:type=Broker,destinationType=Queue" + "org.apache.activemq:type=Broker,destinationName=XXX" → "org.apache.activemq:type=Broker,destinationType=Queue,destinationName=XXX"
		Combines non-overlapping key-value pairs, creating a merged form.


 */


/* Defining the Operations on Map<K, V>

Intersection (Strict Join)
	Find the most specific subset of key-value pairs common to two maps.
		Keep entries where both maps have the same key and value, ignoring pattern values.

Generalized Composition
	Expand an existing pattern to include a new specific key-value pair.
	If Predicate<K> or Predicate<V> matches a wildcard, extend it with the new entry.

Sequential Composition
	Apply multiple expansions step by step.
		Process a list of maps iteratively, merging only valid expansions.

Finalization (Closing the Pattern)
	Remove all wildcards and turn the map into a concrete key-value set.
		Strip out * entries and replace them with fixed values.

Subsetting (Projection)
	Remove some keys or values based on a condition.
		Filter the map using a Predicate<K> or Predicate<V>.

Generalization (Pattern Relaxation)
	Convert a fixed name back into a pattern.
		Replace certain values with * based on a Predicate<V>.

Merging (Union-like Combination)
	Merge maps without overriding existing values.
		Combine key-values while allowing patterns to absorb new values.

 */







    /*private*/ static void contract(ObjectName a,
                                 ObjectName b,
                                 Map<String,String> map,
                                 String key) {
        String aValue = a.getKeyProperty(key);
        String bValue = b.getKeyProperty(key);

        if (aValue == null && bValue == null) {
            map.remove(key);
            return;
        }

        if ("*".equals(aValue) && bValue != null) {
            map.put(key, bValue);
        } else if ("*".equals(bValue) && aValue != null) {
            map.put(key, aValue);
        } else if (aValue != null && aValue.equals(bValue)) {
            map.put(key, aValue);
        } else {
            map.remove(key);
        }
    }

    /*private*/ static void expand(ObjectName a,
                               ObjectName b,
                               Map<String,String> map,
                               String key) {
        String aValue = a.getKeyProperty(key);
        String bValue = b.getKeyProperty(key);

        if ("*".equals(aValue) || "*".equals(bValue) || (aValue != null && bValue != null && !aValue.equals(bValue))) {
            map.put(key, "*");
        } else if (aValue != null) {
            map.put(key, aValue);
        } else if (bValue != null) {
            map.put(key, bValue);
        }
    }




    /*private*/ static void expand1(ObjectName a,
                                    ObjectName b,
                                    Map<String,String> map,
                                    String key) {
        String aValue = a.getKeyProperty(key);
        String bValue = b.getKeyProperty(key);

        boolean aPattern = a.isPropertyListPattern();
        boolean bPattern = b.isPropertyListPattern();

        if (aValue == null && bValue == null) {
            return;
        }

        if ("*".equals(aValue) || "*".equals(bValue) ||
                (aValue != null && bValue != null && !aValue.equals(bValue))) {
            map.put(key, "*");
        }
        else if (aValue != null && bValue == null && bPattern) {
            map.put(key, aValue);
        }
        else if (bValue != null && aValue == null && aPattern) {
            map.put(key, bValue);
        }
        else if (aValue != null) {
            map.put(key, aValue);
        }
    }



}
