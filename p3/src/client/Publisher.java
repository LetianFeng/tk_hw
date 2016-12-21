package client;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {

    private String clientId;
    private Connection connection;
    private Session session;
    private Map<String, MessageProducer> producers = new HashMap<String, MessageProducer>();
    
    public Publisher(String user, String url) throws JMSException {
    	
    	this.clientId = user;
		String host = (url == null || url.isEmpty()) ? ClientConfig.DEFAULT_BROKER_URL : url;
    	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(host);
    	this.connection = connectionFactory.createConnection();
    	this.connection.setClientID(user.toUpperCase() + "publish"); 	
    	this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    	addProducer(this.clientId.toUpperCase(), false);
    	addProducer(ClientConfig.DEFAULT_PUBLIC_CHANNEL, true);
    }

    public MessageProducer addProducer(String topic, boolean isTopic) throws JMSException {
    	
        Pattern pattern = Pattern.compile("[T,F]\\..+");
        Matcher matcher = pattern.matcher(topic);
    	if (!matcher.matches()) {
        	topic = (isTopic ? ClientConfig.TOPIC_PREFIX : ClientConfig.USER_PREFEX) + topic.toUpperCase();
    	}
    	if(!producers.containsKey(topic)) {
	        Topic t = this.session.createTopic(topic);
	        MessageProducer mp = this.session.createProducer(t);
	        this.producers.put(topic, mp);
	        System.out.println("topic"+mp.toString()+ " unsubscribed");
	        return mp;
    	}
    	else
    		return null;
    }

    public void closeConnection() throws JMSException {
    	
        connection.close();
    }

    public void publishMessage(String msg, String topic) throws JMSException {

        TextMessage textMessage = session.createTextMessage(msg);
        MessageProducer mp = getProducer(topic.toUpperCase());
        if (mp != null) {
        	mp.send(textMessage);
        } else {
        	MessageProducer mp_t = addProducer(topic.toUpperCase(), true);
        	if(mp_t != null)
        		mp_t.send(textMessage);
        }
    }
    
    public MessageProducer getProducer(String topic) {
    	
    	topic = topic.toUpperCase();
    	if (this.producers.containsKey(topic)) {
    		return this.producers.get(topic);
    	} else {
    		return null;
    	}
    }
}