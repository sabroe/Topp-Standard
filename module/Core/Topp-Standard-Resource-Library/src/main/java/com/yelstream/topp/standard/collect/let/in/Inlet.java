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

package com.yelstream.topp.standard.collect.let.in;

import java.util.stream.Stream;

/**
 * Dual-access interface for accessing collection content by writing.
 * <p>
 *     Access can be done in one of two ways:
 * </p>
 * <ol>
 *     <li>
 *         Supplying a collection directly.
 *         <br/>
 *         This is offline and eager.
 *     </li>
 *     <li>
 *         Supplying data through a stream.
 *         <br/>
 *         This is online and lazy.
 *     </li>
 * </ol>
 * <p>
 *     Conventional interface:
 * </p>
 * <pre>
 *     interface ResourceProvider {
 *         void setResourcesAsStream(Stream&lt;X&gt; stream);
 *         void setResourcesAsList(List&lt;X&gt; list);
 *     }
 * </pre>
 * <p>
 *     Replacement interface:
 * </p>
 * <pre>
 *     interface ResourceProvider {
 *         void resources(Consumer&lt;ListInlet&lt;X&gt;&gt; resources);
 *     }
 * </pre>
 * <p>
 *     Usages of this replacement interface:
 * </p>
 * <pre>
 *     ResourceProvider provider=...
 *     ...
 *     List&lt;Resource&gt; resourceList=...
 *     provider.resources(in->in.set(resources));
 *     ...
 *     try (Stream&lt;Resource&gt; resourceStream=...) {
 *         provider.resources(in->in.stream(stream));
 *     }
 * </pre>
 * <p>
 *     Only one of {@link #set(Object)} and {@link #stream(Stream)} are to be called.
 * </p>
 * @param <T> Type of element accessed.
 * @param <R> Type of collection of elements for direct offline access, usually a collection of elements.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-04
 */
public interface Inlet<T,R> {
    /**
     * Sets elements as an online, lazy stream.
     * <p>
     *     In this context, take care to close the stream, which are auto-closable,
     *     and may have an action tied to their close operation:
     * </p>
     * <pre>
     *     try (Stream&lt;Resource&gt; stream=...) {
     *         provider.stream(stream);
     *     }
     * </pre>
     * @param stream Stream of elements.
     *               <br/>
     *               This stream may or may not be live.
     */
    void stream(Stream<T> stream);

    /**
     * Sets elements as an offline, eager collection.
     * @param collection Collection of elements.
     */
    void set(R collection);
}
