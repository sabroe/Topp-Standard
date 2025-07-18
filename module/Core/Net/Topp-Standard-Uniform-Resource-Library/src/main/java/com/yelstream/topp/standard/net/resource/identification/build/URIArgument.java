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

package com.yelstream.topp.standard.net.resource.identification.build;

import com.yelstream.topp.standard.net.resource.identification.path.SegmentedPath;
import com.yelstream.topp.standard.net.resource.identification.path.TaggedPath;
import com.yelstream.topp.standard.net.resource.identification.query.MappedQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.net.URI;

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

    /**
     * Gets the path as a tagged path.
     * @return Tagged path.
     */
    public TaggedPath taggedPath() {
        return TaggedPath.ofPath(path);
    }

    /**
     * Gets the query as a mapped query.
     * @return Mapped query.
     */
    public MappedQuery mappedQuery() {
        return MappedQuery.of(query);
    }

    /**
     * Gets the path as a segmented path.
     * @return Segmented path.
     */
    public SegmentedPath segmentedPath() {
        return SegmentedPath.ofPath(path);
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

        /**
         * Sets the current path from a tagged path.
         * @param taggedPath Tagged path.
         * @return This builder.
         */
        public Builder taggedPath(TaggedPath taggedPath) {
            path(taggedPath.toPath());
            return this;
        }

        /**
         * Sets the current query from a mapped query.
         * @param mappedQuery Mapped query.
         * @return This builder.
         */
        public Builder mappedQuery(MappedQuery mappedQuery) {
            query(mappedQuery.toQuery());
            return this;
        }

        /**
         * Sets the current path from a segmented path.
         * @param segmentedPath Segmented path.
         * @return This builder.
         */
        public Builder segmentedPath(SegmentedPath segmentedPath) {
            path(segmentedPath.toPath());
            return this;
        }
    }

    public static URIArgument of(URI uri) {
        return builder().uri(uri).build();
    }
}
