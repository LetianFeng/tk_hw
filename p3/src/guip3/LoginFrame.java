package guip3;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import guip3.guiinterface;
import guip3.LoginFrame;
import guip3.Constant;


public class LoginFrame  extends JFrame{
	private String title = "Welcome to fantastic Weibo!";
	private JTextField textUsername;
    private JLabel username;
    private JButton btnLogin;
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
	}

	
	protected void initialize() {
		this.setTitle(title);
		this.setBounds(Constant.loginFrameX, Constant.loginFrameY, Constant.loginFrameWidth, Constant.loginFrameHeight);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.getContentPane().setLayout(null);
		
		username = new JLabel("UserName:");
		username.setBounds(100, 100, 81, 23);
		this.getContentPane().add(username);
		
		textUsername = new JTextField();
		textUsername.setBounds(200, 100, 105, 21);
		textUsername.setColumns(10);
		this.getContentPane().add(textUsername);
		
		btnLogin = new JButton("Log in");
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setFont(new Font("Ebrima", Font.PLAIN, 12));
		btnLogin.setBounds(140, 170, 105, 35);
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			   WeiboFrame weiboframe = new WeiboFrame();
			   weiboframe.setVisible(true);
			   
				
			}
		});	

		this.getContentPane().add(btnLogin);
	
	}
}


