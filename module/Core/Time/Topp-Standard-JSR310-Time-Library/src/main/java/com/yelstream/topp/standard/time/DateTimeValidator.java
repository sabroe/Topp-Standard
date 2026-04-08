package com.yelstream.topp.standard.time;

import java.util.regex.Pattern;

public final class DateTimeValidator {

    private final Pattern pattern;

    public DateTimeValidator(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public boolean isValid(String text) {
        return text != null && pattern.matcher(text).matches();
    }
}
