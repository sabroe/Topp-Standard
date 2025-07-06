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

package com.yelstream.topp.standard.resource.util.let.in;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-04
 */
@lombok.Builder(builderClassName="Builder")
@AllArgsConstructor(staticName="of")
final class DefaultListInlet<X> implements ListInlet<X> {
    /**
     * Stream-consumer.
     */
    private final Consumer<Stream<X>> streamConsumer;

    /**
     * List-consumer.
     */
    private final Consumer<List<X>> listConsumer;

    @Override
    public void stream(Stream<X> stream) {
        streamConsumer.accept(stream);
    }

    @Override
    public void set(List<X> list) {
        listConsumer.accept(list);
    }
}
