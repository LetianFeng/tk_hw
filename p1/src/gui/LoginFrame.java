package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import client.GameClientGuiInterface;

public class LoginFrame extends JFrame{
	//components
	private JTextField textUsername;
    private JLabel lbUsername;
    private JButton btnLogin;
    private JButton btnCancel;
    private JLabel lblH;
	
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
				//client.logout();
				System.exit(0);
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

		
		btnLogin = new JButton("Log in");
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Ebrima", Font.PLAIN, 12));
		btnLogin.setBounds(152, 158, 105, 35);
		
		lblH = new JLabel("");
	    Image img = new ImageIcon(this.getClass().getResource("Minion-Hello-icon.png")).getImage();
	    lblH.setIcon(new ImageIcon(img));
		lblH.setBounds(32, 43, 133, 128);
		this.getContentPane().add(lblH);
		
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
		btnCancel.setBounds(252, 158, 105, 35);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//client.logout();
				System.exit(0);
			}
		});
		this.getContentPane().add(btnCancel);
		this.setVisible(true);
	}
}
