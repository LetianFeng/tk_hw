package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MessageFrame extends JFrame{

	private static final long serialVersionUID = -5551595662801733565L;

	public MessageFrame(String message) {
		initialize(message);
	}
	
	public void initialize(String message) {
		this.setName(message);
		this.setBounds(Constant.messageFrameX, Constant.messageFrameY, Constant.messageFrameWidth, Constant.messageFrameHeight);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disposeFrame();
			}
		}
		);
		this.getContentPane().setLayout(null);
		
		System.out.printf("mesage label is: "+message +"\n");
		JLabel notifyLabel = new JLabel(message);
		notifyLabel.setBounds(Constant.messageLabelX, Constant.messageLabelY, Constant.messageLabeWidth, Constant.messageLabelHeight);
		
		JButton btn = new JButton("OK");
		btn.setForeground(Color.BLACK);
		btn.setBackground(Color.LIGHT_GRAY);
		btn.setFont(new Font("Ebrima", Font.PLAIN, 12));
		btn.setBounds(150, 100, 105, 35);
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
