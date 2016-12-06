package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class BookingFrame extends JFrame{
	
	public BookingFrame() {
		initialize();
	}
	
	private void initialize() {
		this.setTitle("Booking Room");
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
