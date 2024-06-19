package com.yelstream.topp.standard.util.regex;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Slf4j
@UtilityClass
public class Patterns {


    @SuppressWarnings("SameParameterValue")
    private static Pattern createPattern(String regEx,
                                         String defaultRegEx) {
        Pattern pattern;
        try {
            pattern=Pattern.compile(regEx);
        } catch (PatternSyntaxException ex) {
            log.error("Failure to recognize reg-ex to filter by container-name; reg-ex configured is {}.",regEx);
            pattern=Pattern.compile(defaultRegEx);
        }
        return pattern;
    }

}
