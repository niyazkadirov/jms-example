package example;

import javax.jms.JMSException;

public interface ProducerMBean {

    void sendMessage(String message) throws JMSException;
    void closeConnection() throws JMSException;
}
