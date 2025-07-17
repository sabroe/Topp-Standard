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
import lombok.Singular;
import lombok.ToString;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Segmented URI path where the path is broken into individual elements.
 * <p>
 * A segmented path consists of elements (e.g., ["a", "b"] for "a/b"),
 * with flags indicating whether the path is absolute (starts with "/") or a container (ends with "/").
 * This class provides methods to create, manipulate, and convert segmented paths to and from URI strings.
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2025-07-17
 */
@ToString
@Getter
@lombok.Builder(builderClassName="Builder",toBuilder=true)
@AllArgsConstructor(staticName="of")
public final class SegmentedPath {
    /**
     * Ordered list of path elements (e.g., ["yelstream.com", "topp"] for "/yelstream.com/topp").
     */
    @Singular
    private final List<String> elements;

    /**
     * Indicates, if full, textual path starts with a forward slash "/".
     */
    private final boolean absolute;

    /**
     * Indicates, if full, textual path ends with a forward slash "/", representing a container.
     */
    private final boolean container;

    /**
     * Indicates if this path represents a content resource (file-like, no trailing slash).
     * @return True if the path is content (not a container), false otherwise.
     */
    public boolean isContent() {
        return !container;
    }

    /**
     * Indicates if this path represents the root container (empty path with absolute and container flags).
     * @return True if the path is the root container (e.g., "/"), false otherwise.
     */
    public boolean isContainerRoot() {
        return elements.isEmpty() && absolute && container;
    }

    /**
     * Validates the path's elements using the default validation rules.
     * <p>
     *     Useful for post-construction checks, such as after deserialization,
     *     of if the builder was used with a non-default verification of elements.
     * </p>
     * @throws IllegalArgumentException If any element is null, blank, or contains a slash.
     */
    public void validate() {
        validateElements(elements);
    }

    /**
     * Gets the number of elements in the path.
     * @return Number of elements.
     */
    public int length() {
        return elements.size();
    }

    /**
     * Gets the element at a specific index.
     * @param index Index of element.
     * @return Element at a specific index.
     * @throws IllegalArgumentException Thrown in case of the index is out of bounds.
     */
    public String elementAt(int index) {
        if (index<0 || index>=elements.size()) {
            throw new IllegalArgumentException("Failure to address element at index; index is '%d', element count is '%d'!".formatted(index,elements.size()));
        }
        return elements.get(index);
    }

    /**
     * Converts the segmented path to a URI path string.
     * <p>
     * Examples:
     *     <ul>
     *         <li>{@code elements=["yelstream.com", "topp"], absolute=true, container=true} → {@code /yelstream.com/topp/}</li>
     *         <li>{@code elements=["yelstream.com", "topp"], absolute=false, container=false} → {@code yelstream.com/topp}</li>
     *         <li>{@code elements=[], absolute=false, container=false} → {@code ""}</li>
     *     </ul>
     * </p>
     * @return The URI path string, respecting absolute and container flags.
     */
    public String toPath() {
        if (elements.isEmpty()) {
            if (absolute && container) {
                return "/";
            }
            return "";
        }
        StringBuilder path = new StringBuilder();
        if (absolute) {
            path.append("/");
        }
        path.append(String.join("/", elements));
        if (container) {
            path.append("/");
        }
        return path.toString();
    }

    /**
     * Converts the segmented path to a URI.
     * @return Created URI.
     * @throws IllegalArgumentException Thrown in case of the URI being invalid.
     */
    public URI toURI() {
        URI uri=URI.create(toPath());
        StandardURIPredicate.IsPathOnly.requireMatch(uri);  //Note: Safety check for now; should not be necessary!
        return uri;
    }

    /**
     * Creates an empty, segmented path.
     * @return Created segmented path.
     */
    public static SegmentedPath of() {
        return SegmentedPath.of(Collections.emptyList(), false, false);
    }

    /**
     * Creates a segmented path from a URI path string.
     * <p>
     * For example:
     *     <ul>
     *         <li>{@code /yelstream.com/topp/} → {@code elements=["yelstream.com", "topp"], absolute=true, container=true}</li>
     *         <li>{@code yelstream.com/topp} → {@code elements=["yelstream.com", "topp"], absolute=false, container=false}</li>
     *         <li>{@code /yelstream.com/topp} → {@code elements=["yelstream.com", "topp"], absolute=true, container=false}</li>
     *     </ul>
     *     Leading slashes determine {@code absolute}, and trailing slashes determine {@code container}.
     *     Empty or null paths result in an empty segment list with {@code absolute=false, container=false}.
     * </p>
     *
     * @param path The URI path (e.g., {@code /yelstream.com/topp} or {@code yelstream.com/topp/}).
     * @return Created segmented path.
     * @throws IllegalArgumentException Thrown in case of path not being valid.
     */
    public static SegmentedPath ofPath(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Failure to create segmented path; path is not set!");
        }
        if (path.isEmpty()) {
            return of();
        }
        if (path.isBlank()) {
            throw new IllegalArgumentException("Failure to create segmented path; path cannot be blank, path is '%s'!");
        }
        boolean absolute=path.startsWith("/");
        boolean container=path.endsWith("/");
        List<String> segments=createCleanElements(path);
        return SegmentedPath.of(Collections.unmodifiableList(segments),absolute,container);
    }

    /**
     * Creates a segmented path from a URI.
     * @param uri URI containing a path-only string.
     * @return Created segmented path.
     * @throws IllegalArgumentException Thrown in case of the URI being invalid.
     */
    public static SegmentedPath ofURI(URI uri) {
        StandardURIPredicate.IsPathOnly.requireMatch(uri);  //This is the case of e.g. an "entry" from a JAR URL and kept in a URI!
        return ofPath(uri.getPath());
    }

    /**
     * Removes leading and trailing slashes from the path.
     * @param path Path.
     * @return Cleaned path.
     */
    private static String cleanPath(String path) {
        return path.replaceAll("(^/+)|(/+$)", "");
    }

    /**
     * Splits the path into elements and validates them.
     * @param path Path.
     * @return Cleaned elements.
     * @throws IllegalArgumentException Thrown in case of any element being invalid.
     */
    private static List<String> createCleanElements(String path) {
        String cleanedPath=cleanPath(path);
        List<String> elements=splitPath(cleanedPath);
        validateElements(elements);
        return elements;
    }

    /**
     * Splits a path into elements.
     * @param path Path.
     * @return List of elements.
     */
    private static List<String> splitPath(String path) {
        return path.isEmpty()?Collections.emptyList():Arrays.asList(path.split("/"));
    }

    /**
     * Validates elements using the default validation rules.
     * @param elements Elements to validate.
     * @throws IllegalArgumentException Thrown in case of any element being invalid.
     */
    private static void validateElements(List<String> elements) {
        validateElements(elements,SegmentedPath::validateElement);
    }

    /**
     * Validates aelements using a custom validation function.
     * @param elements Elements to validate.
     * @param elementValidation Validation function to apply to each element.
     * @throws IllegalArgumentException Thrown in case of any element being invalid.
     */
    private static void validateElements(List<String> elements,
                                         Consumer<String> elementValidation) {
        for (String element: elements) {
            validateElement(element,elementValidation);
        }
    }

    /**
     * Validates a single element using a custom validation function.
     * @param element Element to validate.
     * @param elementValidation Validation function to apply to each element.
     * @throws IllegalArgumentException Thrown in case of any element being invalid.
     */
    private static void validateElement(String element,
                                        Consumer<String> elementValidation) {
        elementValidation.accept(element);
    }

    /**
     * Default validation for a single element.
     * @param element Element to validate.
     * @throws IllegalArgumentException Thrown in case of any element being invalid.
     */
    private static void validateElement(String element) {
        if (element==null) {
            throw new IllegalArgumentException("Failure to validate element; element is not set!");
        }
        if (element.isEmpty()) {
            throw new IllegalArgumentException("Failure to validate element; element is empty!");
        }
        if (element.isBlank()) {
            throw new IllegalArgumentException("Failure to validate element; element is non-empty, but blank, element is '%s'".formatted(element));
        }
        if (element.contains("/")) {
            throw new IllegalArgumentException("Failure to validate element; element contains '/', element is '%s'".formatted(element));
        }
    }

    /**
     * Builder of {@link SegmentedPath} instances.
     */
    public static class Builder {
        /**
         * Applied validation of elements.
         * <p>
         *     Allows for a custom setting.
         * </p>
         */
        private Consumer<String> elementValidation=SegmentedPath::validateElement;

        /**
         * Indicates, if full, textual path starts with a forward slash "/".
         */
        private boolean absolute;

        /**
         * Indicates, if full, textual path ends with a forward slash "/", representing a container.
         */
        private boolean container;

        /**
         * Sets a custom validation function for elements.
         * @param elementValidation The validation function to apply to each element, or null to use default validation.
         * @return This builder.
         */
        public Builder elementValidation(Consumer<String> elementValidation) {
            this.elementValidation=elementValidation==null?SegmentedPath::validateElement:elementValidation;
            return this;
        }

        /**
         * Sets the path as absolute (starting with "/").
         * @param absolute Indicates, if path is absolute.
         * @return This builder.
         */
        public Builder absolute(boolean absolute) {
            this.absolute=absolute;
            return this;
        }

        /**
         * Sets the path as absolute (starting with "/").
         * @return This builder.
         */
        public Builder absolute() {
            return absolute(true);
        }

        /**
         * Sets the path as relative (not starting with "/").
         * @return This builder.
         */
        public Builder relative() {
            return absolute(false);
        }

        /**
         * Sets the path as a container (ending with "/").
         * @param container Indicates, if path is a container.
         * @return This builder.
         */
        public Builder container(boolean container) {
            this.container=container;
            return this;
        }

        /**
         * Sets the path as a container (ending with "/").
         * @return This builder.
         */
        public Builder container() {
            return container(true);
        }

        /**
         * Sets the path as a content resource (not ending with "/").
         * @return This builder.
         */
        public Builder content() {
            return container(false);
        }

        /**
         * Appends a list of validated elements to the path.
         * @param elements Elements.
         * @return This builder.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public Builder appendElements(List<String> elements) {
            validateElements(elements, elementValidation);
            return elements(elements);
        }

        /**
         * Appends a single validated element to the path.
         * @param element Element.
         * @return This builder.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public Builder appendElement(String element) {
            validateElement(element, elementValidation);
            return element(element);
        }

        /**
         * Appends an element and sets the path as a container.
         * @param element Element.
         * @return This builder.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public Builder appendContainerElement(String element) {
            return container().appendElement(element);
        }

        /**
         * Appends an element and sets the path as a content resource.
         * @param element Element.
         * @return This builder.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public Builder appendContentElement(String element) {
            return content().appendElement(element);
        }

        /**
         * Validates the current elements using the configured validation function.
         * @return This builder.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public Builder validate() {
            validateElements(elements, elementValidation);
            return this;
        }

        /**
         * Builds a validated segmented path.
         * @return Created segmented path.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public SegmentedPath build() {
            validateElements(elements, elementValidation);
            return SegmentedPath.of(elements, absolute, container);
        }

        /**
         * Builds a container path.
         * @return Created segmented path.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public SegmentedPath buildContainer() {
            return container().build();
        }

        /**
         * Builds a content path.
         * @return Created segmented path.
         * @throws IllegalArgumentException Thrown in case of an element being invalid.
         */
        public SegmentedPath buildContent() {
            return content().build();
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
