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
 *     as properly implemented <i>double-checked locking</i> (DCL) —
 *     it is thread-safe, lazy, and low synchronization overhead after first access.
 * </p>
 * <p>
 *     Example usage:
 * </p>
 * <pre>{@code
 *     Container<MyService> serviceContainer = Containers.lazy(MyService::new);
 *     //...
 *     MyService service = serviceContainer.get();
 * }</pre>
 * <p>
 *     In addition to the simple holder contract
 *     {@link com.yelstream.topp.standard.system.holder.Container},
 *     this also has a variation
 *     {@link com.yelstream.topp.standard.system.holder.ResettableContainer}
 *     which may be reset.
 *     The need to reset a held instance does show up in certain usages.
 * </p>
 * <p>
 *     In plain Java,
 *     this is an alternative way to do on-demand initialization:
 * </p>
 * <pre>{@code
 *     public class MyService {
 *         private static class Holder {
 *             private static final MyService INSTANCE = new MyService();
 *         }
 *
 *         public static MyService getInstance() {
 *             return Holder.INSTANCE;
 *         }
 *
 *         private MyService() {
 *             //...
 *         }
 *
 *        //...
 *    }
 * }</pre>
 * <p>
 *     This is lazy because the inner class is not loaded until {@code #getInstance()} is called the first time.
 *     Thread-safety is guaranteed by JVM class initialization semantics.
 *     No {@code volatile}, no DCL boilerplate.
 *     With just a {@code static final} field read),
 *     this is very fast after initialization.
 * </p>
 * <p>
 *     To be independent of frameworks,
 *     this is the recommended pattern.
 * </p>
 * <p>
 *     In Spring,
 *     there is excellent built-in support for lazy initialization.
 *     Support comes by using {@code @Lazy} in combination with a {@code @Bean}, {@code @Component}, {@code @Service}, etc.
 * </p>
 * <p>
 *     The same effect can be applied globally by setting
 *     {@code spring.main.lazy-initialization = true} in {@code application.properties}.
 *     This setting makes almost everything lazy by default — great for startup time.
 * </p>
 * <p>
 *     In Quarkus,
 *     CDI (based on ArC) is lazy by default for normal scopes, e.g., {@code @ApplicationScoped} and {@code @RequestScoped}.
 *     A {@code @ApplicationScoped} bean is created only when needed the first time — a method is invoked on the contextual reference  —,
 *     while {@code @Singleton} and {@code @Dependent} are created eagerly on injection hence these are not lazy.
 * </p>
 * <p>
 *     If needed, eager initialization can be forced with {@code @io.quarkus.runtime.Startup},
 *     but the default is already on-demand.
 * </p>
 * <p>
 *     Overall, lazy initialization happens without extra annotations.
 * </p>
 * <p>
 *     In Lombok, this example shows a convenient, nice solution:
 * </p>
 * <pre>{@code
 *     @Getter(lazy = true)
 *     private final MyService service = creteMyService();
 * }</pre>
 * <p>
 *     The decoration leads to the generation of a modern implementation of the initialization-on-demand holder pattern.
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
