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

package com.yelstream.topp.standard.net.resource.identification.builder.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Associates (path,tag) as present in a Docker name.
 * <p>
 *   Given a Docker name like
 *   {@code nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence:1.0.0}
 *   this associates ({@code /yelstream.com/topp/application/docker-intelligence},{@code 1.0.0}).
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-09
 */
@Getter
@Accessors(fluent=true)
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public class TaggedPath {
    /**
     * Path.
     * <p>
     *     This may not contain colons '{@code :}'.
     * </p>
     */
    private final String path;

    /**
     * Tag.
     * <p>
     *     This may not contain slashes '{@code /}'.
     * </p>
     */
    private final String tag;

    public TaggedPath path(String path) {
        return toBuilder().path(path).build();
    }

    public TaggedPath tag(String tag) {
        return toBuilder().tag(tag).build();
    }

    /**
     * Creates the full path.
     * @return Full path.
     */
    public String toFullPath() {
        return createFullPath(path,tag);
    }

    /**
     * Creates a full path.
     * @param path Path.
     * @param tag Tag.
     * @return Created full path.
     */
    public static String createFullPath(String path, String tag) {
        if (tag==null) {
            return path;
        } else {
            return String.format("%s:%s",path==null?"":path,tag);
        }
    }

    /**
     * Created a tagged path from a full path.
     * @param fullPath Full path.
     * @return Created tagged path.
     */
    public static TaggedPath ofFullPath(String fullPath) {
        int index=fullPath.lastIndexOf(':');
        if (index==-1) {
            return TaggedPath.of(fullPath,null);
        } else {
            return TaggedPath.of(fullPath.substring(0,index),fullPath.substring(index+1));
        }
    }

    public SegmentedPath segmentedPath() {
        return null;  //TO-DO: Fix!
    }

    public TaggedPath segmentedPath(SegmentedPath segmentedPath) {
        //TO-DO: Fix!
        return this;
    }
}
