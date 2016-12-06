package gui;


import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;

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
