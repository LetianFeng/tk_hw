package guip3;

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
	private JTextField textUsername;
    private JLabel username;
    private JButton btnLogin;
    private JButton btnAvatar1;
    private JButton btnAvatar2;
    private JButton btnAvatar3;
    private JButton btnAvatar4;
    private JLabel avatar;
	private JFrame loginFrame;
    public int avatarId = 1;

	final static private Color grey = Color.LIGHT_GRAY;
    
	//main function for test
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
	
	protected void initialize() {
		this.setTitle(title);
		this.setBounds(Constant.loginFrameX, Constant.loginFrameY, Constant.loginFrameWidth, Constant.loginFrameHeight);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.getContentPane().setLayout(null);
		
		username = new JLabel("User Name:");
		username.setBounds(100, 125, 81, 23);
		this.getContentPane().add(username);
		
		avatar = new JLabel("Avatar:");
		avatar.setBounds(100, 50, 81, 23);
		this.getContentPane().add(avatar);
		
		textUsername = new JTextField();
		textUsername.setBounds(200, 125, 105, 21);
		textUsername.setColumns(10);
		this.getContentPane().add(textUsername);
		
		btnAvatar1 = new JButton();
		btnAvatar1.setBounds(200, 50, 50, 50);
		Image btnAvatar1Img = new ImageIcon(this.getClass().getResource(Constant.bigAvatar.get(1))).getImage();
        btnAvatar1.setIcon(new ImageIcon(btnAvatar1Img));

		btnAvatar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				avatarId = 1;
				updateAvatarBackground();
			}
		});	
        this.getContentPane().add(btnAvatar1);
        
		btnAvatar2 = new JButton();
		btnAvatar2.setBounds(255, 50, 50, 50);
		Image btnAvatar2Img = new ImageIcon(this.getClass().getResource(Constant.bigAvatar.get(2))).getImage();
        btnAvatar2.setIcon(new ImageIcon(btnAvatar2Img));
        
		btnAvatar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				avatarId = 2;
				updateAvatarBackground();
			}
		});	
        
        this.getContentPane().add(btnAvatar2);
        
		btnAvatar3 = new JButton();
		btnAvatar3.setBounds(310, 50, 50, 50);
		Image btnAvatar3Img = new ImageIcon(this.getClass().getResource(Constant.bigAvatar.get(3))).getImage();
        btnAvatar3.setIcon(new ImageIcon(btnAvatar3Img));
        
		btnAvatar3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				avatarId = 3;
				updateAvatarBackground();
			}
		});	
		
        this.getContentPane().add(btnAvatar3);
        
		btnAvatar4 = new JButton();
		btnAvatar4.setBounds(365, 50, 50, 50);
		Image btnAvatar4Img = new ImageIcon(this.getClass().getResource(Constant.bigAvatar.get(4))).getImage();
        btnAvatar4.setIcon(new ImageIcon(btnAvatar4Img));
        
		btnAvatar4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			 	avatarId = 4;
				updateAvatarBackground();
			}
		});	
        this.getContentPane().add(btnAvatar4);

		btnLogin = new JButton("Log In");
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Ebrima", Font.PLAIN, 12));
		btnLogin.setBounds(200, 170, 105, 35);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = textUsername.getText();

				if(userName.equals("")) {
					System.out.println("invalid user name!");
					JOptionPane.showMessageDialog(null, "invalid user name!");
				}
				else {
					WeiboFrame weiboframe = new WeiboFrame(avatarId, userName);
					weiboframe.setVisible(true);
					loginFrame.dispose();
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


