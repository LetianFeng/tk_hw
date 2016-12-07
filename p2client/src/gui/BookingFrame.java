package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class BookingFrame extends JFrame{

	private String title;

	public BookingFrame(String clientName) {
		this.title = "Booking Room " + clientName;
		initialize();
	}
	
	private void initialize() {
		this.setTitle(title);
		this.setBounds(Constants.frameX, Constants.frameY, Constants.frameWidth, Constants.frameHeight);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}
		);
	}
}
