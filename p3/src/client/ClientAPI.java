package client;

import java.util.List;

public interface ClientAPI {
	
	/**
	 * login to the micro blog system as userName with the avatarNumber(th) profile picture
	 * 
	 * @param userName the user id to take as blog holder
	 * @param avatarNumber the avatar index of the chosen profile picture
	 * @return
	 */
	boolean login(String userName, int avatarNumber);
	
	/**
	 * follow the topic identified by topicName
	 * 
	 * @param topicName the topic to follow
	 */
	void subscribeTopic(String topicName);
	
	/**
	 * follow the user identified by userName
	 * 
	 * @param userName the user id to follow
	 */
	void followPerson(String userName);
	
	/**
	 * unfollow the topic identified by topicName
	 * 
	 * @param topicName the topic to unfollow
	 */
	void unSubscribeTopic(String topicName);
	
	/**
	 * unfollow the user identified by userName
	 * 
	 * @param userName the user id to unfollow
	 */
	void unFollowPerson(String userName);
	
	/**
	 * send the post content over to publishers related to relevant topics
	 * 
	 * @param blogContent the plain text of a post
	 */
	void sendBlog(String blogContent);
	
	/**
	 * get a list of unread posts and push to GUI for rendering
	 */
	void showBlogList();
	
	/**
	 * @return list containing subscriptions including both users and topics
	 */
	List<String> getSubscriberList();
	
	/**
	 * @return list containing users and topics that once appeared in posts received
	 */
	List<String> getTopicList();
	
	/**
	 * @param prefix ClientConfig.USER_PREFIX or ClientConfig.TOPIC_PREFIX
	 * @return list containing users or topics depending on the prefix passed in
	 */
	List<TopicMgtItem> getTopicManagementList(String prefix);
	
}
