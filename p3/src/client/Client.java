package client;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

public class Client implements ClientAPI{
	private String userName;
	private int avatarNumber;
	private Publisher publisher;
	private Subscriber subscriber;
	private ArrayList<String> subscriberList;
	private ArrayList<String> topicList;
	
	public Client() {
		this.subscriberList = new ArrayList<String>();
		this.topicList = new ArrayList<String>();
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
		this.subscriber.subscribe(topicName, isTopic(topicName));
	}
	
	@Override
	public void unSubscribeTopic(String topicName) {
		
	}
	
	@Override
	public void sendBlog(String blogContent) {
		
	}
	
	private boolean isTopic(String topicName) {
		return true;
	}
}
