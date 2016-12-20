package client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Subscriber {

    private Client client;
    private String clientId;
    private Connection connection;
    private Session session;
    private Map<String, MessageConsumer> consumers = new HashMap<String, MessageConsumer>();
    
    public Subscriber(String user, String url, Client client) throws JMSException {
    	
    	this.client = client;
		this.clientId = user;
		String host = (url == null || url.isEmpty()) ? ClientConfig.DEFAULT_BROKER_URL : url;
    	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(host);
    	this.connection = connectionFactory.createConnection();
    	this.connection.setClientID(user+"_"+System.currentTimeMillis()); 	
    	this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	this.connection.start();
    	subscribe(ClientConfig.DEFAULT_PUBLIC_CHANNEL, true);
    	subscribe(this.clientId, false);
    }

    public void subscribe(String topic, Boolean isTopic) throws JMSException {
    	
    	topic = (isTopic ? ClientConfig.TOPIC_PREFIX : ClientConfig.USER_PREFEX) + topic;
        Topic t = this.session.createTopic(topic);
        MessageConsumer mc = this.session.createConsumer(t);
        mc.setMessageListener(new SubscriberMessageListener(topic, this.client));
        this.consumers.put(topic, mc);
    }
    
    public void unSubscribe(String topic, boolean isTopic) throws JMSException {
    	
    	String t = isTopic ? ClientConfig.TOPIC_PREFIX + topic : ClientConfig.USER_PREFEX + topic;
    	if (this.consumers.containsKey(t)) {
    		MessageConsumer mc = this.consumers.get(t);
    		mc.close();
    		this.consumers.remove(t);
    	}
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }
    
    public ArrayList<String> getSubscribedTopics() throws JMSException {
    	
    	ArrayList<String> list = new ArrayList<String>();
    	Iterator<Entry<String, MessageConsumer>> it = this.consumers.entrySet().iterator();
    	while (it.hasNext()) {
    		Entry<String, MessageConsumer> item = it.next();
    		MessageConsumer mc = (MessageConsumer)item.getValue();
    		String name = (String)item.getKey();
    		try {
	    		SubscriberMessageListener sml = (SubscriberMessageListener)mc.getMessageListener();
	    		list.add(sml.getTopic());
    		} catch (Exception ex) {
    			System.out.println("An error occured during getting message listener for " + name);
    			ex.printStackTrace();
    		}
    	}
    	return list;
    }
}