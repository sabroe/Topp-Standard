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

package com.yelstream.topp.standard.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class Texts {

    static String x= """
        SYM
        BUM
        .V/1LLHR
        .F/BA2906/14Feb/MAN
        .O/LH1122/13Feb/FRA/C
        .O/LH1123/13Feb/FRB/C
        .O/LH1124/13Feb/FRC/C
        .O/LH1125/13Feb/FRD/C
        .O/LH1126/13Feb/FRH/C
        .N/0088888005001
        .S/Y//C/////
        .H///
        .P/1XXXXXX/GIRISHKUMARRAVJIBHAIMR
        .E/VIP
        ENDBUM""";

    public static void basicTest(String[] args) {
        System.out.println("."+x+".");

        @SuppressWarnings("java:S5843")
        Pattern pattern = Pattern.compile(
                "^([.])(?<f0>[A-Z])" +
                        "(?:(/)(?<f1>[^/\\n]*))?" +
                        "(?:(/)(?<f2>[^/\\n]*))?" +
                        "(?:(/)(?<f3>[^/\\n]*))?" +
                        "(?:(/)(?<f4>[^/\\n]*))?" +
                        "(?:(/)(?<f5>[^/\\n]*))?" +
                        "(?:(/)(?<f6>[^/\\n]*))?" +
                        "(?:(/)(?<f7>[^/\\n]*))?" +
                        "(?:(/)(?<f8>[^/\\n]*))?" +
                        "(?:(/)(?<f9>[^/\\n]*))?" +
                        "(?:(/)(?<f10>[^/\\n]*))?" +
                        "$", Pattern.MULTILINE);
        Matcher matcher=pattern.matcher(x);

        String result = matcher.replaceAll(match -> {
            StringBuilder replacement = new StringBuilder();
            Map<String,Integer> namedGroups=match.namedGroups();
            Map<Integer,String> indexToName=namedGroups.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));

            String group=match.group();
System.out.println("Group: "+group);

            for (int i = 1; i <= match.groupCount(); i++) {
                String value = matcher.group(i);

                if (value!=null) {
                    String name=indexToName.get(i);

                    if (name!=null) {
                        if (name.equals("f2")) {
                            value="REPLACEMENT";
                        }
                    }

                    replacement.append(value);
                }
            }

            return replacement.toString();
        });

        System.out.println(result);
    }




    public static void main(String[] args) {
        System.out.println("."+x+".");

        @SuppressWarnings("java:S5843")
        Pattern pattern = Pattern.compile(
                "^([.])(?<f0>[A-Z])" +
                        "(?:(/)(?<f1>[^/\\n]*))?" +
                        "(?:(/)(?<f2>[^/\\n]*))?" +
                        "(?:(/)(?<f3>[^/\\n]*))?" +
                        "(?:(/)(?<f4>[^/\\n]*))?" +
                        "(?:(/)(?<f5>[^/\\n]*))?" +
                        "(?:(/)(?<f6>[^/\\n]*))?" +
                        "(?:(/)(?<f7>[^/\\n]*))?" +
                        "(?:(/)(?<f8>[^/\\n]*))?" +
                        "(?:(/)(?<f9>[^/\\n]*))?" +
                        "(?:(/)(?<f10>[^/\\n]*))?" +
                        "$", Pattern.MULTILINE);

//        String result=update(pattern,x,MatcherReplacer.replaceAll(),MatchReplacer.identity());

        String result=update(pattern,x,MatcherReplacer.replaceAll(),MatchReplacer.create(context->{
            String mappedValue=null;
            if ("O".equals(context.getMatch().group("f0"))) {
                if ("f3".equals(context.getGroupName())) {
                    mappedValue="REPLACE-IT!";
                }
            }
            return mappedValue;
        }));

        System.out.println(result);
    }

    @FunctionalInterface
    interface MatcherReplacer {
        Function<Function<MatchResult,String>,String> replace(Matcher matcher);

        static MatcherReplacer replaceFirst() {
            return matcher-> matcher::replaceFirst;
        }

        static MatcherReplacer replaceAll() {
            return matcher-> matcher::replaceAll;
        }
    }

    public static String update(Pattern pattern,
                                String text,
                                MatcherReplacer matcherReplacer,
                                MatchReplacer matchReplacer) {
        Matcher matcher=pattern.matcher(text);

        String result=matcherReplacer.replace(matcher).apply(matchReplacer::apply);
        return result;
    }

    @FunctionalInterface
    interface MatchReplacer {
        String apply(MatchResult match);

        static MatchReplacer identity() {
            return MatchResult::group;
        }

        @lombok.Builder(builderClassName="Builder")
        @AllArgsConstructor
        @Getter
        public static class Context {
            private final MatchResult match;
            private final int groupIndex;
            private final String groupName;
        }

        static MatchReplacer create(Function<Context,String> mapper) {
            return match -> {
                StringBuilder replacement = new StringBuilder();
                Map<String,Integer> namedGroups=match.namedGroups();
                Map<Integer,String> indexToName=namedGroups.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));

                String group=match.group();
                System.out.println("Group: "+group);

                for (int i = 1; i <= match.groupCount(); i++) {
                    String value = match.group(i);

                    if (value!=null) {
                        String name=indexToName.get(i);

                        if (mapper!=null) {
                            Context context = Context.builder().match(match).groupIndex(i).groupName(name).build();
                            String mappedValue=mapper.apply(context);
                            if (mappedValue!=null) {
                                value=mappedValue;
                            }
                        }

                        replacement.append(value);
                    }
                }

                return replacement.toString();
            };
        }
    }
}
