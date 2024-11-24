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

        @lombok.Builder.Default
        private final URI uri=DEFAULT_URI;

        private final String projectKey;

        private final String branchName;

        private final String pullRequestNumber;

        public static Builder fromURI(URI uri) throws URISyntaxException {
            return builder().uri(uri);
        }

        public static class Builder {
            private URI uri=DEFAULT_URI;

            public Builder overrideURI(URI overrideUri) throws URISyntaxException {
                return uri(URIs.builder().uri(uri).overrideURI(overrideUri).build());
            }

/*
            public Builder overrideBaseURI(URI overrideBaseUri) throws URISyntaxException {
                return uri(URIs.builder().uri(uri).overrideBaseURI(overrideBaseUri).build());
            }
*/
        }

        public URI getURI() throws URISyntaxException {
            MappedQuery query=new MappedQuery();
            if (projectKey!=null) {
                query.add("id",projectKey);
            }
            if (branchName!=null) {
                query.add("branch",branchName);
            }
            if (pullRequestNumber!=null) {
                query.add("pullRequest",pullRequestNumber);
            }
            URIs.Builder builder=URIs.builder().uri(uri).mappedQuery(query);
            return builder.build();
        }
    }
}
