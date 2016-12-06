package gui;

import java.awt.Toolkit;

public class Constants {
	final static int windowWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final static int windowHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	final static int frameX = (int)(windowWidth * 0.1);
	final static int frameY = (int)(windowHeight * 0.05);
	final static int frameWidth = (int)(windowWidth * 0.8);
	final static int frameHeight = (int)(windowHeight * 0.90);
}
