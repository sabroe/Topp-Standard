package com.yelstream.topp.standard.microprofile.health.value;

//import com.yelstream.topp.standard.microprofile.health.HealthCheckResponses;
import com.yelstream.topp.standard.util.Maps;
import com.yelstream.topp.standard.util.Optionals;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
//import org.eclipse.microprofile.health.HealthCheckResponse;

import java.util.Map;
import java.util.Optional;

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
