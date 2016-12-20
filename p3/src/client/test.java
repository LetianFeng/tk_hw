package client;

import java.awt.EventQueue;

import javax.jms.JMSException;

import guip3.LoginFrame;

public class test {
	
	
	public static void main(String[] args) throws JMSException, InterruptedException {
        
		Client client = new Client();
		client.login("kecen", 1);
		client.subscribeTopic("test");
		client.subscribeTopic("test2");
		
		Thread.sleep(1000);
		
		String blog = "{\"content\":\"first #test# blog\", \"date\": \"2016-12-25 12:59:59\", \"sender\": \"kecen\", \"avatar\": \"1\"}";
		String blogMsg = "Hello #test# world";
		String blogMsg2 = "Hello #test2# aa #test# world";
		client.sendBlog(blogMsg);
		
		Thread.sleep(3000);
		System.out.println("topics " + client.getTopicList());
		System.out.println("subscriptions " + client.getSubscriberList());
		
		Client client2 = new Client();
		client2.login("oscar", 2);
		client2.sendBlog(blogMsg);
		
		Thread.sleep(2000);
		
		client2.subscribeTopic("test2");
		client2.followPerson("kecen");
		client.sendBlog(blogMsg2);
		client.sendBlog("Random post.");
		client2.sendBlog(blogMsg2);
		
		Thread.sleep(2000);
		for (TopicMgtItem item : client2.getTopicManagementList(ClientConfig.USER_PREFEX)) {
			System.out.println("Topic Mgt Item (user): " + item.toString());
		}
		for (TopicMgtItem item : client2.getTopicManagementList(ClientConfig.TOPIC_PREFIX)) {
			System.out.println("Topic Mgt Item (topic): " + item.toString());
		}
		
		for (BlogMessage msg : client.getBlogList()) {
			System.out.println("[Client] " + MessageUtil.blogToJson(msg));
		}
		for (BlogMessage msg : client2.getBlogList()) {
			System.out.println("[Client2] " + MessageUtil.blogToJson(msg));
		}
		System.out.println("subscriptions b4" + client2.getSubscriberList());
		
		client2.unFollowPerson("kecen");
		client2.unSubscribeTopic("test2");
		client.sendBlog("client random post");
		
		//System.out.println("subscriptions after" + client2.getSubscriberList());
		
		Thread.sleep(2000);
		for (BlogMessage msg : client2.getBlogList()) {
			System.out.println("[Client2] " + MessageUtil.blogToJson(msg));
		}
		
		client.shutDown();
		client2.shutDown();
    }
}
