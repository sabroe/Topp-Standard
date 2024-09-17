package com.yelstream.topp.standard.log.resist.slf4j;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Journal {
    private final Log log;

    public Log end() {
        return log;
    }
}
