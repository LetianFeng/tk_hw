package guip3;

import java.util.Date;
import java.util.List;
import client.BlogMessage;

public interface GuiAPI {
	void showNotification(String notification);
	
	void showBlog(List<BlogMessage> blogMessage);
	
	//void showException(Exception e);
}
