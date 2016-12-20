package guip3;

import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class Constant {
	
	final static int windowWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final static int windowHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	
	//login frame properties
	final static int loginFrameX = (int)(windowWidth * 0.35);
	final static int loginFrameY = (int)(windowHeight * 0.2);
	final static int loginFrameWidth = 500;
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

	final static Map<Integer, String> bigAvatar = new HashMap<Integer, String>() {
		{
			put(1, "Avatar1-icon.png");
			put(2, "Avatar2-icon.png");
			put(3, "Avatar3-icon.png");
			put(4, "Avatar4-icon.png");
		}
	};

	final static Map<Integer, String> smallAvatar = new HashMap<Integer, String>() {
		{
			put(1, "Avatar1-icon_small.png");
			put(2, "Avatar2-icon_small.png");
			put(3, "Avatar3-icon_small.png");
			put(4, "Avatar4-icon_small.png");
		}
	};
}
