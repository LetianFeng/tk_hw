package client;

import java.util.Date;

public class BlogMessage {
	
	private String content;
	private Date date;
	private String sender;
	private int avatar;
	
	public BlogMessage(String c, Date d, String s, int a) {
		this.content = c;
		this.date = d;
		this.sender = s;
		this.avatar = a;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public String getSender() {
		return this.sender;
	}
	
	public int getAvatar() {
		return this.avatar;
	}
}
