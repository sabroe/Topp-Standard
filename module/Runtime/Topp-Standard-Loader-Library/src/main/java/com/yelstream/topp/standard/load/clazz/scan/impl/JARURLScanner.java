package com.yelstream.topp.standard.load.clazz.scan.impl;

import com.yelstream.topp.standard.load.clazz.scan.URLScanner;

import java.io.IOException;
import java.util.stream.Stream;

public class JARURLScanner implements URLScanner {
    @Override
    public Stream<String> scan(Offset offset, Filter filter) throws IOException {
        return Stream.empty();
    }
/*
    @Override
    public void close() throws Exception {

    }
*/
}
