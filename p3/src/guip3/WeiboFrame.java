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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import guip3.Constant;
import guip3.LoginFrame;

public class WeiboFrame  extends JFrame{
	private String title = "Welcome to fantastic Weibo!";
    private JButton btnSetting;
    private JTextField textContent;
    private JButton btnSend;
    private JLabel Avatar;
    
	public WeiboFrame() {
		initialize();
	}

	
	protected void initialize() {
		this.setTitle(title);
		this.setBounds(Constant.weiboFrameX, Constant.weiboFrameY, Constant.weiboFrameWidth, Constant.weiboFrameHeight);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.getContentPane().setLayout(null);
		
		textContent = new JTextField();
		textContent.setBounds(20, 500, 300, 150);
		textContent.setColumns(10);
		this.getContentPane().add(textContent);
		
		btnSend = new JButton("Send");
		btnSend.setBounds(350, 550, 100, 50);
		this.getContentPane().add(btnSend);
		
		btnSetting = new JButton();
		btnSetting.setBounds(430, 10, 30, 30);
		Image settingImg = new ImageIcon(this.getClass().getResource("Settings-icon.png")).getImage();
        btnSetting.setIcon(new ImageIcon(settingImg));
        
        Avatar = new JLabel();
		Avatar.setBounds(10,5,50, 50);
//		if (Avatarnummber = 1){
//		Image AvatarImg = new ImageIcon(this.getClass().getResource("Settings-icon.png")).getImage();
//		}
		Image AvatarImg = new ImageIcon(this.getClass().getResource("Avatar1-icon.png")).getImage();
		Avatar.setIcon(new ImageIcon(AvatarImg));
		this.getContentPane().add(Avatar);
		
		btnSetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			   SettingFrame settingframe = new SettingFrame();
			   settingframe.setVisible(true);
			   
				
			}
		});	

		this.getContentPane().add(btnSetting);
	}
}
