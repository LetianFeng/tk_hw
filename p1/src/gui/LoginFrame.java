package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import client.GameClientGuiInterface;

public class LoginFrame extends JFrame{
	//components
	private JTextField textUsername;
    private JPasswordField textPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
	
    private GameClientGuiInterface client;

	public LoginFrame(GameClientGuiInterface client) {
		this.client = client;
		initialize();
	}
	
	protected void initialize() {
		this.setName("loginFrame");
		this.setBounds(Constant.loginFrameX, Constant.loginFrameY, Constant.loinFrameLength, Constant.loinFrameWidth);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				client.logout();
			}
		}
		);
		this.getContentPane().setLayout(null);
		
		lbUsername = new JLabel("UserName:");
		lbUsername.setBounds(175, 64, 81, 23);
		this.getContentPane().add(lbUsername);
		
		textUsername = new JTextField();
		textUsername.setBounds(266, 65, 105, 21);
		textUsername.setColumns(10);
		this.getContentPane().add(textUsername);
		
		lbPassword = new JLabel("Password:");
		lbPassword.setBounds(175, 97, 81, 22);
		this.getContentPane().add(lbPassword);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(266, 100, 105, 24);
		textPassword.setColumns(10);
		this.getContentPane().add(textPassword);
		
		btnLogin = new JButton("Log in");
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Ebrima", Font.PLAIN, 12));
		btnLogin.setBounds(122, 158, 105, 35);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = textUsername.getText();
				if(!client.login(userName)) {
					String msg = "duplicate user name";
					MessageFrame msgFrame = new MessageFrame(msg);
					System.out.printf("username "+userName+" invalid\n");
				}
			}
		});
		
		this.getContentPane().add(btnLogin);
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setFont(new Font("Ebrima", Font.PLAIN, 12));
		btnCancel.setBounds(222, 158, 105, 35);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.logout();
			}
		});
		this.getContentPane().add(btnCancel);
		this.setVisible(true);
	}
}
