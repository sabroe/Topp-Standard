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

package com.yelstream.topp.standard.lang.process.builder;

import lombok.Singular;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Utility addressing instances of {@link ProcessBuilder}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-02-10
 */
@UtilityClass
public class ProcessBuilders {
    @lombok.Builder(builderClassName="Builder")
    private static ProcessBuilder createProcessBuilder(Command command,
                                                       File directory,  //TO-DO: Consider creating a 'Directory' object!
                                                       @Singular List<Consumer<Map<String,String>>> environmentOps,
                                                       boolean redirectErrorStream,    //TO-DO: Consider creating a 'Redirects.Builder' object!
                                                       ProcessBuilder.Redirect redirectError,
                                                       ProcessBuilder.Redirect redirectOutput,
                                                       ProcessBuilder.Redirect redirectInput) {
        ProcessBuilder processBuilder=new ProcessBuilder(command.command());
        if (directory!=null) {
            processBuilder.directory(directory);
        }
        Map<String,String> environment=processBuilder.environment();
        environmentOps.forEach(op->op.accept(environment));
        if (redirectErrorStream) {
            processBuilder.redirectErrorStream(true);
        }
        if (redirectError!=null) {
            processBuilder.redirectError(redirectError);
        }
        if (redirectOutput!=null) {
            processBuilder.redirectOutput(redirectOutput);
        }
        if (redirectInput!=null) {
            processBuilder.redirectInput(redirectInput);
        }
        return processBuilder;
    }

    public static class Builder {
        public Builder inheritIO() {
            return redirectInput(ProcessBuilder.Redirect.INHERIT).
                   redirectOutput(ProcessBuilder.Redirect.INHERIT).
                   redirectError(ProcessBuilder.Redirect.INHERIT);
        }
    }
}
