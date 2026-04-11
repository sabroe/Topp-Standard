package com.yelstream.topp.standard.time.format;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Function;

public final class TemporalFormat<T> {

    private final DateTimeValidator validator;
    private final DateTimeFormatter formatter;
    private final Function<String, T> parser;

    public TemporalFormat(DateTimeValidator validator,
                          DateTimeFormatter formatter,
                          Function<String, T> parser) {
        this.validator = validator;
        this.formatter = formatter;
        this.parser = parser;
    }

    public Optional<T> tryParse(String text) {
        if (!validator.isValid(text)) {
            return Optional.empty();
        }
        try {
            return Optional.of(parser.apply(text));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
