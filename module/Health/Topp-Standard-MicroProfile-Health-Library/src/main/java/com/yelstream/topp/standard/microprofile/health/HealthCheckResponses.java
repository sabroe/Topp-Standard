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

package com.yelstream.topp.standard.microprofile.health;

import com.yelstream.topp.standard.lang.annotation.Annotations;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Utility addressing instances of {@link HealthCheckResponse}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-22
 */
@Slf4j
@UtilityClass
public class HealthCheckResponses {
    /**
     * Default annotations allowed to be used as decoration upon data of a health-check response.
     */
    @SuppressWarnings("java:S2386")
    public static final List<Class<? extends Annotation>> DEFAULT_ALLOWED_ANNOTATIONS=HealthChecks.DEFAULT_PROBE_ANNOTATIONS;

    /**
     * Default name used to decorate data of a health-check response with annotations.
     */
    public static final String DEFAULT_ANNOTATION_DATA_NAME="check";

    /**
     * Converts a health-check response to a builder.
     * @param response Health-check response.
     * @return Created builder.
     */
    public static Builder toBuilder(HealthCheckResponse response) {
        return Builder.fromResponse(response);
    }

    /**
     * Creates a health-check response.
     * @param name Name of response.
     * @param status Status of response.
     * @param data Data associated with response.
     * @return Created response.
     */
    @SuppressWarnings("unused")
    @lombok.Builder(builderClassName="Builder")
    private static HealthCheckResponse createHealthCheckResponse(String name,
                                                                 HealthCheckResponse.Status status,
                                                                 @lombok.Singular("data") Map<String,Object> data) {
        return new HealthCheckResponse(name,status,Optional.ofNullable(data));
    }

    /**
     * Builder of {@link HealthCheckResponse} instances.
     * <p>
     *     The presence of this is a reaction against the original MicroProfile Health {@link HealthCheckResponseBuilder},
     *     which is a bit too blunt and without nuances in its way of setting data.
     * </p>
     * <p>
     *     Note that is actually extends and implements the original MicroProfile Health {@link HealthCheckResponseBuilder}.
     * </p>
     */
    @SuppressWarnings({"java:S1068","FieldMayBeFinal","FieldCanBeLocal","unused","java:S1450"})
    public static class Builder extends HealthCheckResponseBuilder {
        /**
         * Name of health-check response.
         */
        private String name;

        /**
         * Status of health-check response.
         */
        private HealthCheckResponse.Status status;

        /**
         * Data associated with health-check response.
         */
        private Map<String,Object> data=new HashMap<>();

        /**
         * Sets the response status.
         * @param status Status.
         * @return Builder.
         */
        public Builder status(HealthCheckResponse.Status status) {
            this.status=status;
            return this;
        }

        @Override
        public Builder status(boolean up) {
            return status(up?HealthCheckResponse.Status.UP:HealthCheckResponse.Status.DOWN);
        }

        @Override
        public Builder down() {
            return status(HealthCheckResponse.Status.DOWN);
        }

        @Override
        public Builder up() {
            return status(HealthCheckResponse.Status.UP);
        }

        @Override
        public Builder withData(String key,
                                String value) {
            return data(key,value);
        }

        @Override
        public Builder withData(String key,
                                long value) {
            return data(key,value);
        }

        @Override
        public Builder withData(String key,
                                boolean value) {
            return data(key,value);
        }

        /**
         * Adds simple annotation names to data.
         * @param key Data key.
         * @param annotations Annotations in data value as a textual list of simple names.
         * @return Builder.
         */
        public Builder withAnnotations(String key,
                                       List<Class<? extends Annotation>> annotations) {
            return withData(key,Annotations.toSimpleNames(annotations).toString());  //Yes, keep value a simple string for possible serialization; respect the intentions with the '#withData(...)' methods!
        }

        /**
         * Adds simple annotation names to data.
         * @param clazz Class to cross-reference the existence of allowed annotations.
         * @param key Data key.
         * @param annotations Allowed annotations in data value as a list of simple names.
         *                    Note that the annotations are filtered.
         * @return Builder.
         */
        public Builder withAnnotations(String key,
                                       Class<?> clazz,
                                       List<Class<? extends Annotation>> annotations) {
            return withAnnotations(key,Annotations.filterByPresence(clazz,annotations));
        }

        /**
         * Adds simple health-check annotation names to data.
         * @param annotations Annotations in data value as a list of simple names.
         * @return Builder.
         */
        public Builder withCheckAnnotations(List<Class<? extends Annotation>> annotations) {
            return withAnnotations(DEFAULT_ANNOTATION_DATA_NAME,annotations);
        }

        /**
         * Adds simple health-check annotation names to data.
         * @param clazz Class to cross-reference the existence of allowed annotations.
         * @param annotations Allowed annotations in data value as a list of simple names.
         *                    Note that the annotations are filtered.
         * @return Builder.
         */
        public Builder withCheckAnnotations(Class<?> clazz,
                                            List<Class<? extends Annotation>> annotations) {
            return withAnnotations(DEFAULT_ANNOTATION_DATA_NAME,clazz,annotations);
        }

        /**
         * Adds simple health-check annotation names to data.
         * @param clazz Class to cross-reference the existence of default allowed annotations.
         * @return Builder.
         */
        public Builder withCheckAnnotations(Class<?> clazz) {
            return withAnnotations(DEFAULT_ANNOTATION_DATA_NAME,clazz,DEFAULT_ALLOWED_ANNOTATIONS);
        }

        /**
         * Adds simple health-check annotation names to data.
         * @param healthcheck Object to cross-reference the existence of default allowed annotations.
         * @return Builder.
         */
        public Builder withCheckAnnotations(HealthCheck healthcheck) {
            return withCheckAnnotations(healthcheck.getClass());
        }

        /**
         * Adds simple health-check annotation names to data.
         * @param healthcheck Object to cross-reference the existence of default allowed annotations.
         * @return Builder.
         */
        public Builder withCheckAnnotations(FutureHealthCheck healthcheck) {
            return withCheckAnnotations(healthcheck.getClass());
        }

        /**
         * Creates a builder from a health-check response.
         * @param response Health-check response.
         * @return Created builder.
         */
        public static Builder fromResponse(HealthCheckResponse response) {
            return builder().name(response.getName()).status(response.getStatus()).data(response.getData().orElse(null));
        }

        /**
         * Creates a builder from a health-check response builder.
         * @param builder Health-check response builder.
         * @return Created builder.
         */
        public static Builder fromResponseBuilder(HealthCheckResponseBuilder builder) {
            return fromResponse(builder.build());
        }
    }
}
