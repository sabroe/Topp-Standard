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

package com.yelstream.topp.standard.microprofile.health.value;

//import com.yelstream.topp.standard.microprofile.health.HealthCheckResponses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
//import org.eclipse.microprofile.health.HealthCheckResponse;

import java.util.Map;

/**
 * Health-check response values applicable for serialization of {@link HealthCheckResponse} instances.
 * <p>
 *     The original {@link HealthCheckResponse} is a mess of responsibilities
 *     since it does not carry a clean set of values for a response object.
 * </p>
 * <p>
 *     This is immutable.
 * </p>
 * <p>
 *     If this needs to be changed,
 *     then consider using this to create a {@link HealthCheckResponses.Builder}.
 *     build a new {@link HealthCheckResponse},
 *     and convert this into a new {@link HealthCheckResponseValue}.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2024-06-23
 */
@Getter
@ToString
@AllArgsConstructor  //Yes, keep public constructor, no fiddling, make it easy for other APIs to create this by implicit inspection and normal constructors!
public class HealthCheckResponseValue {
    /**
     * Name of health-check response.
     */
    private final String name;

    /**
     * Status of health-check response.
     */
//    private final HealthCheckResponse.Status status;
    private final String status;  //TO-DO: Enum; UP, DOWN, possibly something more!

    /**
     * Data of health-check response.
     */
    private final Map<String,String> data;

    /**
     * Converts this to a health-check response.
     * @return Created health-check response.
     */
/*
    public HealthCheckResponse toHealthCheckResponse() {
        return new HealthCheckResponse(name,status,Optional.ofNullable(Maps.convertMapValueToObject(this.data)));
    }
*/

    /**
     * Converts a health-check response to a health-check response value.
     * @return Created health-check response value.
     */
/*
    public static HealthCheckResponseValue fromHealthCheckResponse(HealthCheckResponse response) {
        return new HealthCheckResponseValue(response.getName(),response.getStatus(),Maps.convertMapValueToString(Optionals.get(response.getData())));
    }
*/
}
