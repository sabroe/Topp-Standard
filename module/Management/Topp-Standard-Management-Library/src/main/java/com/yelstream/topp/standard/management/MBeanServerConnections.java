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

import lombok.experimental.UtilityClass;

import javax.management.Attribute;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanFeatureInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Utilities for MBean server connections.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-07
 */
@UtilityClass
public class MBeanServerConnections {

    public static MBeanServerConnection createConnection(JMXConnector connector) throws IOException {
        return builder().connector(connector).build();
    }

    @lombok.Builder(builderClassName="Builder")
    private static MBeanServerConnection buildConnection(JMXConnector connector) throws IOException {
        return connector.getMBeanServerConnection();
    }

    public static class Builder {
        private JMXConnector connector=null;

        public Builder connector(JMXConnector connector) {
            this.connector=connector;
            return this;
        }
    }

    public static MBeanInfo getInfo(MBeanServerConnection connection,
                                    ObjectName objectName) throws IOException {
        try {
            return connection.getMBeanInfo(objectName);
        } catch (InstanceNotFoundException | IntrospectionException | ReflectionException ex) {
            throw new IOException(String.format("Failure to get info; object name is %s",objectName), ex);
        }
    }

    public static List<MBeanAttributeInfo> getAttributeInfos(MBeanServerConnection connection,
                                                             ObjectName objectName) throws IOException {
        return getAttributeInfos(getInfo(connection,objectName));
    }

    public static List<MBeanAttributeInfo> getAttributeInfos(MBeanInfo info) {
        return Arrays.stream(info.getAttributes()).toList();
    }

    public static List<MBeanAttributeInfo> getAttributeInfos(List<MBeanAttributeInfo> infos,
                                                             Predicate<MBeanAttributeInfo> predicate) {
        return infos.stream().filter(predicate).toList();
    }

    public static List<MBeanAttributeInfo> getAttributeInfoReadable(List<MBeanAttributeInfo> infos) {
        return getAttributeInfos(infos,MBeanAttributeInfo::isReadable);
    }

    public static List<String> getAttributeNames(List<MBeanAttributeInfo> infos) {
        return infos.stream().map(MBeanFeatureInfo::getName).toList();
    }

    public static List<String> getAttributeNamesReadable(List<MBeanAttributeInfo> infos) {
        return getAttributeNames(getAttributeInfoReadable(infos));
    }

    public static List<Attribute> getAttributes(MBeanServerConnection connection,
                                                ObjectName objectName,
                                                List<String> attributeNames) throws IOException {
        try {
            return connection.getAttributes(objectName,attributeNames.toArray(new String[0])).asList();
        } catch (InstanceNotFoundException | ReflectionException ex) {
            throw new IOException(String.format("Failure to get attributes; object name is %s", objectName), ex);
        }
    }

    public static List<Attribute> getAttributes(MBeanServerConnection connection,
                                                ObjectName objectName) throws IOException {
        MBeanInfo info=getInfo(connection,objectName);
        return getAttributes(connection,objectName,getAttributeNamesReadable(getAttributeInfos(info)));
    }

/*
    Long consumerCount = (Long) connection.getAttribute(queue, "ConsumerCount");
    Long producerCount = (Long) connection.getAttribute(queue, "ProducerCount");
    Long queueSize = (Long) connection.getAttribute(queue, "QueueSize");
*/

}
