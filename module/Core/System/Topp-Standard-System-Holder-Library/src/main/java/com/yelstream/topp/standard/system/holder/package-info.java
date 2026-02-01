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

/**
 * Utilities for creating and containing abstract instances.
 * <p>
 *     This provides clean and safe ways to achieve on-demand initialization with the same effective guarantees
 *     as properly implemented double-checked locking (DCL) — i.e. thread-safe, lazy,
 *     and low synchronization overhead after first access.
 * </p>
 * <p>
 *     Example usage:
 * </p>
 * <pre>{@code
 *     Container<ExpensiveService> serviceContainer = Containers.lazy(ExpensiveService::new);
 *     //...
 *     ExpensiveService service = serviceContainer.get();
 * }</pre>
 * <p>
 *     In addition to a simple holder
 *     {@link com.yelstream.topp.standard.system.holder.Container},
 *     this also has a variation which may be reset
 *     {@link com.yelstream.topp.standard.system.holder.ResettableContainer}.
 * </p>
 * <p>
 *     In plain Java,
 *     an alternative, simple way to do on-demand initialization and if the usage is in control of the class in question:
 * </p>
 * <pre>{@code
 *     public class ExpensiveService {
 *         private static class Holder {
 *             private static final ExpensiveService INSTANCE = new ExpensiveService();
 *         }
 *
 *         public static ExpensiveService getInstance() {
 *             return Holder.INSTANCE;
 *         }
 *
 *         private ExpensiveService() {
 *             //...
 *         }
 *
 *        //...
 *    }
 * }</pre>
 * <p>
 *     This is lazy since the inner class is not loaded until getInstance() is first called.
 *     <br/>
 *     Thread-safety is guaranteed by JVM class initialization semantics (no explicit synchronization needed).
 *     <br/>
 *     No {@code volatile}, no DCL boilerplate.
 *     <br/>
 *     Very fast after initialization (just a static final field read).
 *     <br/>
 *     This is usually the recommended pattern when you control the class and want per-instance or global lazy fields without frameworks.
 * </p>
 * <p>
 *     In the Spring Framework,
 *     there is excellent built-in support for lazy initialization.
 *     It comes by using {@code @Lazy} in combination with a {@code @Bean}, {@code @Component}, {@code @Service}, etc.
 *     <br/>
 *     It can be applied globally by setting
 *     {@code spring.main.lazy-initialization=true} in {@code application.properties}
 *     (makes almost everything lazy by default — great for startup time).
 * </p>
 * <p>
 *     In Quarkus,
 *     CDI (based on ArC) is lazy by default for normal scopes ({@code @ApplicationScoped}, {@code @RequestScoped}, etc.).
 *     <br/>
 *     A {@code @ApplicationScoped} bean is created only when first needed (method invoked on the contextual reference),
 *     while {@code @Singleton} and {@code @Dependent} are created eagerly on injection (not lazy).
 *     <br/>
 *     Eager initialization can be forced with {@code @io.quarkus.runtime.Startup} if needed,
 *     but the default is already on-demand.
 *     <br/>
 *     Lazy initialization is "for free" and without extra annotations —
 *     and it's thread-safe because the container handles it.
 * </p>
 * <p>
 *     In Lombok,
 *     this is correct and may be convenient:
 * </p>
 * <pre>{@code
 *     @Getter(lazy = true)
 *     private final Heavy heavy = computeHeavy();
 * }</pre>
 * <p>
 *     Lombok generates something very close to the initialization-on-demand holder pattern
 *     (or volatile + DCL in some cases), thread-safe, and clean.
 *     <br/>
 *     This is probably the nicest one-liner solution if already using Lombok.
 * </p>
 * <p>
 *     See {@link com.yelstream.topp.standard.system.holder.Container},
 *     {@link com.yelstream.topp.standard.system.holder.ResettableContainer},
 *     {@link com.yelstream.topp.standard.system.holder.Containers}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-09
 */
package com.yelstream.topp.standard.system.holder;
