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

package com.yelstream.topp.standard.xml.bind;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import lombok.experimental.UtilityClass;

/**
 * Utility addressing instances of {@link JAXBContext}.
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2022-04-20
 */
@UtilityClass
public class JAXBContexts {
    /**
     * Create a new JAXB context.
     * @param declaredType Class to be recognized by JAXB context.
     * @return JAXB context.
     * @throws JAXBException Thrown in case of JAXB error.
     */
    public static JAXBContext createJAXBContext(Class<?> declaredType) throws JAXBException {
        return JAXBContext.newInstance(declaredType);
    }

    //TO-DO: Add builder? Binder, JAXBIntrospector, SchemaOutputResolver,

    @lombok.Builder(builderClassName="Builder")
    private static JAXBContext createJAXBContextByBuilder(Class<?> declaredType) throws JAXBException {
        JAXBContext context=JAXBContext.newInstance(declaredType);
/*
        if (properties!=null) {
            properties.forEach((name,value)->setProperty(marshaller,name,value));
        }
        if (schema!=null) {
            marshaller.setSchema(schema);
        }
        if (eventHandler!=null) {
            marshaller.setEventHandler(eventHandler);
        }
        if (adapter!=null) {
            marshaller.setAdapter(adapter);
        }
        if (listener!=null) {
            marshaller.setListener(listener);
        }
        if (attachmentMarshaller!=null) {
            marshaller.setAttachmentMarshaller(attachmentMarshaller);
        }
 */
        return context;
    }


/*
  TO-DO: Introduce something like "JAXBRuntime", possibly test on JAXBContext like below,
    constants for "org.glassfish.jaxb" and "org.eclipse.persistence.jaxb",
    and what about enum "StandardJAXBRuntime" ?!


1 Check the JAXBContext Implementation Class:

The JAXBContext instance is created by the runtime, and its class name indicates the underlying implementation.
GlassFish JAXB typically uses org.glassfish.jaxb.runtime.v2.runtime.JAXBContextImpl.
EclipseLink MOXy uses org.eclipse.persistence.jaxb.JAXBContext.

import jakarta.xml.bind.JAXBContext;

public class CheckJAXBRuntime {
    public static void main(String[] args) throws Exception {
        JAXBContext context = JAXBContext.newInstance(YourClass.class);
        String runtimeClass = context.getClass().getName();
        System.out.println("JAXBContext implementation: " + runtimeClass);

        if (runtimeClass.contains("org.glassfish.jaxb")) {
            System.out.println("Runtime is GlassFish JAXB");
        } else if (runtimeClass.contains("org.eclipse.persistence.jaxb")) {
            System.out.println("Runtime is EclipseLink MOXy");
        } else {
            System.out.println("Unknown JAXB runtime: " + runtimeClass);
        }
    }
}


2 Test for Runtime-Specific Properties:

Each JAXB implementation supports specific properties that can be set on the Marshaller. Attempting to set these properties can reveal the runtime, as unsupported properties throw a jakarta.xml.bind.PropertyException.
GlassFish JAXB supports org.glassfish.jaxb.namespacePrefixMapper.
MOXy supports org.eclipse.persistence.jaxb.MarshallerProperties.NAMESPACE_PREFIX_MAPPER.

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

public class CheckJAXBRuntimeByProperty {
    public static void main(String[] args) throws Exception {
        JAXBContext context = JAXBContext.newInstance(YourClass.class);
        Marshaller marshaller = context.createMarshaller();

        // Test for GlassFish JAXB
        try {
            marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", null);
            System.out.println("Runtime is GlassFish JAXB");
        } catch (jakarta.xml.bind.PropertyException e) {
            System.out.println("Not GlassFish JAXB: " + e.getMessage());
        }

        // Test for EclipseLink MOXy
        try {
            marshaller.setProperty("org.eclipse.persistence.jaxb.MarshallerProperties.NAMESPACE_PREFIX_MAPPER", null);
            System.out.println("Runtime is EclipseLink MOXy");
        } catch (jakarta.xml.bind.PropertyException e) {
            System.out.println("Not EclipseLink MOXy: " + e.getMessage());
        }
    }
}

3 Inspect Classpath Dependencies:

Check the JARs or dependencies in your project’s classpath to infer the runtime.
GlassFish JAXB includes org.glassfish.jaxb:jaxb-runtime (or com.sun.xml.bind:jaxb-impl for older versions).
MOXy includes org.eclipse.persistence:org.eclipse.persistence.moxy.
For Maven, you can inspect the pom.xml or run mvn dependency:tree to see which JAXB-related dependencies are present.

mvn dependency:tree | grep -E 'jaxb|eclipse.persistence'
If you see org.glassfish.jaxb:jaxb-runtime, it’s GlassFish JAXB.
If you see org.eclipse.persistence:org.eclipse.persistence.moxy, it’s MOXy.
Alternatively, programmatically check the classpath:

java

Collapse

Wrap

Copy
public class CheckClasspath {
    public static void main(String[] args) {
        try {
            Class.forName("org.glassfish.jaxb.runtime.v2.runtime.JAXBContextImpl");
            System.out.println("GlassFish JAXB is on the classpath");
        } catch (ClassNotFoundException e) {
            System.out.println("GlassFish JAXB not found");
        }

        try {
            Class.forName("org.eclipse.persistence.jaxb.JAXBContext");
            System.out.println("EclipseLink MOXy is on the classpath");
        } catch (ClassNotFoundException e) {
            System.out.println("EclipseLink MOXy not found");
        }
    }
}


4 Use ServiceLoader to Inspect JAXBContextFactory:

JAXB implementations may register a JAXBContextFactory via the META-INF/services/jakarta.xml.bind.JAXBContextFactory file. You can use ServiceLoader to inspect which factory is loaded.
This is less common but can confirm the runtime if multiple implementations are present.
Example:

java

Collapse

Wrap

Copy
import jakarta.xml.bind.JAXBContextFactory;
import java.util.ServiceLoader;

public class CheckJAXBContextFactory {
    public static void main(String[] args) {
        ServiceLoader<JAXBContextFactory> loader = ServiceLoader.load(JAXBContextFactory.class);
        for (JAXBContextFactory factory : loader) {
            System.out.println("JAXBContextFactory: " + factory.getClass().getName());
            if (factory.getClass().getName().contains("org.glassfish.jaxb")) {
                System.out.println("Runtime is GlassFish JAXB");
            } else if (factory.getClass().getName().contains("org.eclipse.persistence")) {
                System.out.println("Runtime is EclipseLink MOXy");
            }
        }
    }
}
Explanation: The JAXBContextFactory implementation class will indicate the runtime (e.g., org.glassfish.jaxb.runtime.v2.ContextFactory for GlassFish or org.eclipse.persistence.jaxb.JAXBContextFactory for MOXy).


Maven Dependency Example:
For GlassFish JAXB:
xml

Collapse

Wrap

Copy
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>4.0.5</version>
</dependency>
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
    <version>4.0.2</version>
</dependency>
For EclipseLink MOXy:
xml

Collapse

Wrap

Copy
<dependency>
    <groupId>org.eclipse.persistence</groupId>
    <artifactId>org.eclipse.persistence.moxy</artifactId>
    <version>4.0.2</version>
</dependency>
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
    <version>4.0.2</version>
</dependency>



Summary:
To determine if the JAXB runtime is GlassFish or MOXy:

Check the JAXBContext implementation class name (e.g., org.glassfish.jjaxb.runtime.v2.runtime.JAXBContextImpl for GlassFish, org.eclipse.persistence.jaxb.JAXBContext for MOXy).
Test runtime-specific properties (org.glassfish.jaxb.namespacePrefixMapper for GlassFish, org.eclipse.persistence.jaxb.MarshallerProperties.NAMESPACE_PREFIX_MAPPER for MOXy).
Inspect classpath dependencies or use ServiceLoader for JAXBContextFactory. The first method (checking JAXBContext class) is the most straightforward. Ensure your project includes the correct dependencies for the desired runtime to avoid conflicts.

*/

/*
  TO-DO: Solidify marshaller properties for different runtimes:
    1) Property "org.glassfish.jaxb.namespacePrefixMapper"
       Class "org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper"
    2) "org.eclipse.persistence.jaxb.MarshallerProperties.NAMESPACE_PREFIX_MAPPER"
       Expected Object: A Map<String, String> where:
           Key: The namespace URI (e.g., "http://example.com/ns").
           Value: The desired prefix (e.g., "ns1").

https://grok.com/share/bGVnYWN5_f1b2cb69-a923-47bd-9f12-135fdfcf828c
https://grok.com/share/bGVnYWN5_6bd79690-af1c-4c3d-a2b5-3b67f888ba88

 */


}
