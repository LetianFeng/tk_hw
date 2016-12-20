package client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.JMSException;

import guip3.GuiAPI;

public class Client implements ClientAPI{
	private String userName;
	private int avatarNumber;
	private Publisher publisher;
	private Subscriber subscriber;
	private ArrayList<String> topicList;
	private ArrayList<BlogMessage> messageQueue;
	private GuiAPI gui;
	
	public Client() {
		this.topicList = new ArrayList<String>();
		this.messageQueue = new ArrayList<BlogMessage>();
		//gui = new GuiAPI();
	}
	
	@Override 
	public boolean login(String userName, int avatarNumber) {
		try {
			this.userName = userName;
			this.avatarNumber = avatarNumber;
			this.publisher = new Publisher(userName, null);
			this.subscriber = new Subscriber(userName, null, this);
		} catch (JMSException je) {
			System.out.println("An error has occured during login.");
			return false;
		} catch (Exception e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void subscribeTopic(String topicName) {
		try {
			this.subscriber.subscribe(topicName, true);
			if(!topicList.contains(topicName)) {
				topicList.add(topicName);
			}
		} catch (JMSException je) {
			System.out.println("An error has occured during topic subscription.");
		} catch (Exception e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void unSubscribeTopic(String topicName) {
		try {
			subscriber.unSubscribe(topicName);
		} catch (JMSException je) {
			System.out.println("An error has occured during topic unsubscription.");
		} catch (Exception e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<BlogMessage> getBlogList() {
		//needs modification to refresh topicList
		//return this.subscriber.getBlogList();
		return this.messageQueue;
	}
	
	@Override
	public void sendBlog(String blogContent) {
		try {
			Calendar calendar = Calendar.getInstance();
			Date sendDate = calendar.getTime();
			BlogMessage blog = new BlogMessage(blogContent, sendDate, userName, avatarNumber);
			String blogMsg = MessageUtil.blogToJson(blog);
			ArrayList<String> topics = MessageUtil.parseTopics(blogContent);
			for (String topic : topics) {
				publisher.publishMessage(blogMsg, ClientConfig.TOPIC_PREFIX + topic);
			}
			publisher.publishMessage(blogMsg, ClientConfig.USER_PREFEX + userName);
			gui.showBlog(blog);
		} catch (JMSException je) {
			System.out.println("An error has occured during publishing blog.");
		} catch (Exception e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public ArrayList<String> getSubscriberList() {
		try {
			return this.subscriber.getSubscribedTopics();
		} catch (JMSException je) {
			System.out.println("An error has occured during gettting subscriptions.");
		}
		return new ArrayList<String>();
	}
	
	@Override
	public ArrayList<String> getTopicList() {
		return topicList;
	}
	
	public void addTopic(String topic) {
		this.topicList.add(topic);
	}
	
	public void queueMessage(BlogMessage bm) {
		if (bm != null) {
			this.messageQueue.add(bm);
		}
	}
	
	public void notifyGui() {
		int count = this.messageQueue.size();
		String msg = "You have " + count + " unread messages.";
		this.gui.showNotification(msg);
	}
	
	public Subscriber getSub() {
		return this.subscriber;
	}
	
	public Publisher getPub() {
		return this.publisher;
	}
	
	public String getUser() {
		return this.userName;
	}
	
	public void shutDown() {
		try {
			this.subscriber.closeConnection();
			this.publisher.closeConnection();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
