package com.yelstream.topp.standard.lang;

import lombok.AllArgsConstructor;

public class CopyableTest {

    @AllArgsConstructor
    static class Data implements Copyable<Data> {
        private String value;

        @Override
        public Data copy() {
            return new Data(this.value);
        }
    }


}
