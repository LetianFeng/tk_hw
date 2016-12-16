package guip3;


import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class WeiboFrame  extends JFrame{
	private String title = "Welcome to fantastic Weibo!";
    private JButton btnSetting;
    private JTextArea textContent;
    private JButton btnSend;
    private JLabel Avatar;
	private int avatarId;
	private JFrame mainFrame;
	private String userName;
    
	public WeiboFrame(int avatarId, String userName) {
		this.avatarId = avatarId;
		this.userName = userName;
		initialize();
		mainFrame = this;
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
		
		textContent = new JTextArea();
		textContent.setLineWrap(true);
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

		String avatarPath = "Avatar" + String.valueOf(avatarId) + "-icon.png";
		Image AvatarImg = new ImageIcon(this.getClass().getResource(avatarPath)).getImage();
		Avatar.setIcon(new ImageIcon(AvatarImg));
		this.getContentPane().add(Avatar);
		
		btnSetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setEnabled(false);
				SettingFrame settingframe = new SettingFrame(mainFrame);
				settingframe.setVisible(true);
			}
		});

		JLabel userNameLabel = new JLabel(userName);
		userNameLabel.setBounds(70, 5, 50, 50);

		this.getContentPane().add(userNameLabel);

		this.getContentPane().add(btnSetting);

		this.setResizable(false);

	}
}
