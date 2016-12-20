package client;

import java.util.List;

import javax.jms.JMSException;

public interface ClientAPI {
	
	//boolean login(String userName, int avatarNumber) throws JMSException;
	boolean login(String userName, int avatarNumber);
	
	void subscribeTopic(String topicName);
	
	void followPerson(String userName);
	
	void unSubscribeTopic(String topicName);
	
	void unFollowPerson(String userName);
	
	void sendBlog(String blogContent);
	
	void showBlogList();
	
	List<String> getSubscriberList();
	
	List<String> getTopicList();
	
	/**
	 * @param prefix ClientConfig.USER_PREFIX or ClientConfig.TOPIC_PREFIX
	 * @return list containing topics with that prefix
	 */
	List<TopicMgtItem> getTopicManagementList(String prefix);
	
}
