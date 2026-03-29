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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.event.bind;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.LoggingEvent;
import org.slf4j.spi.LoggingEventBuilder;

import java.util.List;

@AllArgsConstructor(staticName = "of")
public class FallbackEventBinding implements EventBinding {
    @Override
    public boolean log(LoggingEvent event,
                       Logger logger) {
        LoggingEventBuilder builder=logger.atLevel(event.getLevel());
        builder=setCause(builder,event.getThrowable());
        builder=addMarkers(builder,event.getMarkers());
        builder=addKeyValuePairs(builder,event.getKeyValuePairs());
        builder.log(event.getMessage(),event.getArgumentArray());
        return true;
    }

    public static LoggingEventBuilder setCause(LoggingEventBuilder builder,
                                               Throwable throwable) {  //TODO: Move to 'LoggingEventBuilders'!
        if (throwable!=null) {
            builder=builder.setCause(throwable);
        }
        return builder;
    }

    public static LoggingEventBuilder addMarkers(LoggingEventBuilder builder,
                                                 List<Marker> markers) {  //TODO: Move to 'LoggingEventBuilders'!
        if (markers!=null) {
            for (Marker marker: markers) {
                builder=builder.addMarker(marker);
            }
        }
        return builder;
    }

    public static LoggingEventBuilder addKeyValuePairs(LoggingEventBuilder builder,
                                                       List<KeyValuePair> keyValuePairs) {  //TODO: Move to 'LoggingEventBuilders'!
        if (keyValuePairs!=null) {
            for (KeyValuePair kv: keyValuePairs) {
                builder=builder.addKeyValue(kv.key, kv.value);
            }
        }
        return builder;
    }
}
