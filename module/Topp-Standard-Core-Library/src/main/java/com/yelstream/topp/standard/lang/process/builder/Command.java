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

package com.yelstream.topp.standard.lang.process.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Singular;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command lines for building processes.
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-10
 */
@Getter
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public class Command {
    /**
     * Program name.
     */
    private final String program;

    /**
     * Arguments.
     */
    @Singular
    private final List<String> arguments;

    public static class Builder {
        public Builder executable(Path path) {
            return program(path.toAbsolutePath().toString());
        }

        public Builder executable(File file) {
            return program(file.getAbsoluteFile().toString());
        }
    }

    public List<String> command() {
        List<String> all=new ArrayList<>();
        all.add(program);
        all.addAll(arguments);
        return Collections.unmodifiableList(all);
    }
}
