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
import com.yelstream.topp.standard.util.function.ex.FunctionWithException;
import jakarta.jms.Connection;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import lombok.experimental.UtilityClass;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-17
 */
@UtilityClass
public class Messages {

    public static <M extends Message,
                   N extends Message> Sender replyTo(Connection connection,
                                                     M receivedMessage,
                                                     FunctionWithException<Session,N,JMSException> responseMessageCreator,
                                                     BiConsumerWithException<M,N,JMSException> responseMessageDecorator) throws JMSException {
        Destination replyToDestination = receivedMessage.getJMSReplyTo();

        if (replyToDestination == null) {
            return null;
        }

        try (Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE)) {
            try (MessageProducer producer=session.createProducer(replyToDestination)) {

                N responseMessage=responseMessageCreator.apply(session);

                if (responseMessageDecorator==null) {
                    responseMessage.setJMSCorrelationID(receivedMessage.getJMSMessageID());
                } else {
                    responseMessageDecorator.accept(receivedMessage,responseMessage);
                }

                return Senders.builder().producer(producer).build();
            }
        }
    }

    public static Sender replyTo(Connection connection,
                                 TextMessage receivedMessage,
                                 String responseText) throws JMSException {
        return replyTo(connection,receivedMessage,session->session.createTextMessage(responseText),null);
    }
}
