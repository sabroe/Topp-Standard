/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.gradle.contribution.tool.sonarqube;

import com.yelstream.topp.standard.lang.Strings;
import com.yelstream.topp.standard.net.MappedQuery;
import com.yelstream.topp.standard.net.URIs;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utilities addressing the access to Sonarqube dashboards.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-11-23
 */
@UtilityClass
public class Dashboard {
    /**
     *
     */
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor
    public static class Project {
        /**
         *
         */
        public static final URI DEFAULT_URI=URI.create("https://localhost:9000/dashboard");

        private final URI baseURI;

        private final String projectKey;

        private final String branchName;

        private final String pullRequestNumber;

        public static Builder fromURI(URI uri) {
            return builder().baseURI(uri);
        }

        @SuppressWarnings("FieldMayBeFinal")
        public static class Builder {
            private URI baseURI=DEFAULT_URI;

            public Builder resolve(URI uri) {
                baseURI=baseURI.resolve(uri);
                return this;
            }

            public Builder resolveFromConfiguration(URI uri) throws URISyntaxException {
                String path=uri.getPath();
                if (Strings.isEmpty(path)) {
                    uri=URIs.builder().uri(uri).path(baseURI.getPath()).build();
                } else {
                    if (path.equals("/")) {
                        uri=URIs.builder().uri(uri).path("").build();
                    }
                }
                baseURI=baseURI.resolve(uri);
                String query=uri.getQuery();
                if (!Strings.isEmpty(query)) {
                    baseURI=URIs.Builder.fromURI(baseURI).query(query).build(); //TO-DO: Add to existing query, if any, not replace all query params!
                }
                return this;
            }
        }

        public URI getURI() throws URISyntaxException {
            MappedQuery mappedQuery=MappedQuery.of(baseURI.getQuery());
            if (projectKey!=null) {
                mappedQuery.add("id",projectKey);
            }
            if (branchName!=null) {
                mappedQuery.add("branch",branchName);
            }
            if (pullRequestNumber!=null) {
                mappedQuery.add("pullRequest",pullRequestNumber);
            }
            URIs.Builder builder=URIs.builder().uri(baseURI).mappedQuery(mappedQuery);
            return builder.build();
        }
    }
}
