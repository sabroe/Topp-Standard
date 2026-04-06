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

package com.yelstream.topp.standard.logging.slf4j.spi.logger.event.consume;

import com.yelstream.topp.standard.logging.slf4j.Loggers;
import com.yelstream.topp.standard.logging.slf4j.spi.event.FixedLoggingEvent;
import com.yelstream.topp.standard.logging.slf4j.spi.event.LoggingEvents;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.bind.EventBinding;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.event.bind.EventBindings;
import com.yelstream.topp.standard.logging.slf4j.spi.logger.route.LoggerRouting;
import lombok.Singular;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.event.LoggingEvent;
import org.slf4j.spi.MDCAdapter;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Utilities for instances of {@link EventConsumer}.
 *
 * @author Morten Sabroe Mortensen
 * @version 1.0
 * @since 2026-03-29
 */
@UtilityClass
public class EventConsumers {
    /**
     *
     */
    public static EventConsumer create(Logger logger) {
        return event->{
            Loggers.logEvent(logger,event);
        };
    }

    /**
     *
     */
    public static EventConsumer createFromLogger(Supplier<Logger> loggerSupplier) {
        return event->{
            Logger logger=loggerSupplier.get();
            Loggers.logEvent(logger,event);
        };
    }

    /**
     *
     */
    public static EventConsumer create(LoggerRouting loggerRouting) {
        return event->{
            Logger logger = loggerRouting.target(event.getLevel(), event.getMarkers());
            Loggers.logEvent(logger,event);
        };
    }

    /**
     *
     */
    public static EventConsumer create(Function<LoggingEvent,Logger> loggerResolver) {  //TODO: Create 'LoggerResolver' object!
        return create(loggerResolver,EventBindings.DEFAULT_EVENT_BINDING);
    }

    /**
     *
     */
    public static EventConsumer create(Function<LoggingEvent,Logger> loggerResolver,
                                       EventBinding eventBinding) {
        return event->{
            Logger logger=loggerResolver.apply(event);
            //TODO: Test for null-logger?
            boolean status=eventBinding.log(event,logger);
            if (!status) {
                throw new IllegalStateException("Failure to log event!");  //TODO: Identify event?
            }
        };
    }

    /**
     *
     */
    public static EventConsumer create(List<Consumer<LoggingEvent>> capturers,
                                       List<UnaryOperator<LoggingEvent>> transformers,
                                       EventConsumer eventConsumer) {
        return event->{
            if (capturers!=null) {
                for(Consumer<LoggingEvent> capturer:capturers) {
                    capturer.accept(event);
                }
            }
            if (transformers!=null) {
                for(UnaryOperator<LoggingEvent> transformer:transformers) {
                    event=transformer.apply(event);
                }
            }
            eventConsumer.log(event);
        };
    }

    /**
     *
     */
    public static EventConsumer create(List<Consumer<LoggingEvent>> capturers,
                                       List<UnaryOperator<LoggingEvent>> transformers,
                                       Function<LoggingEvent,Logger> loggerResolver,
                                       EventBinding eventBinding) {
        EventConsumer eventConsumer=create(loggerResolver,eventBinding);
        return create(capturers,transformers,eventConsumer);
    }

    /**
     *
     */
    @SuppressWarnings({"unused"})
    @lombok.Builder(builderClassName = "Builder")
    private static EventConsumer createByBuilder(@Singular List<Consumer<LoggingEvent>> capturers,
                                                 @Singular List<UnaryOperator<LoggingEvent>> transformers,
                                                 Function<LoggingEvent,Logger> loggerResolver,
                                                 EventBinding eventBinding) {
        return create(capturers, transformers, loggerResolver, eventBinding);
    }

    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue","FieldMayBeFinal"})
    public static class Builder {
//        private List<Consumer<LoggingEvent>> capturers;
//        private List<UnaryOperator<LoggingEvent>> transformers;
//        private Function<LoggingEvent,Logger> loggerResolver;
        private EventBinding eventBinding=EventBindings.DEFAULT_EVENT_BINDING;

        public Builder loggerRouter(LoggerRouting loggerRouting) {
            return loggerResolver(loggerRouting::target);
        }
    }



    @lombok.Builder(builderClassName = "Builder2", builderMethodName = "builder2")
    public static EventConsumer create(Function<Supplier<Instant>,Instant> timeMapper,
                                       Supplier<Map<String, String>> contextMapSupplier,
                                       EventConsumer eventConsumer) {
        return event -> {
            FixedLoggingEvent.Builder builder = FixedLoggingEvent.builder().event(event);
            if (timeMapper != null) {
                Instant time=timeMapper.apply(()->LoggingEvents.convertTimestamp(event.getTimeStamp()));
                builder = builder.time(time);
            }
            builder = builder.threadName(Thread.currentThread().getName());
            if (contextMapSupplier != null) {
                Map<String, String> contextMap = contextMapSupplier.get();
                builder = builder.contextMap(contextMap);
            }
            FixedLoggingEvent event2 = builder.build();
            eventConsumer.log(event2);

/*
            String renderedMessage = MessageRenderers.DEFAULT_MESSAGE_RENDERER.render(event2);
            System.out.println(String.format("[%s] %s", event2.getLevel(), renderedMessage));
*/
        };
    }

    public static class Builder2 {
        private Function<Supplier<Instant>,Instant> timeMapper=_->Instant.now();

        public Builder2 timeSupplier(Supplier<Instant> timeSupplier) {
            return timeMapper(_->timeSupplier.get());
        }

        public Builder2 time(Instant time) {
            return timeMapper(_->time);
        }

        public Builder2 mdcAdapter(MDCAdapter mdcAdapter) {
            return contextMapSupplier(mdcAdapter::getCopyOfContextMap);
        }
    }
}
