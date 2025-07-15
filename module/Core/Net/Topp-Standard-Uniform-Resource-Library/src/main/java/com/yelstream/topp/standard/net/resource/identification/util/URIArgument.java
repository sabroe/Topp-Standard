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

package com.yelstream.topp.standard.net.resource.identification.util;

import com.yelstream.topp.standard.net.resource.identification.builder.util.MappedQuery;
import com.yelstream.topp.standard.net.resource.identification.builder.util.TaggedPath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.net.URI;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Unified set of all possible URI constructor arguments.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-15
 */
@Getter
@ToString
@AllArgsConstructor(staticName="of")
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class URIArgument {
    /**
     * Full, textual URI.
     */
    private final String parsable;

    /**
     * Scheme.
     */
    private final String scheme;

    /**
     * Scheme specific part.
     */
    private final String schemeSpecificPart;

    /**
     * Authority.
     */
    private final String authority;

    /**
     * User info.
     */
    private final String userInfo;

    /**
     * Host.
     */
    private final String host;

    /**
     * Port.
     */
    @lombok.Builder.Default
    private final int port=-1;

    /**
     * Path.
     */
    private final String path;

    /**
     * Query.
     */
    private final String query;

    /**
     * Fragment.
     */
    private final String fragment;


    public TaggedPath taggedPath() {
        return TaggedPath.ofFullPath(path);
    }

    public MappedQuery mappedQuery() {
        return MappedQuery.of(query);
    }


    @SuppressWarnings("java:S1118")
    public static class Builder {
        public Builder uri(URI uri) {
//TODO: ??            parsable(uri.toString());
            scheme(uri.getScheme());
            schemeSpecificPart(uri.getSchemeSpecificPart());
            authority(uri.getAuthority());
            userInfo(uri.getUserInfo());
            host(uri.getHost());
            port(uri.getPort());
            path(uri.getPath());
            query(uri.getQuery());
            fragment(uri.getFragment());
            return this;
        }



        public TaggedPath taggedPath() {
            return TaggedPath.ofFullPath(path);
        }

        public Builder taggedPath(TaggedPath taggedPath) {
            path(taggedPath.toFullPath());
            return this;
        }

        public String tag() {
            return taggedPath().tag();
        }

        public Builder tag(String tag) {
            taggedPath(taggedPath().tag(tag));
            return this;
        }

        public String untaggedPath() {
            return TaggedPath.ofFullPath(path).path();
        }

        public Builder untaggedPath(String path) {
            taggedPath(taggedPath().path(path));
            return this;
        }



        public Builder taggedPathUpdate(Consumer<TaggedPath> consumer) {
            TaggedPath taggedPath=taggedPath();
            consumer.accept(taggedPath);
            return taggedPath(taggedPath);
        }




        public MappedQuery mappedQuery() {
            return MappedQuery.of(query);
        }

        public Builder mappedQuery(MappedQuery mappedQuery) {
            query(mappedQuery.formatAsString());
            return this;
        }


        public Builder mappedQueryUpdate(Consumer<MappedQuery> consumer) {
            MappedQuery mappedQuery=mappedQuery();
            consumer.accept(mappedQuery);
            return mappedQuery(mappedQuery);
        }


    }

    public static URIArgument of(URI uri) {
        return builder().uri(uri).build();
    }
}
