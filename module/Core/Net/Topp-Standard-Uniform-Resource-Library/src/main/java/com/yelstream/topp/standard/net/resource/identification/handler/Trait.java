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

package com.yelstream.topp.standard.net.resource.identification.handler;

/**
 * Trait is the set of characteristics indicating what a URI scheme handler does, how it behaves, and may be used.
 * <p>
 *     These are mostly relevant for non-standard URI schemes.
 *     Dedicated URL scheme handlers are an exception to otherwise normalized handling.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-20
 */
public interface Trait {
    /**
     * Indicates, if the URI scheme is regular.
     * <p>
     *     Regular in the sense that no elements are up for special interpretation,
     *     that there is no "entry", no "tag", no special properties.
     * <p>
     * @return Indicates, if the scheme is regular.
     */
    boolean isRegular();

    /**
     * Indicates, if the URI scheme has "an entry" in its scheme-specific part.
     * <p>
     *     The definition of an entry comes from a JAR URL like {@code jar:<url>!/entry}.
     *     It is, by defintion, the part after "!/" in the scheme-specific part.
     * <p>
     * @return Indicates, if the scheme has an entry.
     */
    boolean isWithEntry();

    /**
     * Indicates, if the URI scheme has properties in its scheme-specific part.
     * <p>
     *     Properties start with a semicolon ";" as in a JDBC URL.
     *     For
     *     {@code jdbc:sqlserver://localhost:1433;databaseName=database1;user=user1;password=password1}
     *     the properties are named {@code databaseName}, {@code user}, {@code password}.
     * <p>
     * @return Indicates, if the scheme has properties.
     */
    boolean isWithProperties();

    /**
     * Indicates, if the URI scheme has a tag in its scheme-specific part.
     * <p>
     *     A Docker URL may have a tag like {@code latest} or {@code 1.0.0},
     *     initiated by a colon ":" at the end of the path.
     * <p>
     * @return Indicates, if the scheme has a tag.
     */
    boolean isWithTag();

    /**
     * Indicates, if the URI scheme has an inner URI in its scheme-specific part.
     * <p>
     *     A JAR URL of the form {@code jar:<url>!/entry} has,
     *     per definition,
     *     an inner URI in the form of {@code <url>}.
     *     This inner URI may be a regular file URI or a non-regular JAR URI.
     * <p>
     * <p>
     *     A non-regular JDBC URI like
     *     {@code jdbc:sqlserver://localhost:1433;databaseName=database1;user=user1;password=password1}
     *     has,
     *     once stripped from the scheme name {@code jdbc} and the properties,
     *     an inner URI
     *     {@code sqlserver://localhost:1433}.
     * </p>
     * @return Indicates, if the scheme has an inner URI.
     */
    boolean isWithInnerURI();

    /**
     * Indicates, if the URI scheme has a dedicated post-construct operator to be applied upon
     * {@link com.yelstream.topp.standard.net.resource.identification.build.URIArgument}.
     * <p>
     *     This can be to "nudge" or modify certain values.
     * </p>
     * @return Indicates, if the scheme has a dedicated post-construct operator.
     */
//    boolean hasArgumentOperator();

    /**
     * Indicates, if the URI scheme has a dedicated constructor.
     * <p>
     *     This constructor
     *     {@link com.yelstream.topp.standard.net.resource.identification.build.URIConstructor}
     *     creates a {@link java.net.URI} from a
     *     {@link com.yelstream.topp.standard.net.resource.identification.build.URIArgument}.
     * </p>
     * <p>
     *     This can be to "nudge" or modify certain values.
     * </p>
     * @return Indicates, if the scheme has a dedicated constructor.
     */
//    boolean hasConstructor();


}
