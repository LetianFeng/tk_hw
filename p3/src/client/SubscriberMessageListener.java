package client;

import guip3.GuiAPI;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SubscriberMessageListener implements MessageListener{
	
	private String topic;
	private String user;
	private Client client;
	
	public SubscriberMessageListener(String clientId, String topic, Client client) {
		this.topic = topic;
		this.user = clientId;
		this.client = client;
	}

	public void onMessage(Message msg) {

        TextMessage textMessage = (TextMessage) msg;
        try {
            String text = textMessage.getText();
            BlogMessage bm = MessageUtil.jsonToBlog(text);
            if (bm.getSender().equals(this.user)) {
            	System.out.println("Own message has been received for user: " + this.user);
            	// send gui message directly
            	client.sendbackOwnMessage(bm);
            } else {
            	System.out.println("New message has been received from " + bm.getSender());
            	System.out.println("this user is " + this.user);
        		// send gui notification for new message
            	// store current message in stack
            	client.storeNewBlog(bm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public String getTopic() {
		return this.topic;
	}
}
