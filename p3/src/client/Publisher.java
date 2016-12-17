package client;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Publisher {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(Publisher.class);

    private String clientId;
    private Connection connection;
    private Session session;
    private Map<String, MessageProducer> producers = new HashMap<String, MessageProducer>();
    
    public Publisher(String user, String url) throws JMSException {
    	
    	this.clientId = user;
		String host = (url == null || url.isEmpty()) ? ClientConfig.DEFAULT_BROKER_URL : url;
    	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
    	this.connection = connectionFactory.createConnection();
    	this.connection.setClientID(user+"_1"); 	
    	this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	System.out.println("fine before adding producer");
    	addProducer(this.clientId, false);
    	addProducer(ClientConfig.DEFAULT_PUBLIC_CHANNEL, true);
    }

    public void addProducer(String topic, boolean isTopic) throws JMSException {
    	
    	topic = (isTopic ? ClientConfig.TOPIC_PREFIX : ClientConfig.USER_PREFEX) + topic;
        Topic t = this.session.createTopic(topic);
        MessageProducer mp = this.session.createProducer(t);
        this.producers.put(topic, mp);
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }

    public void publishMessage(String msg, String topic) throws JMSException {

        TextMessage textMessage = session.createTextMessage(msg);
        MessageProducer mp = getProducer(topic);
        if (mp != null) {
        	mp.send(textMessage);
        } else {
        	System.out.println("no such producer: " + topic);
        }
    }
    
    public MessageProducer getProducer(String topic) {
    	if (this.producers.containsKey(topic)) {
    		return this.producers.get(topic);
    	} else {
    		return null;
    	}
    }
}