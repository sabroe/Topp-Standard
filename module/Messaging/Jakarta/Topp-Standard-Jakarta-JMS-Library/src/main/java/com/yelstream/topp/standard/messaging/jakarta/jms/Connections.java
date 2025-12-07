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
import lombok.experimental.UtilityClass;

/**
 *
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-03
 */
@UtilityClass
public class Connections {

    public interface ConnectionCreator extends Factory<Connection,JMSException> {}

    public interface ConnectionConsumer extends ConsumerWithException<Connection,JMSException> {}

    public static Connection createConnection(ConnectionFactory connectionFactory) throws JMSException {
        return connectionFactory.createConnection();
    }

    public static void run(ConnectionCreator creator,
                           ConnectionConsumer consumer) throws JMSException {
        try (Connection connection=creator.create()) {
            connection.start();
            consumer.accept(connection);
        }
    }

    public static void run(ConnectionFactory factory,
                           ConnectionConsumer consumer) throws JMSException {
        ConnectionCreator creator=()->createConnection(factory);
        run(creator,consumer);
    }
}
