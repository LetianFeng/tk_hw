package guip3;


import client.BlogMessage;
import client.Client;
import client.ClientAPI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class WeiboFrame  extends JFrame implements GuiAPI {
	private String title = "Welcome to fantastic Weibo!";
    private JButton btnSetting;
    private JTextArea textContent;
    private JButton btnSend;
    private JButton btnRefresh;
    private JLabel Avatar;
	private int avatarId;
	private JFrame mainFrame;
	private String userName;
	private String textWord;
    private static int count;
	private JLayeredPane blogBoxGroupPanel;
	private SubscriptionButton subButton;
	private JLabel notificationLabel;
	private JScrollPane blogGroupPanelScrollPane;
	private int blogMessageCurrentHeight = 0;
	private ClientAPI clientAPI;

	@Override
	public void showNotification(String notification) {
		notificationLabel.setText(notification);
		notificationLabel.setVisible(true);
	}

	@Override
	public void showBlog(BlogMessage blogMessage) {
		BlogBox blogBox = new BlogBox(blogMessage, 10, blogMessageCurrentHeight + 5,
										Constant.weiboFrameWidth - 60, this);
		blogBoxGroupPanel.add(blogBox, new Integer(1));
		blogMessageCurrentHeight += blogBox.getHeight();
		blogBoxGroupPanel.setPreferredSize(new Dimension(485, blogMessageCurrentHeight + 10));
		blogGroupPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		blogGroupPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
            e.getAdjustable().setValue(e.getAdjustable().getMaximum()); 
            blogGroupPanelScrollPane.getVerticalScrollBar().removeAdjustmentListener(this);
	        }
	    });
		this.repaint();
	}

	public ClientAPI getClientAPI() {
		return clientAPI;
	}

	public SubscriptionButton getSubButton() {
		return subButton;
	}

	public static void main(String[] args) {
		WeiboFrame weiboFrame = new WeiboFrame(3, "my name");
		weiboFrame.setVisible(true);
	}

	public WeiboFrame(int avatarId, String userName) {
		this.avatarId = avatarId;
		this.userName = userName;
		mainFrame = this;
		clientAPI = new Client(this);
		clientAPI.login(userName, avatarId);
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

		notificationLabel = new JLabel("You have new Message");
		notificationLabel.setBounds(200, 20, 200, 20);
		notificationLabel.setForeground(Color.red);
		notificationLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
		notificationLabel.setVisible(false);
		this.getContentPane().add(notificationLabel);

		textContent = new JTextArea();
		textContent.setLineWrap(true);
		textContent.setBounds(20, 550, 300, 100);
		textContent.setColumns(10);
	
		this.getContentPane().add(textContent);
		

		btnSend = new JButton("Send");
		btnSend.setBounds(350, 550, 100, 50);
	//	btnSend.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "DoClick");
	//	this.getRootPane().setDefaultButton(btnSend);
		btnSend.addKeyListener(new KeyAdapter() {
			  public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	btnSend.doClick();
			    }
			  }
			});
		btnSend.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		
			textWord = textContent.getText();
			count = textWord.length();
			if(count > 200){
				System.out.println("Maximum 200 characters!");
				JOptionPane.showMessageDialog(null, "Sorry, you can write maximum 200 characters!");
				return;
			}
			else if(count == 0){
				System.out.println("Can't send empty message!");
				JOptionPane.showMessageDialog(null, "Say something? Your friends are waiting for you!");
				return;
			}

			clientAPI.sendBlog(textWord);
//			BlogMessage blogMessage = new BlogMessage(textWord, new Date(), userName, avatarId);
//			showBlog(blogMessage);
			textContent.setText("");

		}
	});
		this.getContentPane().add(btnSend);

		btnSetting = new JButton();
		btnSetting.setBounds(440, 10, 30, 30);
		Image settingImg = new ImageIcon(this.getClass().getResource("Settings-icon.png")).getImage();
        btnSetting.setIcon(new ImageIcon(settingImg));
        
    	btnSetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lockMainFrame();
				SettingFrame settingframe = new SettingFrame(mainFrame);
				settingframe.setVisible(true);
			}
		});

        
    	btnRefresh = new JButton();
		btnRefresh.setBounds(400, 10, 30, 30);
		Image refreshImg = new ImageIcon(this.getClass().getResource("Apps-Dialog-Refresh-icon.png")).getImage();
        btnRefresh.setIcon(new ImageIcon(refreshImg));
        this.getContentPane().add(btnRefresh);
        
        btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                  clientAPI.showBlogList();
				notificationLabel.setVisible(false);
			}
		});

        
        Avatar = new JLabel();
		Avatar.setBounds(10,5,50, 50);

		String avatarPath = "Avatar" + String.valueOf(avatarId) + "-icon.png";
		Image AvatarImg = new ImageIcon(this.getClass().getResource(avatarPath)).getImage();
		Avatar.setIcon(new ImageIcon(AvatarImg));
		this.getContentPane().add(Avatar);
		
	
		JLabel userNameLabel = new JLabel(userName);
		userNameLabel.setBounds(70, 5, 200, 50);

		this.getContentPane().add(userNameLabel);
		this.getContentPane().add(btnSetting);
        this.setResizable(false);
        
		blogBoxGroupPanel = new JLayeredPane();
		blogBoxGroupPanel.setLayout(null);

		blogBoxGroupPanel.setPreferredSize(new Dimension(485,100));
		blogGroupPanelScrollPane = new JScrollPane(blogBoxGroupPanel);
	    blogGroupPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    blogGroupPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    blogGroupPanelScrollPane.setBounds(0, 55, 485, 480);
	    JPanel contentPane = new JPanel(null);
	    contentPane.setLayout(null);
	    contentPane.setBounds(5, 5, 485, 550);

	    contentPane.add(blogGroupPanelScrollPane);
	    this.getContentPane().add(contentPane);	

		String textSample1 = "this is a message! \"this is a message!\" this is a message! important" +
				" this is a message! \"this is a message!\" this is a message! important, #sample# " +
				" this is a message! \"this is a message!\" #new tag# important, #second tag#"+
				" this is a message! \"this is a message!\" #new tag# important, #second tag#";
		String textSample2 = "this is a message! \"this is a message!\" this is a message! important" +
				" this is a message! \"this is a message!\" this is a message! important, #sample# ";
		String textSample3 = "this is a message! \"this is a message!\" this is a message! important" +
				" this is a message! \"this is a message!\" this is a message! important, #sample# " +
				" this is a message! \"this is a message!\" #new tag# important, #second tag#"+
				" this is a message! \"this is a message!\" #new tag# important, #second tag#" +
				" this is a message! \"this is a message!\" this is a message! important, #sample# " +
				" is it right? not at all";
		BlogMessage blogMessage1 = new BlogMessage(textSample1, new Date(), "player 1", 1);
		BlogMessage blogMessage2 = new BlogMessage(textSample2, new Date(), "player 2", 2);
		BlogMessage blogMessage3 = new BlogMessage(textSample3, new Date(), "player 3", 3);

		showBlog(blogMessage1);
		showBlog(blogMessage2);
		showBlog(blogMessage3);

		subButton = new SubscriptionButton(clientAPI);
		subButton.setVisible(false);
		blogBoxGroupPanel.add(subButton, new Integer(2));
	}


	public void lockMainFrame() {
		this.mainFrame.setEnabled(false);
	}
	
	//@Override
	public void unlockMainFrame() {
		this.mainFrame.setEnabled(true);
	}

	
}  
