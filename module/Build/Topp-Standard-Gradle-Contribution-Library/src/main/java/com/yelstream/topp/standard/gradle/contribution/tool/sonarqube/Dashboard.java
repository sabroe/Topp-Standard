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
import lombok.Builder;
import lombok.experimental.UtilityClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
        @lombok.Builder.Default
        private final URI uri=null;

        @lombok.Builder.Default
        private final String scheme="https";

        @lombok.Builder.Default
        private final String host="sonarqube.com";

        @lombok.Builder.Default
        private final int port=-1;

        @lombok.Builder.Default
        private final String path="/dashboard";

        private final String projectKey;

        private final String branchName;

        private final String pullRequestNumber;

        private URI getURI() throws URISyntaxException {
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
            return URIs.builder().scheme(scheme).host(host).port(port).path(path).mappedQuery(query).build();
        }
    }

    public static void main(String[] args) throws Exception {
        {
            Project project=Project.builder().projectKey("XXX-yyy-zzz").branchName("special").build();
            URI uri=project.getURI();
            System.out.println("Dashboard project URI: "+project.getURI());
        }

        URI uri2=URIs.Builder.fromURI(uri).host("sonarqube-enterprise.beumer.com").build();
        System.out.println("Dashboard project URI: "+uri2);

        URI base=URI.create("http://sonarqube-enterprise.beumer.com:500");
        URI uri3=URIs.Builder.fromURI(uri).host("sonarqube-enterprise.beumer.com").build();
        System.out.println("Dashboard project URI: "+uri3);
    }
}
