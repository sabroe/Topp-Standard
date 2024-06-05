/*
 * Project: Topp Standard
 * GitHub: https://github.com/sabroe/Topp-Standard
 *
 * Copyright 2024 Morten Sabroe Mortensen
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

package com.yelstream.topp.standard.net;

import lombok.experimental.UtilityClass;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utility addressing instances of {@link URI}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-05
 */
@UtilityClass
public class URIs {

    /**
     *
     * https://jakarta.ee/specifications/restful-ws/4.0/apidocs/jakarta.ws.rs/jakarta/ws/rs/core/uribuilder
     *
     * @param scheme
     * @param schemeSpecificPart
     * @param authority
     * @param userInfo
     * @param host
     * @param port
     * @param path
     * @param query
     * @param fragment
     * @return
     * @throws URISyntaxException
     */
    @lombok.Builder(builderClassName="Builder")  //TODO: Rename to 'URIBuilder'!
    @SuppressWarnings({"java:S2589","java:S1066","ConstantConditions","unused","java:S3776","java:S107"})
    private static URI create(String scheme,
                              String schemeSpecificPart,
                              String authority,
                              String userInfo,
                              String host,
                              int port,
                              String path,
                              String query,
                              String fragment) throws URISyntaxException {
        URI uri=null;

        if (uri==null) {
            if (schemeSpecificPart!=null && (authority==null && userInfo==null && host==null && port==-1 && path==null && query==null)) {
                uri=new URI(scheme,schemeSpecificPart,fragment);
            }
        }

        if (uri==null) {
            if (userInfo!=null || port!=-1) {
                uri=new URI(scheme,userInfo,host,port,path,query,fragment);
            }
        }

        if (uri==null) {
            if ((host!=null || path!=null || fragment!=null) && (userInfo==null && port==-1 && query==null && authority==null)) {
                uri=new URI(scheme,host,path,fragment);
            }
        }

        if (uri==null) {
            if (authority!=null) {
                uri=new URI(scheme,authority,path,query,fragment);
            }
        }

        return uri;
    }

    @SuppressWarnings({"unused","java:S1450","FieldCanBeLocal","UnusedReturnValue","java:S1066"})
    public static class Builder {
         public Builder uri(URI uri) {
            scheme(uri.getScheme());
            schemeSpecificPart(uri.getSchemeSpecificPart());
            authority(uri.getAuthority());
            userInfo(uri.getUserInfo());
            host(uri.getHost());
            port(uri.getPort());
            path(uri.getPath());
            query(uri.getQuery());
            fragment(uri.getFragment());
            if ("file".equals(scheme)) {
                if (host==null) {
                    host("");
                }
            }
            return this;
        }

        public static Builder fromURI(URI uri) {
            return builder().uri(uri);
        }

/*
        public static Builder fromURI(String uri) {
            return fromURI(new URI(uri));
        }
*/

        public static Builder fromPath(String path) {
            return builder().path(path);
        }
    }

}

class Test {

    /*
     mailto:java-net@www.example.com
     news:comp.lang.java
     urn:isbn:096139210x

     http://example.com/languages/java/
     sample/a/index.html#28
     ../../demo/b/index.html
     file:///~/calendar
     */
    public static void main(String[] args) throws URISyntaxException {
        {
            URIs.Builder builder = URIs.builder();
            URI uri1 = new URI("mailto:java-net@www.example.com");
            builder.uri(uri1);
            URI uri2 = builder.build();
            System.out.println(uri1 + ", " + uri2);
        }
        {
            URIs.Builder builder = URIs.builder();
            URI uri1 = new URI("news:comp.lang.java");
            builder.uri(uri1);
            URI uri2 = builder.build();
            System.out.println(uri1 + ", " + uri2);
        }
        {
            URIs.Builder builder = URIs.builder();
            URI uri1 = new URI("urn:isbn:096139210x");
            builder.uri(uri1);
            URI uri2 = builder.build();
            System.out.println(uri1 + ", " + uri2);
        }
        {
            URIs.Builder builder = URIs.builder();
            URI uri1 = new URI("http://example.com/languages/java/");
            builder.uri(uri1);
            builder.host("xxx.com");
            URI uri2 = builder.build();
            System.out.println(uri1 + ", " + uri2);
        }
        {
            URIs.Builder builder = URIs.builder();
            URI uri1 = new URI("sample/a/index.html#28");
            builder.uri(uri1);
            URI uri2 = builder.build();
            System.out.println(uri1 + ", " + uri2);
        }
        {
            URIs.Builder builder = URIs.builder();
            URI uri1 = new URI("../../demo/b/index.html");
            builder.uri(uri1);
            URI uri2 = builder.build();
            System.out.println(uri1 + ", " + uri2);
        }
        {
            URIs.Builder builder = URIs.builder();
            URI uri1 = new URI("file:///~/calendar");
            builder.uri(uri1);
            URI uri2 = builder.build();
            System.out.println(uri1 + ", " + uri2);
        }
        {
            URIs.Builder builder = URIs.builder();
            URI uri1 = new URI("file://server/~/calendar");
            builder.uri(uri1);
            URI uri2 = builder.build();
            System.out.println(uri1 + ", " + uri2);
        }
        {
            URIs.Builder builder = URIs.builder();
            URI uri1 = new URI("file://server/~/calendar-dir/");
            builder.uri(uri1);
            URI uri2 = builder.build();
            System.out.println(uri1 + ", " + uri2);
        }
    }

}