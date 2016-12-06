package gui;

import java.awt.Toolkit;

public class Constants {
	final static int windowWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final static int windowHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	final static int frameX = (int)(windowWidth * 0.1);
	final static int frameY = (int)(windowHeight * 0.05);
	final static int frameWidth = (int)(windowWidth * 0.8);
	final static int frameHeight = (int)(windowHeight * 0.90);
	final static String ad1 = "Hotel 1 is " +
			"located within a 100-year-old building beside Central " +
			"Station. It offers free Wi-Fi, a free full American breakfast and " +
			"soundproofed rooms with free minibar.";
	final static String ad2 = "Hotel 2 offers modern rooms with free minibar " +
			"drinks and free Wi-Fi. It is just steps from Central Station, " +
			"and a 5-minute underground ride from Trade Fair.";
	final static String ad3 = "Hotel 3 is just 400 m from Central Station, this modern " +
			"hotel offers soundproofed rooms with flat-screen TV, daily buffet breakfasts, " +
			"and a private garage.";
}
