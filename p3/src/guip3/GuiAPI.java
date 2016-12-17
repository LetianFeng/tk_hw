package guip3;

import client.BlogMessage;

public interface GuiAPI {
	void showNotification(String notification);
	
	void showBlog(BlogMessage blogMessage);
	
	//void showException(Exception e);
}
