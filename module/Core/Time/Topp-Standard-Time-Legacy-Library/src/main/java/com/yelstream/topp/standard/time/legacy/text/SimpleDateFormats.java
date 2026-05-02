/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024-2026 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.time.legacy.text;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility addressing instances of {@link SimpleDateFormat}.
 * <p>
 *     Note that {@link SimpleDateFormat} is mutable!
 * </p>
 * <p>
 *     This does pattern-based conversion,
 *     possibly pattern extraction from an existing {@link SimpleDateFormat}.
 * </p>
 * <p>
 *     Offered if optional locale handling,
 *     optional "strict vs. lenient" handling.
 * </p>
 * <p>
 *     Note that it is not possible to reliably convert an arbitrary {@link SimpleDateFormat} instance
 *     into a {@link DateTimeFormatter} which is the more modern equivalent.
 * </p>
 * <p>
 *     Key characteristics of {@link SimpleDateFormat}:
 * </p>
 * <ul>
 *     <li>Is mutable</li>
 *     <li>Has lenient parsing</li>
 *     <li>Supports some legacy quirks</li>
 * </ul>
 * <p>
 *     Key characteristics of {@link DateTimeFormatter}:
 * </p>
 * <ul>
 *     <li>Is immutable</li>
 *     <li>Is strict by default</li>
 *     <li>Has slightly different pattern semantics</li>
 * </ul>
 /**
 * <h2>Limitations</h2>
 * <ul>
 *     <li>Only the pattern is converted; other configuration such as leniency and time zone must be handled separately.</li>
 *     <li>Pattern compatibility is not guaranteed for all cases.</li>
 *     <li>{@link DateTimeFormatter} is stricter than {@link SimpleDateFormat}.</li>
 * </ul>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-04-11
 */
@UtilityClass
public class SimpleDateFormats {

    public static DateTimeFormatter toFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    public static DateTimeFormatter toFormatter(String pattern,
                                                Locale locale) {
        return DateTimeFormatter.ofPattern(pattern, locale);
    }

/*
    public static DateTimeFormatter toFormatter(SimpleDateFormat format) {
        Objects.requireNonNull(format, "format");
        return DateTimeFormatter.ofPattern(format.toPattern(),format.getDateFormatSymbols().getLocale());
    }
*/

/*
    public static DateTimeFormatter toFormatter(SimpleDateFormat format) {
        Objects.requireNonNull(format, "format");
        return DateTimeFormatter.ofPattern(format.toPattern(), format.getLocale());
    }
*/

    public static DateTimeFormatter toFormatter(SimpleDateFormat format) {
        Objects.requireNonNull(format, "format");
        return DateTimeFormatter.ofPattern(format.toPattern());
    }

    public static DateTimeFormatter toFormatter(SimpleDateFormat format,
                                                ResolverStyle resolverStyle) {
        Objects.requireNonNull(format, "format");
        Objects.requireNonNull(resolverStyle, "resolverStyle");

        return DateTimeFormatter
                .ofPattern(format.toPattern())
                .withResolverStyle(resolverStyle);
    }

    public static Optional<DateTimeFormatter> tryToFormatter(SimpleDateFormat format) {
        try {
            return Optional.of(toFormatter(format));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static String pattern(SimpleDateFormat format) {
        Objects.requireNonNull(format, "format");
        return format.toPattern();
    }

    public record PatternIssue(
            String symbol,
            String message,
            Severity severity
    ) {
        public enum Severity {
            INFO,  //Minor difference
            WARNING,  //Likely bug
            ERROR  //Must fix before migration
        }
    }

    public static List<PatternIssue> analyze0(String pattern) {
        List<PatternIssue> issues = new ArrayList<>();

        if (pattern.contains("YYYY")) {
            issues.add(new PatternIssue(
                    "YYYY",
                    "Week-based year differs from calendar year (yyyy).",
                    PatternIssue.Severity.WARNING
            ));
        }

        if (pattern.contains("DD")) {
            issues.add(new PatternIssue(
                    "DD",
                    "Day-of-year used instead of day-of-month (dd).",
                    PatternIssue.Severity.WARNING
            ));
        }

        if (pattern.contains("Z")) {
            issues.add(new PatternIssue(
                    "Z",
                    "Time zone format differs between APIs.",
                    PatternIssue.Severity.INFO
            ));
        }

        return issues;
    }

    public record MigrationSuggestion(
            String originalPattern,
            String suggestedPattern,
            List<PatternIssue> issues
    ) {}

    public static MigrationSuggestion suggest(SimpleDateFormat format) {
        String pattern = format.toPattern();
        List<PatternIssue> issues = analyze(pattern);

        return new MigrationSuggestion(
                pattern,
                pattern, // optionally adjusted
                issues
        );
    }

    public static String normalize(String pattern) {
        return pattern
                .replace("YYYY", "yyyy")
                .replace("DD", "dd");
    }

    @AllArgsConstructor
    public enum PatternRule {

        WEEK_YEAR("Y", PatternIssue.Severity.WARNING, "Week-based year differs from calendar year (yyyy)."),
        DAY_OF_YEAR("D", PatternIssue.Severity.WARNING, "Day-of-year used instead of day-of-month (dd)."),
        TIMEZONE_Z("Z", PatternIssue.Severity.WARNING, "Zone format differs; consider using XXX."),
        TIMEZONE_NAME("z", PatternIssue.Severity.WARNING, "Zone name parsing is locale-dependent."),
        FRACTION("S", PatternIssue.Severity.INFO, "Fraction precision differs (millis vs nanos).");

        final String symbol;
        final PatternIssue.Severity severity;
        final String message;
    }

    public static List<PatternIssue> analyze(String pattern) {
        List<PatternIssue> issues = new ArrayList<>();

        for (PatternRule rule : PatternRule.values()) {
            if (pattern.contains(rule.symbol)) {
                issues.add(new PatternIssue(
                        rule.symbol,
                        rule.message,
                        rule.severity
                ));
            }
        }

        return issues;
    }

    public static String normalize1(String pattern) {
        return pattern
                .replace("YYYY", "yyyy")
                .replace("DD", "dd");
    }


    public record PatternToken(
            String symbol,   // e.g. "y", "M", "Z"
            int length,      // e.g. 4 for "yyyy"
            boolean literal, // true if inside quotes
            String text      // original text
    ) {}

}
