package client;

import java.util.Date;
import java.util.List;

public interface ClientAPI {
	
	void logIn(String userName, int avatarNumber);
	
	void subscribeTopic(String topicName);
	
	void unSubscribeTopic(String topicName);
	
	void sendBlog(String blogContent, Date sendDate);
	
	List<Object> getSubscriberList();
	
	List<Object> getTopicList();
	
}
