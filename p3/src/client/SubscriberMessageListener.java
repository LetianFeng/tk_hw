package client;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SubscriberMessageListener implements MessageListener{
	
	private String user;
	
	public SubscriberMessageListener(String user) {
		this.user = user;
	}
	
	@Override
	public void onMessage(Message msg) {

        TextMessage textMessage = (TextMessage) msg;
        try {
            String text = textMessage.getText();
            if (text.contains(user)) {
            	System.out.println("Own message has been received.");
            	// send gui message directly
            } else {
            	System.out.println("New message has been received.");
        		// send gui notification for new message
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
