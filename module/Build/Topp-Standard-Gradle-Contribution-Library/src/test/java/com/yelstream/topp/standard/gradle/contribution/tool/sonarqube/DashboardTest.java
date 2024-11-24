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
     * Tests creating a URL with parameters for project-key and branch-name set.
     */
    @Test
    void projectKeyAndBranchName() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        builder=builder.projectKey("xxx-yyy").branchName("special");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("https://localhost:9000/dashboard?id=xxx-yyy&branch=special"),projectUri);
    }

    /**
     * Tests creating a URL with a preset base URL different from the default.
     */
    @Test
    void overrideURI() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI overrideURI=URI.create("http://sonarqube.com");
        builder=builder.overrideURI(overrideURI);

        builder=builder.projectKey("xxx-yyy");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("http://sonarqube.com/dashboard?id=xxx-yyy"),projectUri);
    }

    /**
     * Tests creating a URL with a preset base URL with a specific port.
     */
    @Test
    void overrideURIWithPort() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI overrideURI=URI.create("http://sonarqube.com:9999");
        builder=builder.overrideURI(overrideURI);

        builder=builder.projectKey("xxx-yyy");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("http://sonarqube.com:9999/dashboard?id=xxx-yyy"),projectUri);
    }

    /**
     * Tests creating a URL with a preset base URL but keeping the default port.
     */
    @Test
    void overrideURIButKeepingDefaultPort() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI overrideURI=URI.create("http://sonarqube.com:0");
        builder=builder.overrideURI(overrideURI);

        builder=builder.projectKey("xxx-yyy");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("http://sonarqube.com:9000/dashboard?id=xxx-yyy"),projectUri);
    }

    /**
     * Tests creating a URL with a preset base URL with an offset path.
     */
    //@Test
    //TO-DO: Consider this against possible, viable URL-resolution schemes!
    void overrideURIWithOffsetPath() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI overrideURI=URI.create("http://sonarqube.com/aaa/bbb/ccc");
        builder=builder.overrideURI(overrideURI);

        builder=builder.projectKey("xxx-yyy");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("http://sonarqube.com/aaa/bbb/ccc/dashboard?id=xxx-yyy"),projectUri);
    }

    /**
     * Tests creating a URL with a preset base URL with an explicit path.
     */
    @Test
    void overrideBaseURIWithOffsetPath() throws URISyntaxException {
        Dashboard.Project.Builder builder=Dashboard.Project.builder();

        URI overrideURI=URI.create("http://sonarqube.com/aaa/bbb/ccc/xxx");
        builder=builder.overrideURI(overrideURI);

        builder=builder.projectKey("xxx-yyy");

        Dashboard.Project project=builder.build();
        URI projectUri=project.getURI();

        Assertions.assertEquals(URI.create("http://sonarqube.com/aaa/bbb/ccc/xxx?id=xxx-yyy"),projectUri);
    }
}
