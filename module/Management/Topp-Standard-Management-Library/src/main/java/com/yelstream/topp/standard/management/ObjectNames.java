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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

/**
 * Utilities for object names for MBeans.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-07
 */
@UtilityClass
public class ObjectNames {
    @lombok.Builder(builderClassName="Builder")
    private ObjectName createObjectName(String domain,
                                        @Singular Map<String,String> properties) throws MalformedObjectNameException {
        return new ObjectName(domain,new Hashtable<>(properties));
    }

    /**
     * Joins two object names symmetrically, ensuring the result is the same regardless of argument order.
     *
     * @param a The first ObjectName (must not be null).
     * @param b The second ObjectName (must not be null).
     * @return A new ObjectName containing merged properties.
     * @throws MalformedObjectNameException If the resulting ObjectName is invalid.
     * @throws IllegalArgumentException     If the ObjectNames cannot be joined due to conflicting rules.
     */
    public ObjectName join(ObjectName a, ObjectName b) throws MalformedObjectNameException {
        Objects.requireNonNull(a, String.format("Failure to join; first object name must be set."));
        Objects.requireNonNull(b, String.format("Failure to join; second object name must be set."));

        String domain = chooseDomain(a.getDomain(), b.getDomain());

        Hashtable<String, String> properties = new Hashtable<>();

        Map<String, String> aProps = a.getKeyPropertyList();
        Map<String, String> bProps = b.getKeyPropertyList();

        for (String key : aProps.keySet()) {
            String aValue = aProps.get(key);
            String bValue = bProps.get(key);

            if (bValue == null) {
                // Add key if b is a property-list-pattern
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

        for (String key : bProps.keySet()) {
            if (!properties.containsKey(key)) {
                // Add key if a is a property-list-pattern
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


    /**
     * Joins two object names symmetrically, ensuring the result is the same regardless of argument order.
     *
     * @param a The first ObjectName (must not be null).
     * @param b The second ObjectName (must not be null).
     * @return A new ObjectName containing merged properties.
     * @throws MalformedObjectNameException If the resulting ObjectName is invalid.
     * @throws IllegalArgumentException     If the ObjectNames cannot be joined due to conflicting rules.
     */
    private static Map<String,String> joinProperties(ObjectName a, ObjectName b) throws MalformedObjectNameException {
        Objects.requireNonNull(a,String.format("Failure to join; first object name must be set."));
        Objects.requireNonNull(b,String.format("Failure to join; second object name must be set."));

        Map<String,String> properties=new HashMap<>();

        Map<String,String> aProps=a.getKeyPropertyList();
        Map<String,String> bProps=b.getKeyPropertyList();

        for (String key: aProps.keySet()) {
            String aValue=aProps.get(key);
            String bValue=bProps.get(key);

            if (bValue==null) {
                // Add key if b is a property-list-pattern
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

        for (String key : bProps.keySet()) {
            if (!properties.containsKey(key)) {
                // Add key if a is a property-list-pattern
                if (a.isPropertyListPattern()) {
                    properties.put(key, bProps.get(key));
                }
            }
        }

        return properties;
    }
}
