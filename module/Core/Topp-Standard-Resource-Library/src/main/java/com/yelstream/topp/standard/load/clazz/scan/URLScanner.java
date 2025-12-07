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

package com.yelstream.topp.standard.load.clazz.scan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-29
 */
@FunctionalInterface
public interface URLScanner /*extends AutoCloseable*/ {  //TO-DO: Consider, if auto-closeable is necessary!
    /**
     *
     * <p>
     *     This is immutable.
     * </p>
     *
     */
    @lombok.Builder(builderClassName="Builder")
    @AllArgsConstructor(staticName="of")
    @Getter
    @ToString
    class Filter {
        @lombok.Builder.Default
        private final boolean includeRoot=true;

        @lombok.Builder.Default
        private final boolean includeFiles=true;

        @lombok.Builder.Default
        private final boolean includeDirectories=true;

/* TO-DO: Consider this; this can be done externally from the actual scanning!! Belongs at a higher level.
        @lombok.Builder.Default
        private final boolean verifyLookupOfURLForFile=true;

        @lombok.Builder.Default
        private final boolean verifyLookupOfURLForDirectory=true;

        @lombok.Builder.Default
        private final boolean verifyLookupOfContentForNameToFile=true;

        @lombok.Builder.Default
        private final boolean verifyLookupOfContentForNameToDirectory=true;
*/
    }

    /**
     *
     * <p>
     *     This is immutable.
     * </p>
     *
     */
    @lombok.Builder(builderClassName="Builder")
    @AllArgsConstructor(staticName="of")
    @Getter
    class Offset {

//        private final ClassLoader classLoader;

//        private String path;

        @lombok.Builder.Default
        private URL url=null;
    }

    Stream<String> scan(Offset offset,
                        Filter filter) throws IOException;

    default List<String> scanToList(Offset offset,
                                    Filter filter) throws IOException {
        try (Stream<String> stream=scan(offset,filter)) {
            return stream.toList();
        }
    }
}
