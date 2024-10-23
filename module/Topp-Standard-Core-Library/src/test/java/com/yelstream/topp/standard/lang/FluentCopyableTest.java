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
