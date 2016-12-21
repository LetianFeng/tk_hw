package client;

import guip3.WeiboFrame;
import guip3.GuiAPI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jms.InvalidClientIDException;
import javax.jms.JMSException;

import org.apache.log4j.BasicConfigurator;

public class Client implements ClientAPI{
	
	private String userName;
	private int avatarNumber;
	private Publisher publisher;
	private Subscriber subscriber;
	private ArrayList<String> topicList;
	private ArrayList<BlogMessage> messageQueue;
	private GuiAPI gui;
	
	public Client() {
		
		BasicConfigurator.configure(); // eliminate warning for log4j
		this.topicList = new ArrayList<String>();
		this.messageQueue = new ArrayList<BlogMessage>();
	}
	
	public Client(WeiboFrame wbFrame) {
		
        this();
		gui = wbFrame;
	}
	
	@Override 
	public int login(String userName, int avatarNumber) {
		
		try {
			this.userName = userName;
			this.avatarNumber = avatarNumber;
			this.publisher = new Publisher(userName, null);
			this.subscriber = new Subscriber(userName, null, this);
		} catch (InvalidClientIDException ue) {
			System.out.println("Duplicate user name.");
			return ClientConfig.INVALID_USER_NAME_ERROR;
		} catch (JMSException je) {
			System.out.println("Activemq doesn't start up.");
			return ClientConfig.ACTIVEMQ_NOT_START_UP_ERROR;
		} catch (Exception e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
			return ClientConfig.UNKNOWN_ERROR;
		}
		return ClientConfig.NO_ERROR;
	}
	
	@Override
	public void subscribeTopic(String topicName) {
		
		try {
			topicName = topicName.toUpperCase();
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
	public void followPerson(String userName) {
		
		try {
			userName = userName.toUpperCase();
			this.subscriber.subscribe(userName, false);
			if(!topicList.contains(userName)) {
				topicList.add(userName);
			}
		} catch (JMSException je) {
			System.out.println("An error has occured during user subscription.");
		} catch (Exception e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void unSubscribeTopic(String topicName) {
		
		try {
			topicName = topicName.toUpperCase();
			subscriber.unSubscribe(topicName, true);
		} catch (JMSException je) {
			System.out.println("An error has occured during topic unsubscription.");
		} catch (Exception e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void unFollowPerson(String userName) {
		
		try {
			userName = userName.toUpperCase();
			subscriber.unSubscribe(userName, false);
		} catch (JMSException je) {
			System.out.println("An error has occured during topic unsubscription.");
		} catch (Exception e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void showBlogList() {

		ArrayList<BlogMessage> list = getBlogList();
		for (BlogMessage bm : list) {
			this.gui.showBlog(bm);
		}
	}
	
	public ArrayList<BlogMessage> getBlogList() {

		ArrayList<BlogMessage> list = new ArrayList<BlogMessage>();
		Set<BlogMessage> hs = new HashSet<>();
		hs.addAll(this.messageQueue);
		list.addAll(hs);
		Collections.sort(list, new Comparator<BlogMessage>() {
			public int compare(BlogMessage bm1, BlogMessage bm2) {
				Date date1 = bm1.getDate();
			    Date date2 = bm2.getDate();
			    return date1.compareTo(date2);
			}
		});
		this.messageQueue.clear();
		return list;
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
				publisher.publishMessage(blogMsg, ClientConfig.TOPIC_PREFIX + topic.toUpperCase());
			}
			publisher.publishMessage(blogMsg, ClientConfig.USER_PREFEX + userName.toUpperCase());
			gui.showBlog(blog);
		} catch (JMSException je) {
			System.out.println("An error has occured during publishing blog.");
			je.printStackTrace();
		} catch (Exception e) {
			System.out.println("An error has occured when show blog.");
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<String> getSubscriberList() {
		
		try {
			return this.subscriber.getSubscribedTopics();
		} catch (JMSException je) {
			System.out.println("An error has occured during gettting subscriptions.");
			je.printStackTrace();
		} catch (Exception e) {
			System.out.println("An error has occured.");
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
	
	@Override
	public ArrayList<String> getTopicList() {
		
		return topicList;
	}
	
	@Override
	public ArrayList<TopicMgtItem> getTopicManagementList(String prefix) {
		
		ArrayList<TopicMgtItem> list = new ArrayList<TopicMgtItem>();
		ArrayList<String> subscriptions = getSubscriberList();
		Set<TopicMgtItem> itemset = new HashSet<TopicMgtItem>();
		for (String sub : subscriptions) {
			if (sub.indexOf(prefix) < 0)
				continue;
			sub = sub.substring(sub.indexOf('.') + 1);
			TopicMgtItem topic = new TopicMgtItem(sub, true);
			itemset.add(topic);
		}
		for (String sub : this.topicList) {
			if (sub.indexOf(prefix) < 0)
				continue;
			sub = sub.substring(sub.indexOf('.') + 1);
			TopicMgtItem topic = new TopicMgtItem(sub, false);
			itemset.add(topic);
		}
		list.addAll(itemset);
		return list;
	}
	
	public void addTopic(String topic) {
		
		topic = topic.toUpperCase();
		if (!topicList.contains(topic))
		this.topicList.add(topic);
	}
	
	public void queueMessage(BlogMessage bm) {
		
		if (bm != null) {
			this.messageQueue.add(bm);
		}
	}
	
	public void notifyGui() {
		
		Set<BlogMessage> hs = new HashSet<>();
		hs.addAll(this.messageQueue);	
		int count = hs.size();
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
