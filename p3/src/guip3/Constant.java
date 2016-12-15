package guip3;

import java.awt.Toolkit;

public class Constant {
	
	final static int windowWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final static int windowHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	
	//login frame properties
	final static int loginFrameX = (int)(windowWidth * 0.35);
	final static int loginFrameY = (int)(windowHeight * 0.2);
	final static int loginFrameWidth = 400;
	final static int loginFrameHeight = 300;
	
	//weibo frame properties
	final static int weiboFrameX = (int)(windowWidth * 0.3);
	final static int weiboFrameY = (int)(windowHeight * 0.02);
	final static int weiboFrameWidth = 500;
	final static int weiboFrameHeight = 700;
	
	//setting frame properties
	final static int settingFrameX = (int)(windowWidth * 0.3);
	final static int settingFrameY = (int)(windowHeight * 0.02);
	final static int settingFrameWidth = 500;
	final static int settingFrameHeight = 700;
	
	//icon properties
	final static int minionLabelWidth = 60;
	final static int minionLabelHeight = 60;
	final static String minionIconName = "Minion-Dancing-icon-small.png";
	
	//message label properties
	final static int messageLabeWidth = 300;
	final static int messageLabelHeight = 15;
	final static int messageLabelX = 50;
	final static int messageLabelY = 20;
	
	//notification label properties
	final static int notificationStartX = 650;
	final static int notificationStartY = 20;
	final static int notificationLabelWidth = 250;
	final static int notificationLabelHeight = 15;
	final static int maxNotificationNumber = 20;
}
