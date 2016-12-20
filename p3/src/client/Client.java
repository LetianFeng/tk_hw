package client;

import java.util.ArrayList;
import java.util.Calendar;
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
			this.subscriber = new Subscriber(userName, null);
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
		//needs modification to refresh topicList
		//return this.subscriber.getBlogList();
		return this.messageQueue;
	}
	
	@Override
	public void sendBlog(String blogContent) {
		Calendar calendar = Calendar.getInstance();
		Date sendDate = calendar.getTime();
		BlogMessage blog = new BlogMessage(blogContent, sendDate, this.userName, this.avatarNumber);
		ArrayList<String> topics = parseTopics(blogContent);
		//this.publisher.sendBlog(blog, topics);//parse tag as topic
		gui.showBlog(blog);//gui is not implemented, null pointer exception
		
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
	
}
