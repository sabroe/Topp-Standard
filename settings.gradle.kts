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

rootProject.name = "Yelstream Topp Standard Project"

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
fileTree("gradle/global") { include("**/*.gradle", "**/*.gradle.kts") }.forEach { apply(from = it) }
includeBuild("convention")

include("module:Topp-Standard-Core-Library")
// "Topp-Standard-Inject-Library"

include("module:Logging:SLF4J:Topp-Standard-Logging-Assistance-For-SLF4J-Library")
include("module:Logging:SLF4J:Topp-Standard-Logging-Resistance-For-SLF4J-Library")

include("module:Health:Topp-Standard-Health-Serialization-Library")
include("module:Health:Topp-Standard-MicroProfile-Health-Library")
include("module:Health:Topp-Standard-Spring-Boot-Actuator-Health-Library")

include("module:Configuration:Topp-Standard-MicroProfile-Config-Library")
include("module:Configuration:Topp-Standard-SmallRye-Config-Library")
include("module:Configuration:Topp-Standard-Quarkus-Config-Library")

include("module:Network:Topp-Standard-Network-Tooling-Library")

include("module:Topp-Standard-Annotator-Library")

include("module:Build:Topp-Standard-Gradle-Contribution-Library")

include("module:Management:Topp-Standard-Management-Library")

include("module:Media:Apache:Topp-Standard-Apache-POI-Document-Library")

//TODO! include("module:Messaging:Apache:Topp-Standard-Apache-ActiveMQ-Classic-Library")
//TODO! include("module:Messaging:Apache:Topp-Standard-Apache-ActiveMQ-Artemis-Library")
//TODO! include("module:Messaging:Jakarta:Topp-Standard-Jakarta-JMS-Library")
include("module:Messaging:SmallRye:Topp-Standard-SmallRye-Reactive-Messaging-Library")
//include("module:Messaging:LadyBug:Topp-Standard-LadyBug-Library")

include("module:Data:Jakarta:JAXB:Topp-Standard-Jakarta-JAXB-Library")

include("module:Data:XSD:W3C:Topp-Standard-W3C-XML-Signature-Schema-Library")

include("module:Data:XML:Topp-Standard-XML-Process-Library")
include("module:Data:XML:Topp-Standard-XML-Bind-Library")
include("module:Data:XML:Topp-Standard-XML-Stream-Library")
include("module:Data:XML:Topp-Standard-XML-Crypto-Library")
include("module:Data:XML:Topp-Standard-XML-Easy-Library")
include("module:Data:XML:Topp-Standard-XML-Library")

include("module:Text:Topp-Standard-Text-Manifestation-Library")

include("module:Interoperation:Topp-Standard-Scriptarium-Interoperability-Library")
include("module:Interoperation:Topp-Standard-Polyglarium-Interoperability-Library")

include("module:Core:Topp-Standard-Resource-Library")
include("module:Core:Topp-Standard-Core-Language-Library")
include("module:Core:Topp-Standard-Core-Network-Library")
include("module:Core:Topp-Standard-Core-IO-Library")
include("module:Core:Topp-Standard-Core-Time-Library")
include("module:Core:Topp-Standard-Core-Reflection-Library")
include("module:Core:Topp-Standard-Core-Annotation-Library")
include("module:Core:Topp-Standard-Core-Function-Library")
include("module:Core:Topp-Standard-Core-Concurrent-Library")
include("module:Core:Topp-Standard-Core-Collection-Library")

include("module:Core:Collection:Topp-Standard-Stream-Collection-Library")
include("module:Core:Collection:Topp-Standard-Stream-Gatherer-Library")
include("module:Core:System:Topp-Standard-System-Holder-Library")
include("module:Core:System:Topp-Standard-System-Loader-Library")
include("module:Core:IO:Topp-Standard-Dual-Access-IO-Library")
include("module:Core:Net:Topp-Standard-Uniform-Resource-Library")
include("module:Core:Text:Topp-Standard-Regular-Expression-Library")

//include("module:Core:Collection:Topp-Standard-Instance-Discovery-Collection-Library")
//include("module:Core:Annotation:Topp-Standard-WIP-Annotation-Library")
//include("module:Core:Net:Topp-Standard-Uniform-Resource-Library")


include("module:Domain:Commerce:Persistence:Topp-Standard-Domain-Commerce-Persistence-Core-Library")
//include("module:Demo:Application:Commerce:-Library")

//include("module:AI:MCP:Demo:MCP-Server-Spring-Application")
//include("module:AI:MCP:Demo:MCP-Server-Quarkus-Application")
include("module:AI:MCP:Demo:MCP-Server-Library")

include("module:Topp-Standard-Nillable-Library")
