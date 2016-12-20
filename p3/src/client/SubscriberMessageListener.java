package client;

import java.util.ArrayList;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SubscriberMessageListener implements MessageListener{
	
	private String topic;
	private Client client;
	
	public SubscriberMessageListener(String topic, Client client) {
		this.topic = topic;
		this.client = client;
	}

	public void onMessage(Message msg) {

        TextMessage textMessage = (TextMessage) msg;
        try {
            String text = textMessage.getText();
            BlogMessage bm = MessageUtil.jsonToBlog(text);
            if (!bm.getSender().equals(client.getUser())) {
            	this.client.queueMessage(bm);
            	this.client.notifyGui();
            }
            
            ArrayList<String> topics = MessageUtil.parseTopics(text);
            for (String topic : topics) {
            	client.addTopic(ClientConfig.TOPIC_PREFIX + topic.toUpperCase());
            }
            client.addTopic(ClientConfig.USER_PREFEX + bm.getSender().toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public String getTopic() {
		
		return this.topic;
	}
}
