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

package com.yelstream.topp.standard.net.resource.identification;

import com.yelstream.topp.standard.net.resource.identification.builder.util.MappedQuery;
import com.yelstream.topp.standard.net.resource.identification.builder.util.SegmentedPath;
import com.yelstream.topp.standard.net.resource.identification.builder.util.TaggedPath;
import com.yelstream.topp.standard.net.resource.identification.handler.URISchemeHandler;
import com.yelstream.topp.standard.net.resource.identification.util.URIArgument;
import com.yelstream.topp.standard.net.resource.identification.util.URIConstructor;
import com.yelstream.topp.standard.net.resource.identification.util.URIConstructors;
import lombok.experimental.UtilityClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

/**
 * Utility addressing instances of {@link URI}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-07-12
 */
@UtilityClass
public class URIs {


    public static URL toURL(URI uri) {
        try {
            return uri.toURL();
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("Failure to convert URI to URL; actual URI is '%s'!".formatted(uri));
        }
    }

    public static URL toURL(URI uri,
                            URLStreamHandler handler) {
        try {
            return URL.of(uri,handler);
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("Failure to convert URI to URL; actual URI is '%s'!".formatted(uri));
        }
    }




    /**
     * Creates a URI.
     * <p>
     * The creation is on the premises of the existing constructors of {@link URI} and Lombok.
     * </p>
     * <p>
     * For another example of a builder, with a different purpose of supporting REST, consider taking a look at the
     * <a href="https://jakarta.ee/specifications/restful-ws/4.0/apidocs/jakarta.ws.rs/jakarta/ws/rs/core/uribuilder">Jakarta JAX-RS UriBuilder</a>.
     * </p>
     *
     * @param scheme             Scheme.
     * @param schemeSpecificPart Scheme specific part.
     * @param authority          Authority.
     * @param userInfo           User info.
     * @param host               Host.
     * @param port               Port.
     * @param path               Path.
     * @param query              Query.
     * @param fragment           Fragment.
     * @return Created URI.
     * @throws URISyntaxException Thrown in case of URI syntax error.
     */
    @lombok.Builder(builderClassName="Builder")  //TO-DO: Rename to 'URIBuilder'!
    @SuppressWarnings({"java:S2589", "java:S1066", "ConstantConditions", "unused", "java:S3776", "java:S107"})
    private static URI create(URIArgument argument,
                              URIConstructor constructor,
                              URISchemeHandler handler) throws URISyntaxException {

        constructor=URIConstructors.selectByApplicability(argument);
if (constructor==null) {
    System.out.println("--- NO CONSTRUCTOR!");
    System.out.println("   Argument:"+argument);
    System.out.println("---");
    System.out.println();
}

        URI uri = constructor.create(argument);
        return uri;
    }

    /**
     * Builder of {@link URI} instances.
     */
    @SuppressWarnings({"unused", "java:S1450", "FieldCanBeLocal", "UnusedReturnValue", "java:S1066"})
    public static class Builder {
        public Builder uri(URI uri) {
            argument(URIArgument.of(uri));
            constructor(null);
            handler(null);
            postCreate();
            return this;
        }

        public Builder argumentUpdate(Consumer<URIArgument.Builder> consumer) {
            URIArgument.Builder builder=argument.toBuilder();
            consumer.accept(builder);
            argument(builder.build());
            return this;
        }

        /**
         * Applies state specific fiddling as a post action when all fields have been set.
         */
        @SuppressWarnings({"java:S1301", "SwitchStatementWithTooFewBranches"})
        private void postCreate() {
            URIArgument a=argument;
            if (a.getScheme()!=null) {
                switch (a.getScheme()) {
                    case "file" -> {
                        if (a.getSchemeSpecificPart().startsWith("//")) {
                            if (a.getHost() == null) {
                                a=a.toBuilder().host("").build();
                                argument(a);
                            }
                        }
                    }
                    default -> {
                        //Ignore!
                    }
                }
            }
        }

        public static Builder fromURI(URI uri) {
            return builder().uri(uri);
        }

        public static Builder fromURI(String uri) throws URISyntaxException {
            return fromURI(new URI(uri));
        }

        public static Builder fromURL(URL url) throws URISyntaxException {
            return fromURI(url.toURI());
        }

        public static Builder fromPath(String path) {
            return builder().argumentUpdate(b->b.path(path));  //TO-DO: From path from scratch!
        }

        public TaggedPath taggedPath() {
            return argument.taggedPath();
        }

/* TODO: Fix!
        public Builder taggedPath(TaggedPath taggedPath) {
            path(taggedPath.toFullPath());
            return this;
        }
*/

/* TODO: Fix!
        public String tag() {
            return taggedPath().tag();
        }
*/

/* TODO: Fix!
        public Builder tag(String tag) {
            taggedPath(taggedPath().tag(tag));
            return this;
        }
*/

/* TODO: Fix!
        public String untaggedPath() {
            return TaggedPath.ofFullPath(path).path();
        }
*/

/* TODO: Fix!
        public Builder untaggedPath(String path) {
            taggedPath(taggedPath().path(path));
            return this;
        }
*/

        public Builder taggedPathUpdate(Consumer<TaggedPath> consumer) {
            argumentUpdate(b->b.taggedPathUpdate(consumer));
            return this;
        }


/* TODO: Fix!
        public MappedQuery mappedQuery() {
            return MappedQuery.of(query);
        }
*/

/* TODO: Fix!
        public Builder mappedQuery(MappedQuery mappedQuery) {
            query(mappedQuery.formatAsString());
            return this;
        }
*/

        public Builder mappedQueryUpdate(Consumer<MappedQuery> consumer) {
            argumentUpdate(b->b.mappedQueryUpdate(consumer));
            return this;
        }

        public SegmentedPath segmentedPath() {
            return null;  //TO-DO: Fix!
        }

        public Builder segmentedPath(SegmentedPath segmentedPath) {
            //TO-DO: Fix!
            return this;
        }

        private static void onNotEmpty(String value, Consumer<String> consumer) {  //TO-DO: Consider location!
            if (value!=null && !value.isEmpty()) {
                consumer.accept(value);
            }
        }

        private static void onCondition(int value,
                                        IntPredicate condition,
                                        IntConsumer consumer) {  //TO-DO: Consider location!
            if (condition.test(value)) {
                consumer.accept(value);
            }
        }

        private static <E> void onNotNull(E value, Consumer<E> consumer) {  //TO-DO: Consider location!
            if (value!=null) {
                consumer.accept(value);
            }
        }

        private static void onEmpty(String value, Runnable runnable) {  //TO-DO: Consider location!
            if (value==null || value.isEmpty()) {
                runnable.run();
            }
        }

        private static <E> void onNull(E value, Runnable runnable) {  //TO-DO: Consider location!
            if (value==null) {
                runnable.run();
            }
        }
    }
}
