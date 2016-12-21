package guip3;

import client.ClientConfig;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class LoginFrame  extends JFrame{
	private String title = "Welcome to fantastic MicroBlog!";
	private JTextField userNameTextField;
    private JButton btnAvatar1;
    private JButton btnAvatar2;
    private JButton btnAvatar3;
    private JButton btnAvatar4;
	private JFrame loginFrame;
    private int avatarId = 1;

	final static private Color grey = Color.LIGHT_GRAY;
    
	//main function of the project start here
	 public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    LoginFrame loginframe = new LoginFrame();
	                    loginframe.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }

	public LoginFrame() {
		initialize();
		loginFrame = this;
	}
	
	private void initialize() {
		this.setTitle(title);
		this.setBounds(Constant.loginFrameX, Constant.loginFrameY, Constant.loginFrameWidth, Constant.loginFrameHeight);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.getContentPane().setLayout(null);

		JLabel userNameLabel = new JLabel("User Name:");
		userNameLabel.setBounds(100, 125, 81, 23);
		this.getContentPane().add(userNameLabel);

		JLabel avatarLabel = new JLabel("Avatar:");
		avatarLabel.setBounds(100, 50, 81, 23);
		this.getContentPane().add(avatarLabel);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(200, 125, 105, 21);
		userNameTextField.setColumns(10);
		this.getContentPane().add(userNameTextField);
		
		btnAvatar1 = new JButton();
		btnAvatar1.setBounds(200, 50, 50, 50);
        btnAvatar1.setIcon(new ImageIcon(this.getClass().getResource(Constant.bigAvatar.get(1))));

		btnAvatar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				avatarId = 1;
				updateAvatarBackground();
			}
		});	
        this.getContentPane().add(btnAvatar1);
        
		btnAvatar2 = new JButton();
		btnAvatar2.setBounds(255, 50, 50, 50);
        btnAvatar2.setIcon(new ImageIcon(this.getClass().getResource(Constant.bigAvatar.get(2))));
        
		btnAvatar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				avatarId = 2;
				updateAvatarBackground();
			}
		});
        this.getContentPane().add(btnAvatar2);
        
		btnAvatar3 = new JButton();
		btnAvatar3.setBounds(310, 50, 50, 50);
        btnAvatar3.setIcon(new ImageIcon(this.getClass().getResource(Constant.bigAvatar.get(3))));
        
		btnAvatar3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				avatarId = 3;
				updateAvatarBackground();
			}
		});
        this.getContentPane().add(btnAvatar3);
        
		btnAvatar4 = new JButton();
		btnAvatar4.setBounds(365, 50, 50, 50);
        btnAvatar4.setIcon(new ImageIcon(this.getClass().getResource(Constant.bigAvatar.get(4))));
        
		btnAvatar4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			 	avatarId = 4;
				updateAvatarBackground();
			}
		});	
        this.getContentPane().add(btnAvatar4);

		JButton btnLogin = new JButton("Log In");
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Ebrima", Font.PLAIN, 12));
		btnLogin.setBounds(200, 170, 105, 35);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = userNameTextField.getText();

				if(userName.equals("")) {
					System.out.println("invalid user name!");
					JOptionPane.showMessageDialog(null, "invalid user name!");
				}
				else {
					WeiboFrame weiboframe = new WeiboFrame(avatarId, userName);
					int status_code = weiboframe.login_initial();
					if(status_code == ClientConfig.NO_ERROR) {
						weiboframe.setVisible(true);
						loginFrame.dispose();
					}
					else {
						weiboframe.dispose();
						switch (status_code) {
							case ClientConfig.INVALID_USER_NAME_ERROR:
								JOptionPane.showMessageDialog(null, "duplicate user name!");
								break;
							case ClientConfig.ACTIVEMQ_NOT_START_UP_ERROR:
								JOptionPane.showMessageDialog(null, "can't connect to server!");
								break;
							case ClientConfig.UNKNOWN_ERROR:
								JOptionPane.showMessageDialog(null, "unknown error occurs!");
								break;
						}
					}
				}
			}
		});

		this.getContentPane().add(btnLogin);
		this.setResizable(false);
	}

	private void updateAvatarBackground() {
		btnAvatar1.setBackground(avatarId==1 ? grey : null);
		btnAvatar2.setBackground(avatarId==2 ? grey : null);
		btnAvatar3.setBackground(avatarId==3 ? grey : null);
		btnAvatar4.setBackground(avatarId==4 ? grey : null);
	}
}


