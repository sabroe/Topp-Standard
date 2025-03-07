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

package com.yelstream.topp.standard.interop.script;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.UtilityClass;

import javax.script.ScriptEngineFactory;
import java.util.List;
import java.util.Map;

/**
 * Utilities addressing script engine factories.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-03-07
 */
@UtilityClass
public class ScriptEngineFactories {
    /**
     *
     * <p>
     *     This is immutable.
     * </p>
     */
    @lombok.Builder(builderClassName="Builder",toBuilder=true)
    @AllArgsConstructor(staticName="of")
    @ToString
    @Getter
    public static class Matcher {
        private final String engineName;
        private final String engineVersion;
        private final List<String> extensions;
        private final List<String> mimeTypes;
        private final List<String> names;
        private final String languageName;
        private final String languageVersion;
        private final Map<String,Object> parameters;

        public boolean matches(ScriptEngineFactory factory) {
            return false;  //TO-DO: Fix!
        }
    }

    public static Matcher matcher(ScriptEngineFactory factory) {
        return null;  //TO-DO: Fix!
    }
}
