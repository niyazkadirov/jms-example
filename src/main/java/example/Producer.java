
package example;

import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.*;
import javax.management.*;
import java.io.Console;
import java.lang.management.ManagementFactory;
import java.util.Arrays;


class Producer implements ProducerMBean {

    private static Connection connection;
    private static Session session;
    private static MessageProducer producer;

    @Override
    public void sendMessage(String message) throws JMSException {
        TextMessage msg = session.createTextMessage(message);
        producer.send(msg);
    }

    @Override
    public void closeConnection() throws JMSException {
        TextMessage msg = session.createTextMessage("shutdown");
        producer.send(msg);
        connection.close();
        System.exit(1);
    }

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, JMSException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("example.mbeans:type=Producer");
        Producer mbean = new Producer();
        mbs.registerMBean(mbean, name);


            JmsConnectionFactory factory = new JmsConnectionFactory("amqp://localhost:5672");
            connection = factory.createConnection("admin", "password");
            connection.start();



            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = null;



            if (args.length > 0 && args[0].equalsIgnoreCase("QUEUE")) {
                destination = session.createQueue("MyQueue");
            } else if (args.length > 0 && args[0].equalsIgnoreCase("TOPIC")) {
                destination = session.createTopic("MyTopic");
            } else {
                System.out.println("Error: You must specify Queue or Topic");
                connection.close();
                System.exit(1);
            }

            producer = session.createProducer(destination);

            Console c = System.console();
            String response;
            do {

                response = c.readLine("Enter message: ");
                TextMessage msg = session.createTextMessage(response);
                producer.send(msg);
                if(false){
                    mbean.sendMessage("");
                    mbean.closeConnection();
                }

            } while (!response.equalsIgnoreCase("SHUTDOWN"));
            connection.close();
            System.exit(1);

    }
}