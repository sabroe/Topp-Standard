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

package com.yelstream.topp.standard.net.resource.identification;

import com.yelstream.topp.standard.net.resource.identification.scheme.Scheme;
import com.yelstream.topp.standard.net.resource.identification.scheme.StandardScheme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.UtilityClass;

import java.net.URI;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities for JAR-specific URIs.
 * <p>
 *     JAR URIs are that of a JAR URL: {@code jar:<url>!/{entry}}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-29
 */
@UtilityClass
public class JarURIs {
    /**
     * Separator within JAR URL of the form {@code jar:<url>!/{entry}}.
     */
    public static final String SEPARATOR="!/";

    /**
     * String format for JAR URL.
     */
    public static final String JAR_URL_FORMAT="%1$s:%2$s"+SEPARATOR+"%3$s";

    /**
     * Regular expression for JAR URL.
     */
    public static final String JAR_URL_REGEX=
        "^"+
        "(?<scheme>[a-zA-Z][a-zA-Z0-9+.-]*):"+
        "(?<url>.+)"+
        Pattern.quote(SEPARATOR)+
        "(?<entry>[^!]+)?"+
        "$";

    /**
     *
     */
    @Getter
    @ToString
    @AllArgsConstructor(staticName="of")
    public static final class SplitURI {
        private final String scheme;
        private final String url;
        private final String entry;

        public static SplitURI of(URI uri) {
            StandardScheme.JAR.getScheme().requireMatch(uri);
            Pattern pattern=Pattern.compile(JAR_URL_REGEX);
            Matcher matcher=pattern.matcher(uri.toString());
            if (!matcher.matches()) {
                throw new IllegalStateException();
            }
            String scheme=matcher.group("scheme");
            String url=matcher.group("url");
            String entry=matcher.group("entry");

            return of(scheme,url,entry);
        }
    }

    public String create(String scheme, String url, String entry) {
        Objects.requireNonNull(scheme,"Failure to create URI; scheme is not set!");
        Objects.requireNonNull(url,"Failure to create URI; inner URL is not set!");
        return String.format(JAR_URL_FORMAT,scheme,url,entry==null?"":entry);
    }

    @lombok.Builder(builderClassName="Builder")
    private String createByBuilder(String scheme, String url, String entry) {
        return create(scheme,url,entry);
    }

    @SuppressWarnings({"unused","FieldMayBeFinal"})
    public static class Builder {
        private String scheme=StandardScheme.JAR.getScheme().getName();

        public Builder scheme(String scheme) {
            this.scheme=scheme;
            return this;
        }

        public Builder scheme(Scheme scheme) {
            return scheme(scheme.getName());
        }

        public Builder url(String url) {
            this.url=url;
            return this;
        }

        public Builder url(URI url) {
            return url(url.toString());
        }

        public Builder entry(String entry) {
            this.entry=entry;
            return this;
        }

        public Builder entry(URI entry) {
            StandardURIPredicate.IsPathOnly.requireMatch(entry);
            return entry(entry.getPath());
        }

        public URI buildURL() {
            return URI.create(build());
        }

        public SplitURI buildSplitURL() {
            return SplitURI.of(buildURL());
        }

        public static Builder of(SplitURI splitURI) {
            return builder().scheme(splitURI.getScheme()).url(splitURI.url).entry(splitURI.getEntry());
        }

        public static Builder of(URI uri) {
            StandardScheme.JAR.getScheme().requireMatch(uri);
            Pattern pattern=Pattern.compile(JAR_URL_REGEX);
            Matcher matcher=pattern.matcher(uri.toString());
            if (!matcher.matches()) {
                throw new IllegalStateException();
            }
            String scheme=matcher.group("scheme");
            String url=matcher.group("url");
            String entry=matcher.group("entry");

            return builder().scheme(scheme).url(url).entry(entry);
        }
    }
}
