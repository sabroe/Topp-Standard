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
     * Creates a URI.
     * <p>
     *     The creation is on the premises of the existing constructors of {@link URI} and Lombok.
     * </p>
     * <p>
     *     For another example of a builder, with a different purpose, consider taking a look at
     *     <a href="https://jakarta.ee/specifications/restful-ws/4.0/apidocs/jakarta.ws.rs/jakarta/ws/rs/core/uribuilder">JAX-RS UriBuilder</a>.
     * </p>
     *
     * @param scheme Scheme.
     * @param schemeSpecificPart Scheme specific part.
     * @param authority Authority.
     * @param userInfo User info.
     * @param host Host.
     * @param port Port.
     * @param path Path.
     * @param query Query.
     * @param fragment Fragment.
     * @return Created URI.
     * @throws URISyntaxException Thrown in case of URI syntax error.
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
            if (userInfo!=null || port!=-1 || (host!=null && (port!=-1 || query!=null))) {
                uri=new URI(scheme,userInfo,host,port,path,query,fragment);
            }
        }

        if (uri==null) {
            if ((host!=null || path!=null || fragment!=null) && (userInfo==null && port==-1 && query==null/* && authority==null*/)) {
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

    /**
     * Builder of {@link URI} instances.
     */
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
            if ("file".equals(scheme) && schemeSpecificPart.startsWith("//")) {
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
