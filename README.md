# Topp Standard @ Yelstream

Interoperable Java libraries for standards-compliant, production-ready systems — built as composable runtime components.

Topp Standard is a collection of modular Java libraries developed in the open and published continuously to Maven Central.  
Each module evolves independently — from early experimentation to stable production use — while remaining usable and versioned at every meaningful stage.

Rather than forming a single framework, the project focuses on small, focused building blocks that can be combined and integrated across Java SE and Jakarta EE applications.



## How I think about systems

Composable runtime systems are systems where behavior is built from small, well-defined, replaceable parts — combined at the edges, not hardwired inside.

I design systems as composable runtime structures, independent components that can be combined, replaced, or relocated without upfront coupling decisions.

This perspective unifies composition, correctness, evolution, and operability.



### Composition

Systems are dynamically assembled from independent components with explicit binding decisions made at the application level.

- Composition is defined at the application level, not embedded in frameworks  
- Protocol encoding is separate from transport  
- Components operate in-process or across processes  



### Structure

Systems are governed by static invariants that define correctness and coupling constraints.

- Contracts define structural boundaries between components  
- Dependencies are strictly directional and acyclic — cycles are design failures  
- State and behavior are localized to maintain high cohesion and avoid hidden coupling  
- Complexity is structured, not distributed — local scope remains simple, system behavior remains rich  
- Concerns evolve along independent axes without forced synchronization  



### Operation

Systems are designed for runtime observability and behavioral transparency.

- Components expose behavior through stable interfaces  
- Execution is understandable without inspecting internal implementation  
- Components are observable and testable in isolation  
- Integration points define runtime boundaries between components  



### Evolution

Systems evolve continuously over time rather than remaining fixed artifacts.

- Code starts as exploration, then becomes structure  
- Components are named, shaped, and refined through use  
- Libraries may be split, merged, or re-scoped as understanding improves  
- Boundaries and responsibilities are not final at creation time  

Evolution is constrained by structural invariants and enabled by composability.


The system as a whole evolves continuously rather than through rewrites. 
Coherence emerges from this structured evolution, not from scale.




## Featured Modules

A selection of refined, production-ready modules. Each module is independently versioned and published.

| Artifact @ Maven Central                                                                                                                     | JPMS Module @ JavaDoc                                                                                                                       | Gradle Module @ GitHub                                                                                                                                                             |
|----------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [`topp-standard-operation-comparison`](https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-operation-comparison) | [`com.yelstream.topp.standard.operation.comparison`](https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-operation-comparison) | [`:Core:Operation:Topp-Standard-Operation-Comparison-Library`](https://github.com/sabroe/Topp-Standard/tree/main/module/Core/Operation/Topp-Standard-Operation-Comparison-Library) |
| [`topp-standard-operation-reflection`](https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-operation-reflection) | [`com.yelstream.topp.standard.operation.reflection`](https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-operation-reflection) | [`:Core:Operation:Topp-Standard-Operation-Reflection-Library`](https://github.com/sabroe/Topp-Standard/tree/main/module/Core/Operation/Topp-Standard-Operation-Reflection-Library) |
| [`topp-standard-operation-type`](https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-operation-type)             | [`com.yelstream.topp.standard.operation.type`](https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-operation-type)             | [`:Core:Operation:Topp-Standard-Operation-Type-Library`](https://github.com/sabroe/Topp-Standard/tree/main/module/Core/Operation/Topp-Standard-Operation-Type-Library)             |
| [`topp-standard-annotation-intention`](https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-annotation-intention) | [`com.yelstream.topp.standard.annotation.intention`](https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-annotation-intention) | [`:Annotation:Topp-Standard-Annotation-Intention-Library`](https://github.com/sabroe/Topp-Standard/tree/main/module/Annotation/Topp-Standard-Annotation-Intention-Library)         |
| [`topp-standard-stream-collection`](https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-stream-collection)       | [`com.yelstream.topp.standard.collection.stream`](https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-stream-collection)       | [`:Core:Collection:Topp-Standard-Stream-Collection-Library`](https://github.com/sabroe/Topp-Standard/tree/main/module/Core/Collection/Topp-Standard-Stream-Collection-Library)     |
| [`topp-standard-dual-access-io`](https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-dual-access-io)             | [`com.yelstream.topp.standard.io.dual`](https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-dual-access-io)                    | [`:Core:IO:Topp-Standard-Dual-Access-IO-Library`](https://github.com/sabroe/Topp-Standard/tree/main/module/Core/IO/Topp-Standard-Dual-Access-IO-Library)                           |
| [`topp-standard-system-holder`](https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-system-holder)               | [`com.yelstream.topp.standard.system.holder`](https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-system-holder)               | [`:Core:System:Topp-Standard-System-Holder-Library`](https://github.com/sabroe/Topp-Standard/tree/main/module/Core/System/Topp-Standard-System-Holder-Library)                     |
| [`topp-standard-time`](https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-time)                                 | [`com.yelstream.topp.standard.time`](https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-time)                                 | [`:Core:Time:Topp-Standard-Time-Library`](https://github.com/sabroe/Topp-Standard/tree/main/module/Core/Time/Topp-Standard-Time-Library)                                           |
| [`topp-standard-time-legacy`](https://central.sonatype.com/artifact/com.yelstream.topp.standard/topp-standard-time-legacy)                   | [`com.yelstream.topp.standard.time.legacy`](https://javadoc.io/doc/com.yelstream.topp.standard/topp-standard-time-legacy)                   | [`:Core:Time:Topp-Standard-Time-Legacy-Library`](https://github.com/sabroe/Topp-Standard/tree/main/module/Core/Time/Topp-Standard-Time-Legacy-Library)                             |



## Repository Overview

The featured modules represent only a small, curated subset of the project.
The repository currently contains ~55 modules across multiple stages of maturity, from experimental implementations to production-ready libraries.

Hence, the project serves three purposes:

- A continuously evolving collection of code, built over time through discovery, invention, and iterative refinement.
- A continuously published set of named artifacts for practical use via Maven Central.
- An exploration of an advanced Gradle multi-module architecture built on custom convention plugins and continuous module integration.


## Technical Constraints

- All artifacts are strict JPMS modules implemented in modern Java and validated to maintain acyclic internal dependencies.
- The architecture enforces strong modular boundaries and favors framework-agnostic implementations, while selectively integrating with ecosystems such as Spring Framework, Spring Boot, Quarkus, SmallRye, Eclipse MicroProfile, and Jakarta EE (including CDI), as well as selected external libraries such as SLF4J.
- All artifacts are compiled under strict Java compiler settings, enforcing zero-warning compilation as a build requirement, including `-Xlint:rawtypes`, `-Xlint:unchecked`, `-Xlint:deprecation`, and `-Werror`.
- The project is built using Gradle 9.5.0.
- Java SE 21 is the baseline for most modules, while selected modules target newer versions up to Java SE 26.


<br>

**-- Morten Sabroe Mortensen**
