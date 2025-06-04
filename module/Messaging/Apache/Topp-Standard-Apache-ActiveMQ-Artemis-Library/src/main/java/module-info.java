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

/**
 * Topp Standard Apache ActiveMQ Artemis Library addressing basics of Apache ActiveMQ Artemis.
 * <p>
 *     Note that ActiveMQ Artemis is not fully JPMS-compliant, as of 2.41.0/2025-06-04!
 *     <br/>
 *     For more information, visit the <a href="https://activemq.apache.org/">Apache ActiveMQ Website</a>.
 * </p>
 *
 * @author Morten Sabroe Mortensen
 * @since 2025-06-03
 */
module com.yelstream.topp.standard.messaging.apache.activemq.artemis {
    requires static lombok;
    requires org.slf4j;
    requires java.naming;
//Note: Not for jakarta.jms-api:2.0.3; comes later!    requires jakarta.messaging;
    requires artemis.jms.client;
    exports com.yelstream.topp.standard.messaging.apache.activemq.artemis.jms.client;
}
