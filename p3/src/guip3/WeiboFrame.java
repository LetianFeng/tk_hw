package guip3;


import client.BlogMessage;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private String textWord;
    private static int count;

	public static void main(String[] args) {
		WeiboFrame weiboFrame = new WeiboFrame(3, "my name");
		weiboFrame.setVisible(true);
	}

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
		btnSend.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			textWord = textContent.getText();
			count = textWord.length();
			
			if(count > 5){
				System.out.println("Maximum 30 Words!");
				JOptionPane.showMessageDialog(null, "Maximum 30 Words!");
			}
			else if(count == 0){
				System.out.println("Can't send empty message!");
				JOptionPane.showMessageDialog(null, "Can't send empty message!");
			}

		}
	});
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

		String textSample = "this is a message! \"this is a message!\" this is a message! important" +
				" this is a message! \"this is a message!\" this is a message! important, #sample# " +
				" this is a message! \"this is a message!\" #new tag# important, #second tag#";
		BlogMessage blogMessage = new BlogMessage(textSample, new Date(), "player", 1);
		BlogBox blogBox = new BlogBox(blogMessage, 10, 70, Constant.weiboFrameWidth - 30, 200);
		this.getContentPane().add(blogBox);

	}
}