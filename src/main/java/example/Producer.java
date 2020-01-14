/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example;

import org.apache.log4j.Logger;
import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;
import java.io.Console;


class Producer {
    private static final Logger LOGGER = Logger.getLogger(Consumer.class);

    public static void main(String[] args) {
        try {
            JmsConnectionFactory factory = new JmsConnectionFactory("amqp://localhost:5672");
            Connection connection = null;
            connection = factory.createConnection("admin", "password");
            connection.start();

            LOGGER.info("Connected successfully");


            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = null;
            if (args.length > 0 && args[0].equalsIgnoreCase("QUEUE")) {
                destination = session.createQueue("MyQueue");
                LOGGER.info("Created queue");
            } else if (args.length > 0 && args[0].equalsIgnoreCase("TOPIC")) {
                destination = session.createTopic("MyTopic");
                LOGGER.info("Created Topic");
            } else {
                System.out.println("Error: You must specify Queue or Topic");
                connection.close();
                System.exit(1);
            }

            MessageProducer producer = session.createProducer(destination);

            Console c = System.console();
            String response;
            do {

                response = c.readLine("Enter message: ");

                TextMessage msg = session.createTextMessage(response);
                producer.send(msg);

            } while (!response.equalsIgnoreCase("SHUTDOWN"));
            connection.close();
            System.exit(1);
            LOGGER.info("shutdown");
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.info(e);
        }
    }
}