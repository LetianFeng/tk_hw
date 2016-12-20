package guip3;

import client.BlogMessage;

public interface GuiAPI {

	/**
	 * notify GUI with a message, identifying how many unread posts are there
	 * 
	 * @param notification the message to appear in the notification panel
	 */
	void showNotification(String notification);
	
	/**
	 * render blog post in GUI
	 * 
	 * @param blogMessage the BlogMessage object to push to the GUI for rendering
	 */
	void showBlog(BlogMessage blogMessage);
	
}
