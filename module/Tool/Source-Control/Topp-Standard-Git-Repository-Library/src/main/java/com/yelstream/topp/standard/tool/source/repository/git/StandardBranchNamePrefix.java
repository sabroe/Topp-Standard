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

/**
 * Standard branch name prefix.
 * <p>
 *     A prefix helps teams and users to quickly understand the purpose of a branch just by looking at its name
 *     (especially useful in large repositories or when using tools like GitHub/GitLab/Azure that group branches).
 * </p>
 * <p>
 *     Mapped are the most common prefixes
 *     (based on Git Flow, GitHub Flow, conventional commits-inspired workflows, enterprise patterns, and open-source trends).
 *</p>
 * <p>
 *     Use at your own caution.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-02
 */
@AllArgsConstructor
@SuppressWarnings("java:S115")
public enum StandardBranchNamePrefix {
    /**
     * Developing a new feature or new user story.
     */
    Feature("feature"),

    /**
     * Fixing a non-urgent bug (found during development).
     */
    Bugfix("bugfix"),

    /**
     * Urgent production fix (critical bug in live system).
     */
    Hotfix("hotfix"),

    /**
     * Preparing/stabilizing a new version/tag.
     */
    Release("release"),

    /**
     * Code cleanup/restructuring (no behavior change).
     */
    Refactor("refactor"),

    /**
     * Documentation/README/comments only.
     */
    Docs("docs"),

    /**
     * Adding/improving tests (unit, e2e, integration).
     */
    Test("test"),

    /**
     * Maintenance tasks (dependencies, config, scripts).
     */
    Chore("chore"),

    /**
     * Spikes, PoCs, A/B tests (usually short-lived).
     */
    Experiment("experiment");

    @Getter
    private final String branchNamePrefix;
}
