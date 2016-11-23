package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MessageFrame extends JFrame{
	public MessageFrame(String message) {
		initialize(message);
	}
	
	public void initialize(String message) {
		this.setName(message);
		this.setBounds(300, 300, 500, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		System.out.printf("mesage label is: "+message +"\n");
		JLabel notifyLabel = new JLabel(message);
		notifyLabel.setBounds(20, 20, 400, 20);
		
		JButton btn = new JButton("OK");
		btn.setForeground(Color.BLACK);
		btn.setBackground(Color.LIGHT_GRAY);
		btn.setFont(new Font("Ebrima", Font.PLAIN, 12));
		btn.setBounds(100, 100, 105, 35);
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				disposeFrame();
			}
		});
		
		this.getContentPane().add(notifyLabel);
		this.getContentPane().add(btn);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
	
	private void disposeFrame() {
		this.dispose();
	}
	
}
