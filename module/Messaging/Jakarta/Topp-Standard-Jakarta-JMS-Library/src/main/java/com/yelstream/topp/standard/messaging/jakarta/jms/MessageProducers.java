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

import com.yelstream.topp.standard.messaging.jakarta.jms.util.ConsumerWithException;
import com.yelstream.topp.standard.messaging.jakarta.jms.util.Factory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import lombok.experimental.UtilityClass;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-03
 */
@UtilityClass
public class MessageProducers {

    public interface MessageProducerCreator extends Factory<MessageProducer, JMSException> {}

    public interface MessageProducerConsumer extends ConsumerWithException<MessageProducer,JMSException> {}

    public static void produce(MessageProducerCreator creator,
                               MessageProducerConsumer consumer) throws JMSException {
        try (MessageProducer messageProducer=creator.create()) {
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            consumer.accept(messageProducer);
        }
    }
}
