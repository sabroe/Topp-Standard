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

package com.yelstream.topp.standard.lang;

import lombok.AllArgsConstructor;

import java.util.function.Consumer;

public class FluentCopyableTest {

    @AllArgsConstructor
    static class Data implements Copyable<Data> {
        private String value;

        @Override
        public Data copy() {
            return new Data(this.value);
        }
    }

    static class AdvancedData implements FluentCopyable<AdvancedData, Data> {
        private Data data;

        public AdvancedData(Data data) {
            this.data = data;
        }

        @Override
        public AdvancedData copy(Consumer<Data> consumer) {
            Data copiedData = data.copy();
            consumer.accept(copiedData);
            return this;
        }

        @Override
        public Data copy() {
            return data.copy();
        }
    }



}
