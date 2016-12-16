package guip3;

import java.util.Date;
import java.util.List;

public interface GuiAPI {
	void showNotification(String notification);
	
	void showBlog(String blogContent, Date sendDate, String senderName, int avatarNumber);
	
	void showException(Exception e);
}
