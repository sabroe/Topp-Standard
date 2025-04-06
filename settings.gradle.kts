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

rootProject.name = "Yelstream Topp Standard Project"

include(
    "module:Topp-Standard-Core-Library"
    // "Topp-Standard-Inject-Library"
)

include(
    "module:Logging:SLF4J:Topp-Standard-Logging-Assistance-For-SLF4J-Library",
    "module:Logging:SLF4J:Topp-Standard-Logging-Resistance-For-SLF4J-Library"
)

include(
    "module:Health:Topp-Standard-Health-Serialization-Library",
    "module:Health:Topp-Standard-MicroProfile-Health-Library",
    "module:Health:Topp-Standard-Spring-Boot-Actuator-Health-Library"
)

include(
    "module:Configuration:Topp-Standard-MicroProfile-Config-Library",
    "module:Configuration:Topp-Standard-SmallRye-Config-Library",
    "module:Configuration:Topp-Standard-Quarkus-Config-Library"
)

include(
    "module:Network:Topp-Standard-Network-Tooling-Library"
)

include(
    "module:Topp-Standard-Annotator-Library"
)

include(
    "module:Build:Topp-Standard-Gradle-Contribution-Library"
)

include(
    "module:Management:Topp-Standard-Management-Library"
)

include(
    "module:Data:XML:Topp-Standard-XML-Empress-Library"
)

include(
    "module:Text:Topp-Standard-Text-Manifestation-Library"
)

include(
    "module:Interoperation:Topp-Standard-Scriptarium-Interoperability-Library",
    "module:Interoperation:Topp-Standard-Polyglarium-Interoperability-Library"
)

include(
    "module:Core:Topp-Standard-Core-Language-Library",
    "module:Core:Topp-Standard-Core-Network-Library",
    "module:Core:Topp-Standard-Core-IO-Library",
    "module:Core:Topp-Standard-Core-Time-Library",
    "module:Core:Topp-Standard-Core-Reflection-Library",
    "module:Core:Topp-Standard-Core-Annotation-Library",
    "module:Core:Topp-Standard-Core-Function-Library",
    "module:Core:Topp-Standard-Core-Concurrent-Library",
    "module:Core:Topp-Standard-Core-Collection-Library"
)
