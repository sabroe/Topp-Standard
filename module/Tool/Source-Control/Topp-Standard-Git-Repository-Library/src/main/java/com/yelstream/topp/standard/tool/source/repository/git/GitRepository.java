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
import lombok.Getter;
import lombok.Singular;
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
public class GitRepository {
    /**
     * Protocol to access repository.
     */
    @AllArgsConstructor
    public enum Access {
        HTTPS("https"),
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
    @Singular("accessUri")
    private final Map<Access,URI> accessUris;

    /**
     *
     */
    private final Path localDirectory;
}
