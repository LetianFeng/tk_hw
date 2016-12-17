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
	private ArrayList<String> subscriberList;
	private ArrayList<String> topicList;
	private GuiAPI gui;
	
	public Client() {
		this.subscriberList = new ArrayList<String>();//needs initialize value (public topics).
		this.topicList = new ArrayList<String>();
		//gui = new GUI();
	}
	
	@Override 
	public boolean login(String userName, int avatarNumber) throws JMSException {
		this.userName = userName;
		this.avatarNumber = avatarNumber;
		this.publisher = new Publisher();
		this.subscriber = new Subscriber(userName, "Sports", null);
		if(publisher != null && subscriber != null)
			return true;
		else
			return false;
	}
	
	@Override
	public void subscribeTopic(String topicName) throws JMSException {
		if(!subscriberList.contains(topicName)) {
			this.subscriber.subscribe(topicName, true);
			subscriberList.add(topicName);
		}
		if(!topicList.contains(topicName)) {
			topicList.add(topicName);
		}
	}
	
	@Override
	public void unSubscribeTopic(String topicName) {
		//this.subscriber.unSubscribeTopic(topicName);
		subscriberList.remove(topicName);
	}
	
	@Override
	public ArrayList<BlogMessage> getBlogList() {
		//needs modification to refresh topicList
		//return this.subscriber.getBlogList();
		return null;
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
		return subscriberList;
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
