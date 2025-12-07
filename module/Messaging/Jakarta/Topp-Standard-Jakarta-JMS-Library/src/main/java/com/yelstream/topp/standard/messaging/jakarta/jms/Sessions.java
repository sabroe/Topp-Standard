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
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import lombok.experimental.UtilityClass;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-03
 */
@UtilityClass
public class Sessions {

    public interface SessionCreator extends Factory<Session,JMSException> {}

    public interface SessionConsumer extends ConsumerWithException<Session,JMSException> {}

    public static Session createSession(Connection connection) throws JMSException {
        return connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
    }

    public static void run(SessionCreator creator,
                           SessionConsumer consumer) throws JMSException {
        try (Session session=creator.create()) {
            consumer.accept(session);
        }
    }

    public static void run(Connection connection,
                           SessionConsumer consumer) throws JMSException {
        SessionCreator creator=()->createSession(connection);
        run(creator,consumer);
    }

    public static void run(ConnectionFactory connectionFactory,
                           SessionConsumer consumer) throws JMSException {
        Connections.run(connectionFactory, connection -> run(connection,consumer));
    }
}
