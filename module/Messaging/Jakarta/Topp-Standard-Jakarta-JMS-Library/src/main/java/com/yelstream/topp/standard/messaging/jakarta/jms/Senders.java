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

package com.yelstream.topp.standard.messaging.jakarta.jms;

import com.yelstream.topp.standard.util.function.ex.BiConsumerWithException;
import com.yelstream.topp.standard.util.function.ex.ConsumerWithException;
import jakarta.jms.CompletionListener;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageProducer;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Utility addressing instances of {@link Sender}.
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-17
 */
@UtilityClass
public class Senders {

    public static Sender of(MessageProducer producer) {
        Objects.requireNonNull(producer, "Failure to create sender; message producer is not set!");
        return of(producer::send);
    }

    public static Sender ofAsync(MessageProducer producer) {
        Objects.requireNonNull(producer, "Failure to create sender; message producer is not set!");
        return ofAsync(producer::send);
    }

    private static Sender of(ConsumerWithException<Message, JMSException> consumer) {
        Objects.requireNonNull(consumer, "Failure to create sender; message consumer is not set!");
        return message -> {
            CompletableFuture<Void> future = new CompletableFuture<>();
            try {
                consumer.accept(message);
                future.complete(null);
            } catch (JMSException ex) {
                future.completeExceptionally(ex);
            }
            return future;
        };
    }

    private static Sender ofAsync(BiConsumerWithException<Message, CompletionListener, JMSException> consumer) {
        Objects.requireNonNull(consumer, "Failure to create sender; message consumer is not set!");
        return message -> {
            CompletableFuture<Void> future = new CompletableFuture<>();
            CompletionListener completionListener = CompletionListeners.fromFuture(future);
            try {
                consumer.accept(message,completionListener);
            } catch (JMSException ex) {
                future.completeExceptionally(ex);
            }
            return future;
        };
    }

    @lombok.Builder(builderClassName="Builder")
    private static Sender createSenderByBuilder(MessageProducer producer,
                                                boolean sync,
                                                Destination destination,
                                                int deliveryMode,
                                                int priority,
                                                long timeToLive) {
        if (sync) {
            return createSender(producer,destination,deliveryMode,priority,timeToLive);
        } else {
            return createSenderAsync(producer,destination,deliveryMode,priority,timeToLive);
        }
   }

    private static Sender createSender(MessageProducer producer,
                                       Destination destination,
                                       int deliveryMode,
                                       int priority,
                                       long timeToLive) {
        if (destination==null) {
            if (deliveryMode!=Message.DEFAULT_DELIVERY_MODE || priority!=Message.DEFAULT_PRIORITY || timeToLive!=Message.DEFAULT_TIME_TO_LIVE) {
                return of(message -> producer.send(message,deliveryMode,priority,timeToLive));
            } else {
                return of(producer::send);
            }
        } else {
            if (deliveryMode!=Message.DEFAULT_DELIVERY_MODE || priority!=Message.DEFAULT_PRIORITY || timeToLive!=Message.DEFAULT_TIME_TO_LIVE) {
                return of(message -> producer.send(destination,message,deliveryMode,priority,timeToLive));
            } else {
                return of(message -> producer.send(destination,message));
            }
        }
    }

    private static Sender createSenderAsync(MessageProducer producer,
                                            Destination destination,
                                            int deliveryMode,
                                            int priority,
                                            long timeToLive) {
        if (destination==null) {
            if (deliveryMode!=Message.DEFAULT_DELIVERY_MODE || priority!=Message.DEFAULT_PRIORITY || timeToLive!=Message.DEFAULT_TIME_TO_LIVE) {
                return ofAsync((message,completionListener) -> producer.send(message,deliveryMode,priority,timeToLive,completionListener));
            } else {
                return ofAsync(producer::send);
            }
        } else {
            if (deliveryMode!=Message.DEFAULT_DELIVERY_MODE || priority!=Message.DEFAULT_PRIORITY || timeToLive!=Message.DEFAULT_TIME_TO_LIVE) {
                return ofAsync((message, completionListener) -> producer.send(destination,message,deliveryMode,priority,timeToLive,completionListener));
            } else {
                return ofAsync((message, completionListener) -> producer.send(destination,message,completionListener));
            }
        }
    }

    @SuppressWarnings({"java:S1068","java:S1450","unused","FieldCanBeLocal","UnusedReturnValue","FieldMayBeFinal"})
    public static class Builder {
        private Destination destination=null;
        private int deliveryMode=Message.DEFAULT_DELIVERY_MODE;
        private int priority=Message.DEFAULT_PRIORITY;
        private long timeToLive=Message.DEFAULT_TIME_TO_LIVE;
   }
}
