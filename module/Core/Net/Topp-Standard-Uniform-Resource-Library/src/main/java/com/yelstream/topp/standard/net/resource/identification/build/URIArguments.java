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

package com.yelstream.topp.standard.net.resource.identification.build;

import com.yelstream.topp.standard.net.resource.identification.type.JarURIs;
import com.yelstream.topp.standard.net.resource.identification.path.TaggedPath;
import com.yelstream.topp.standard.net.resource.identification.scheme.StandardScheme;
import lombok.experimental.UtilityClass;

import java.util.Properties;
import java.util.stream.Stream;

/**
 * Utility addressing instances of {@link URIArgument}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-23
 */
@UtilityClass
public class URIArguments {
    /**
     *
     */
    public static String getEntry(URIArgument argument) {
        return JarURIs.SplitURI.of(argument.getParsable()).getEntry();
    }

    /**
     *
     */
    public static URIArgument correctArgumentForFile(URIArgument argument) {
        StandardScheme.File.requireMatch(argument.getScheme());
        if (argument.getSchemeSpecificPart().startsWith("//")) {
            if (argument.getHost()==null) {
                argument=argument.toBuilder().host("").build();
            }
        }
        return argument;
    }

    /**
     *
     */
    public static String getTag(URIArgument argument) {
        return TaggedPath.ofPath(argument.getPath()).getTag();
    }

    /**
     *
     */
    public static Properties getProperties(URIArgument argument) {
        //TO-DO: Create something equivalent to JarURIs.SplitURI, but for JDBC-URLs like "jdbc:sqlserver://localhost:1433;databaseName=database1;user=user1;password=password1;encrypt=true;trustServerCertificate=false".
        String schemeSpecificPart=argument.getSchemeSpecificPart();
        int index=schemeSpecificPart.indexOf(";");
        if (index==-1) {
            return new Properties();
        }
        Properties properties=new Properties();
        String propertiesString=schemeSpecificPart.substring(index+1);
        Stream.of(propertiesString.split(";"))
            .map(String::trim)
            .filter(prop-> !prop.isEmpty())
            .forEach(prop -> {
                String[] keyValue=prop.split("=",2);
                if (keyValue.length==2) {
                    properties.setProperty(keyValue[0].trim(),keyValue[1].trim());
                }
            });
        return properties;
    }
}
