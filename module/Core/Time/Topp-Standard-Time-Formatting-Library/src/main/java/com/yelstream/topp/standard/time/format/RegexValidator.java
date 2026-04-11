package com.yelstream.topp.standard.time.format;

import java.util.regex.Pattern;

public final class RegexValidator implements TextValidator {
    private final Pattern pattern;

    public RegexValidator(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public boolean isValid(String text) {
        return text != null && pattern.matcher(text).matches();
    }
}
