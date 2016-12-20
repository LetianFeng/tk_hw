package client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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
		//gui = new GUI();
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
		}
	}
	
	@Override
	public void unSubscribeTopic(String topicName) {
		try {
			this.subscriber.unSubscribe(topicName);
		} catch (JMSException je) {
			System.out.println("An error has occured during topic unsubscription.");
		} catch (Exception e) {
			System.out.println("An error has occured.");
		}
	}
	
	@Override
	public ArrayList<BlogMessage> getBlogList() {
		return this.messageQueue;
	}
	
	@Override
	public void sendBlog(String blogContent) {
		Calendar calendar = Calendar.getInstance();
		Date sendDate = calendar.getTime();
		BlogMessage bm = new BlogMessage(blogContent, sendDate, this.userName, this.avatarNumber);
		ArrayList<String> topics = parseTopics(blogContent);
		for(String topicName : topics) {
			try {
				this.publisher.publishMessage(MessageUtil.blogToJson(bm), topicName);
				if(!topicList.contains(topicName))
					topicList.add(topicName);
			} catch (JMSException e) {
				System.out.println("An error has occured during publish new blog.");
			}
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
	
	private ArrayList<String> parseTopics(String blogContent) {
		//needs modification of whole function body
		ArrayList<String> topics = new ArrayList<String>();
		topics.add("News");
		return topics;
	}
	
	public void storeNewBlog(BlogMessage bm) {
		this.messageQueue.add(bm);
		Collections.sort(messageQueue, new Comparator<BlogMessage>() {
			public int compare(BlogMessage bm1, BlogMessage bm2) {
				Date date1 = bm1.getDate();
			    Date date2 = bm2.getDate();
			    return date1.compareTo(date2);
			}
		});
		//gui.showNotification(this.messageQueue.size()+ " unread blogs");
		System.out.println(this.messageQueue.size()+ " unread blogs");
	}
	
	public void sendbackOwnMessage(BlogMessage bm) {
		gui.showBlog(bm);//gui is not implemented, null pointer exception
	}
	
}
