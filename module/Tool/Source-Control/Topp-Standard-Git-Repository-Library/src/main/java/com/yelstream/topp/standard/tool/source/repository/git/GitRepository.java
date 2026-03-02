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

package com.yelstream.topp.standard.tool.source.repository.git;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import org.jspecify.annotations.Nullable;

import java.net.URI;
import java.nio.file.Path;
import java.util.Map;

/**
 * Git repository identification.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-02
 */
@AllArgsConstructor
@lombok.Builder(builderClassName = "Builder", toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
public class GitRepository {
    /**
     * Protocol to access repository.
     */
    @AllArgsConstructor
    public enum Access {
        /**
         * Valid HTTPS URI, e.g. {@code https://github.com/user/repo.git}.
         */
        HTTPS("https"),

        /**
         * SCP-like syntax, not a valid URI, e.g. {@code git@github.com:user/repo.git}.
         */
        SCP("scp"),

        /**
         * Valid SSH URI, e.g. {@code ssh://git@github.com/user/repo.git}.
         */
        SSH("ssh");

        /**
         * URI schema.
         */
        private final String schema;
    }

    /**
     * Repository location.
     */
    private final URI location;

    /**
     * Repository name.
     */
    private final String name;

    /**
     * Repository branch.
     */
    @Nullable
    private final String branch;

    /**
     * Repository tag.
     */
    @Nullable
    private final String tag;

    /**
     * Repository commit.
     */
    @Nullable
    private final String commit;

    /**
     *
     */
    @Singular("accessURI")
    private final Map<Access,URI> accessURIs;

    /**
     *
     */
    private final Path localDirectory;


    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    public static GitRepository ofLocation(URI location) {
        return
            switch (location.getScheme()) {
                case "https" -> {
                    Builder builder=builder();
                    builder.location(location);
                    builder.accessURI(Access.HTTPS,URI.create(location+".git"));
                    builder.accessURI(Access.SSH,URI.create("ssh://git@"+location.getHost()+location.getPath()+".git"));
                    builder.branch(StandardBranchName.Main.getBranchName());  //Yes, use this branch per default!
                    builder.name(Path.of(location.getPath()).getFileName().toString());
                    builder.localDirectory(Path.of(location.getPath()).getFileName());
                    yield builder.build();
                }
                default -> throw new UnsupportedOperationException();
            };
    }

    public static void main(String[] args) {  //TODO: Create unit test!
        URI location = URI.create("https://github.com/sabroe/Topp-Standard");
        System.out.println(ofLocation(location));
    }
}
