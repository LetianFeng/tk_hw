package client;

import guip3.GuiAPI;

import java.util.ArrayList;

import javax.jms.JMSException;
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
            //String sender = msg.getJMSDestination().toString();
            //sender = sender.substring(sender.lastIndexOf('.') + 1);
            if (bm.getSender().equals(client.getUser())) {
            	System.out.println("Own message: " + bm.getContent() + " has been received for user: " + client.getUser());
            	// send gui message directly
            	// this has been handled when gui calls client.sendBlog
            } else {
            	System.out.println("New message: " + bm.getContent() + " has been received from " + bm.getSender());
            	System.out.println("this user is " + client.getUser());
            	// store current message in stack
        		// send gui notification for new message
            	this.client.queueMessage(bm);
            	this.client.notifyGui();
            }
            
            ArrayList<String> topics = MessageUtil.parseTopics(text);
            for (String topic : topics) {
            	client.addTopic(ClientConfig.TOPIC_PREFIX + topic);
            }
            client.addTopic(ClientConfig.USER_PREFEX + bm.getSender());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public String getTopic() {
		return this.topic;
	}
}
