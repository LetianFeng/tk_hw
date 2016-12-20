package client;

import java.util.List;

import javax.jms.JMSException;

public interface ClientAPI {
	
	//boolean login(String userName, int avatarNumber) throws JMSException;
	boolean login(String userName, int avatarNumber);
	
	void subscribeTopic(String topicName) throws JMSException;
	
	void unSubscribeTopic(String topicName);
	
	void sendBlog(String blogContent);
	
	List<BlogMessage> getBlogList();
	
	List<String> getSubscriberList();
	
	List<String> getTopicList();
	
}
