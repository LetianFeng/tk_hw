package client;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;

public interface ClientAPI {
	
	boolean login(String userName, int avatarNumber) throws JMSException;
	
	void subscribeTopic(String topicName);
	
	void unSubscribeTopic(String topicName);
	
	void sendBlog(String blogContent);
	
	List<String> getSubscriberList();
	
	List<String> getTopicList();
	
}
