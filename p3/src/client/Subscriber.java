package client;

import guip3.GuiAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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

    private Client client;
    private String clientId;
    private Connection connection;
    private Session session;
    private Map<String, MessageConsumer> consumers = new HashMap<String, MessageConsumer>();
    
    public Subscriber(String user, String url, Client client) throws JMSException {
    	this.client = client;
		this.clientId = user;
		String host = (url == null || url.isEmpty()) ? ClientConfig.DEFAULT_BROKER_URL : url;
    	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
    	this.connection = connectionFactory.createConnection();
    	this.connection.setClientID(user+"_"+System.currentTimeMillis()); 	
    	this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	this.connection.start();
    	subscribe(ClientConfig.DEFAULT_PUBLIC_CHANNEL, true);
    	subscribe(this.clientId, false);
    	
    	System.out.println("fine after subscriber start");
    }

    public void subscribe(String topic, Boolean isTopic) throws JMSException {
    	topic = (isTopic ? ClientConfig.TOPIC_PREFIX : ClientConfig.USER_PREFEX) + topic;
        Topic t = this.session.createTopic(topic);
        MessageConsumer mc = this.session.createConsumer(t);
        mc.setMessageListener(new SubscriberMessageListener(topic, this.client));
        this.consumers.put(topic, mc);
    }
    
    public void unSubscribe(String topic) throws JMSException {
    	if (this.consumers.containsKey(topic)) {
    		MessageConsumer mc = this.consumers.get(topic);
    		mc.close();
    		this.consumers.remove(mc);
    	}
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }
    
    public ArrayList<String> getSubscribedTopics() throws JMSException {
    	ArrayList<String> list = new ArrayList<String>();
    	Iterator<Entry<String, MessageConsumer>> it = this.consumers.entrySet().iterator();
    	while (it.hasNext()) {
    		MessageConsumer mc = (MessageConsumer)it.next().getValue();
    		SubscriberMessageListener sml = (SubscriberMessageListener)mc.getMessageListener();
    		list.add(sml.getTopic());
    	}
    	return list;
    }

    /*public String getMessage(MessageConsumer mc) throws JMSException {
    	
    	String ret = "";
        Message message = mc.receive(ClientConfig.DEFAULT_TIMEOUT);
        if (message != null) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            LOGGER.debug(clientId + ": received message with text='{}'", text);

            // create greeting
            ret = "Hello " + text + "!";
        } else {
            LOGGER.debug(clientId + ": no message received");
        }
        return ret;
    }*/
}