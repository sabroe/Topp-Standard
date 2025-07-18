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

package com.yelstream.topp.standard.net.resource.identification.path;

import com.yelstream.topp.standard.net.resource.identification.util.StandardURIPredicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.net.URI;
import java.util.Objects;

/**
 * Associates (path,tag) as present in a Docker name.
 * <p>
 *     Given a Docker name like
 *     {@code nexus.yelstream.com:5000/yelstream.com/topp/application/docker-intelligence:1.0.0}
 *     this associates ({@code /yelstream.com/topp/application/docker-intelligence},{@code 1.0.0}).
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-09
 */
@ToString
@Getter
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public final class TaggedPath {
    /**
     * Base path without the tag.
     * <p>
     *     For example, {@code /yelstream.com/topp} in {@code /yelstream.com/topp:1.0.0}.
     *     Must not contain colons ({@code :}) and may be null or empty.
     * </p>
     * <p>
     *     This may not contain colons '{@code :}'.
     * </p>
     */
    private final String path;

    /**
     * Optional tag.
     * <p>
     *     For example, {@code 1.0.0} in {@code /yelstream.com/topp:1.0.0}.
     *     Must not contain slashes ({@code /}) if present and may be null.
     * </p>
     * <p>
     *     This may not contain slashes '{@code /}'.
     * </p>
     */
    private final String tag;

    /**
     * Creates a new instance with the specified base path.
     * @param path New base path, must not contain colons.
     * @return New tagged path instance with the updated path.
     * @throws IllegalArgumentException Thrown in case of invalid argument.
     */
    public TaggedPath replacePath(String path) {
        validatePath(path);
        return toBuilder().path(path).build();
    }

    /**
     * Creates a new instance with the specified tag.
     * @param tag New tag, must not contain slashes if non-null.
     * @return New tagged path instance with the updated tag.
     * @throws IllegalArgumentException Thrown in case of invalid argument.
     */
    public TaggedPath replaceTag(String tag) {
        validatePath(path);
        return toBuilder().tag(tag).build();
    }

    /**
     * Converts the base path to a segmented path.
     * <p>
     *     For example, {@code /yelstream.com/topp} becomes a {@link SegmentedPath}
     *     with elements {@code ["yelstream.com", "topp"]}.
     * </p>
     * @return Segmented path representing the base path.
     */
    public SegmentedPath toSegmentedPath() {
        return SegmentedPath.ofPath(path);
    }

    /**
     * Creates a new instance with the base path from a segmented path.
     * @param segmentedPath Segmented path to use as the new base path.
     * @return New tagged path instance with the updated path.
     */
    public TaggedPath replacePath(SegmentedPath segmentedPath) {
        Objects.requireNonNull(segmentedPath, "Failure to replace path; segmented path is not set!");
        return replacePath(segmentedPath.toPath());
    }

    /**
     * Creates the full path, combining the base path and tag.
     * <p>
     *     Examples:
     *     <ul>
     *         <li>{@code path="/yelstream.com/topp", tag="1.0.0"} → {@code /yelstream.com/topp:1.0.0}</li>
     *         <li>{@code path="/yelstream.com/topp", tag=null} → {@code /yelstream.com/topp}</li>
     *         <li>{@code path="", tag="1.0.0"} → {@code :1.0.0}</li>
     *         <li>{@code path=null, tag=null} → {@code ""}</li>
     *     </ul>
     * </p>
     * @return Full path.
     */
    public String toPath() {
        return formatPath(path,tag);
    }

    /**
     * Creates the full path as a URI.
     * <p>
     *     The resulting URI is path-only, with no scheme, authority, query, or fragment.
     *     For example, {@code /yelstream.com/topp:1.0.0} becomes {@code URI.create("/yelstream.com/topp:1.0.0")}.
     * </p>
     * @return Full path URI.
     */
    public URI toURI() {
        URI uri=URI.create(toPath());
        StandardURIPredicate.IsPathOnly.requireMatch(uri);  //Note: Safety check for now; should not be necessary!
        return uri;
    }

    /**
     * Creates a tagged path from a full path.
     * <p>
     *     The path is split at the last colon to separate the base path and tag.
     *     Examples:
     *     <ul>
     *         <li>{@code /yelstream.com/topp:1.0.0} → {@code path="/yelstream.com/topp", tag="1.0.0"}</li>
     *         <li>{@code /yelstream.com/topp} → {@code path="/yelstream.com/topp", tag=null}</li>
     *     </ul>
     * </p>
     * @param path Full path to parse.
     * @return Tagged path instance.
     * @throws IllegalArgumentException Thrown in case of invalid argument.
     */
    public static TaggedPath ofPath(String path) {
        Objects.requireNonNull(path, "Failure to created tagged path; path is not set!");
        if (path.isEmpty()) {
            return TaggedPath.of("",null);
        }
        int index=path.lastIndexOf(':');
        switch (index) {
            case -1 -> {
                validatePath(path);
                return TaggedPath.of(path,null);
            }
            case 0 -> {
                String tag=path.substring(index+1);
                validateTag(tag);
                return TaggedPath.of("",tag);
            }
            default -> {
                String basePath=path.substring(0,index);
                String tag=path.substring(index+1);
                validatePath(basePath);
                validateTag(tag);
                return TaggedPath.of(basePath,tag);
            }
        }
    }

    /**
     * Creates a tagged path from a path-only URI.
     * <p>
     *     The URI must satisfy {@link StandardURIPredicate#IsPathOnly}.
     *     For example, {@code URI.create("/yelstream.com/topp:1.0.0")} yields
     *     {@code path="/yelstream.com/topp", tag="1.0.0"}.
     * </p>
     * @param uri URI.
     * @return Tagged path instance.
     * @throws IllegalArgumentException Thrown in case of invalid argument.
     */
    public static TaggedPath ofURI(URI uri) {
        Objects.requireNonNull(uri, "Failure to created tagged path; URI is not set!");
        StandardURIPredicate.IsPathOnly.requireMatch(uri);
        return ofPath(uri.getPath());
    }

    /**
     * Formats a full path.
     * @param path Path.
     * @param tag Tag.
     * @return Created full path.
     */
    private static String formatPath(String path, String tag) {
        if (tag==null) {
            return path==null?"":path;
        } else {
            return String.format("%s:%s",path==null?"":path,tag);
        }
    }

    /**
     * Validates that the base path does not contain colons.
     * @param path Base path.
     * @throws IllegalArgumentException Thrown in case of invalid argument.
     */
    private static void validatePath(String path) {
        if (path!=null && path.contains(":")) {
            throw new IllegalArgumentException("Failure to validate path; path contains a colon, path is '%s'!".formatted(path));
        }
    }

    /**
     * Validates that the tag does not contain slashes.
     * @param tag Tag.
     * @throws IllegalArgumentException Thrown in case of invalid argument.
     */
    private static void validateTag(String tag) {
        if (tag != null && tag.contains("/")) {
            throw new IllegalArgumentException("Failure to validate tag; tag contains a slash, tag is '%s'!".formatted(tag));
        }
    }

    /**
     * Builder of {@link TaggedPath} instances.
     */
    public static class Builder {
        /**
         * Sets the base path from a segmented path.
         * @param segmentedPath Segmented path.
         * @return This builder.
         * @throws IllegalArgumentException Thrown in case of invalid argument.
         */
        public Builder segmentedPath(SegmentedPath segmentedPath) {
            return path(segmentedPath.toPath());
        }

        /**
         * Builds the path and converts it to a path.
         * @return The URI path string.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public String buildPath() {
            return build().toPath();
        }

        /**
         * Builds the path and converts it to a URI.
         * @return The URI.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public URI buildURI() {
            return build().toURI();
        }

        /**
         * Creates a builder initialized with the given path.
         * @param path Path.
         * @return Created builder.
         * @throws IllegalArgumentException Thrown in case of path being invalid.
         */
        public static Builder of(String path) {
            return ofPath(path).toBuilder();
        }

        /**
         * Creates a builder initialized with the given URI.
         * @param uri URI.
         * @return Created builder.
         * @throws IllegalArgumentException Thrown in case of URI being invalid.
         */
        public static Builder of(URI uri) {
            return ofURI(uri).toBuilder();
        }
    }
}
