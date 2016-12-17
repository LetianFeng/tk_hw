package client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SubscriberMessageListener implements MessageListener{
	
	private String topic;
	private String user;
	
	public SubscriberMessageListener(String clientId, String topic) {
		this.topic = topic;
		this.user = clientId;
	}

	public void onMessage(Message msg) {

        TextMessage textMessage = (TextMessage) msg;
        try {
            String text = textMessage.getText();
            BlogMessage bm = MessageUtil.jsonToBlog(text);
            if (bm.getSender().equals(this.user)) {
            	System.out.println("Own message has been received for user: " + this.user);
            	// send gui message directly
            } else {
            	System.out.println("New message has been received from " + bm.getSender());
            	System.out.println("this user is " + this.user);
        		// send gui notification for new message
            	// store current message in stack
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public String getTopic() {
		return this.topic;
	}
}
