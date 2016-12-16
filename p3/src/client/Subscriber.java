package client;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subscriber {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(Subscriber.class);

    private String clientId;
    private Connection connection;
    private Session session;
    private Map<String, MessageConsumer> consumers;
    
    public Subscriber(String user, String topic, String url) throws JMSException {
		this.clientId = user;
		String host = (url.isEmpty() || url == null) ? ClientConfig.DEFAULT_BROKER_URL : url;
    	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(host);
    	this.connection = connectionFactory.createConnection();
    	this.connection.setClientID(user); 	
    	this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	subscribe(ClientConfig.DEFAULT_PUBLIC_CHANNEL, true);
    	subscribe(this.clientId, false);
    	
    	this.connection.start();
    }

    public void subscribe(String topic, Boolean isTopic) throws JMSException {
    	topic = (isTopic ? ClientConfig.TOPIC_PREFIX : ClientConfig.USER_PREFEX) + topic;
        Topic t = session.createTopic(topic);
        MessageConsumer mc = this.session.createConsumer(t);
        mc.setMessageListener(new SubscriberMessageListener(this.clientId));
        this.consumers.put(topic, mc);
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }

    public String getGreeting(int timeout) throws JMSException {

        String greeting = NO_GREETING;

        // read a message from the topic destination
        Message message = messageConsumer.receive(timeout);

        // check if a message was received
        if (message != null) {
            // cast the message to the correct type
            TextMessage textMessage = (TextMessage) message;

            // retrieve the message content
            String text = textMessage.getText();
            LOGGER.debug(clientId + ": received message with text='{}'", text);

            // create greeting
            greeting = "Hello " + text + "!";
        } else {
            LOGGER.debug(clientId + ": no message received");
        }

        LOGGER.info("greeting={}", greeting);
        return greeting;
    }
}