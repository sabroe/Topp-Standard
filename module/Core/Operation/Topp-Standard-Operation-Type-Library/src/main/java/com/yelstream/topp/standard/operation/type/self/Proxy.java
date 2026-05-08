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

package com.yelstream.topp.standard.operation.type.self;

import lombok.RequiredArgsConstructor;

/**
 * Static proxy for a fluent type hence dependent upon itself as the self-referential type.
 * <p>
 *     This is experimental at best.
 *     </br>
 *     A proxy may not need to involve a delegate of a fluent type.
 *     <br/>
 *     This pattern does not help using Lombok {@code lombok.Delegate};
 *     this separates any proxy from its final implementation,
 *     and does not fly well with proxies for types having generics.
 *     <br/>
 *     Good super-proxy implementations are difficult in Java.
 * </p>
  *
 * @param <S> Proxy/self type.
 * @param <D> Delegate type.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-05-09
 */
@RequiredArgsConstructor
public abstract class Proxy<S extends Proxy<S,D>, D> implements Self<S> {

    protected abstract D delegate();
}
