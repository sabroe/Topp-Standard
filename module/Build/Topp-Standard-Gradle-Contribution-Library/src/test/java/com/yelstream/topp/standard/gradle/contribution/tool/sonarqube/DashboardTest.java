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

package com.yelstream.topp.standard.gradle.contribution.tool.sonarqube;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Tests {@link Dashboard}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-11-24
 */
class DashboardTest {
    /**
     * Tests the creation of URL with project-key and branch-name.
     */
    @Test
    void projectKeyAndBranchName() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        builder=builder.projectKey("x-y-z").branchName("special");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("https://localhost:9000/dashboard?id=x-y-z&branch=special"),projectUri);
    }

    /**
     * Tests the creation of URL with project-key and branch-name with different host and existing path.
     */
    @Test
    void projectKeyWithOtherHostAndExistingPath() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI configURI=URI.create("//sonarqube.com");
        builder=builder.resolveFromConfiguration(configURI);

        builder=builder.projectKey("x-y-z");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("https://sonarqube.com/dashboard?id=x-y-z"),projectUri);
    }

    /**
     * Tests the creation of URL with project-key and branch-name with different host and existing path and additional custom query-parameter.
     */
    @Test
    void projectKeyWithOtherHostAndExistingPathAndExistingParam() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI configURI=URI.create("//sonarqube.com?custom=yes");
        builder=builder.resolveFromConfiguration(configURI);

        builder=builder.projectKey("x-y-z");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("https://sonarqube.com/dashboard?custom=yes&id=x-y-z"),projectUri);
    }

    /**
     * Tests the creation of URL with project-key and branch-name with different host and rest path.
     */
    @Test
    void projectKeyWithOtherHostAndResetPath() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI configURI=URI.create("//sonarqube.com/");
        builder=builder.resolveFromConfiguration(configURI);

        builder=builder.projectKey("x-y-z");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("https://sonarqube.com?id=x-y-z"),projectUri);
    }

    /**
     * Tests the creation of URL with project-key and branch-name with different host and port and existing path.
     */
    @Test
    void projectKeyWithOtherHostAndPort() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI configURI=URI.create("//sonarqube.com:4444");
        builder=builder.resolveFromConfiguration(configURI);

        builder=builder.projectKey("x-y-z");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("https://sonarqube.com:4444/dashboard?id=x-y-z"),projectUri);
    }

    /**
     * Tests the creation of URL with project-key and branch-name with different host and path.
     */
    @Test
    void projectKeyWithOtherHostAndPath() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI configURI=URI.create("//sonarqube.com/aaa/bbb/dashboard");
        builder=builder.resolveFromConfiguration(configURI);

        builder=builder.projectKey("x-y-z");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("https://sonarqube.com/aaa/bbb/dashboard?id=x-y-z"),projectUri);
    }

    /**
     * Tests the creation of URL with project-key and branch-name with different host and port and path.
     */
    @Test
    void projectKeyWithOtherHostAndPortAndPath() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI configURI=URI.create("//sonarqube.com:4444/aaa/bbb/dashboard");
        builder=builder.resolveFromConfiguration(configURI);

        builder=builder.projectKey("x-y-z");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("https://sonarqube.com:4444/aaa/bbb/dashboard?id=x-y-z"),projectUri);
    }
}
