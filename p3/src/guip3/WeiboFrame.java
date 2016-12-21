package guip3;

import client.BlogMessage;
import client.Client;
import client.ClientAPI;
import client.ClientConfig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.*;

public class WeiboFrame extends JFrame implements GuiAPI {
    private String title = "Welcome to fantastic MicroBlog!";
    private WeiboFrame mainFrame;
    private ClientAPI clientAPI;

    // text area for the user to input blog message
    private JTextArea inputTextContent;

    // information to log in with client api
    private int avatarId;
    private String userName;

    // items for the interface to show notification and blog message with client api
    private JLabel notificationLabel;
    private JLayeredPane blogBoxGroupPanel;
    private JScrollPane blogGroupPanelScrollPane;
    private int blogMessageCurrentHeight = 0;
    private SubscriptionButton subButton;

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

    public String getUserName() {
        return userName;
    }

    public ClientAPI getClientAPI() {
        return clientAPI;
    }

    public SubscriptionButton getSubButton() {
        return subButton;
    }

    public WeiboFrame(int avatarId, String userName) {
        this.avatarId = avatarId;
        this.userName = userName;
        clientAPI = new Client(this);
    }

    public int login_initial() {
        int status_code = clientAPI.login(userName, avatarId);
        if(status_code == ClientConfig.NO_ERROR) {
            mainFrame = this;
            initialize();
            return status_code;
        }
        else return status_code;
    }

    private void initialize() {
        this.setTitle(title);
        this.setBounds(Constant.weiboFrameX, Constant.weiboFrameY, Constant.weiboFrameWidth, Constant.weiboFrameHeight);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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

        inputTextContent = new JTextArea();
        inputTextContent.setLineWrap(true);
        inputTextContent.setBounds(20, 550, 300, 100);
        inputTextContent.setColumns(10);
        this.getContentPane().add(inputTextContent);

        JButton btnSend = new JButton("Send");
        btnSend.setBounds(350, 550, 100, 50);
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                String textWord = inputTextContent.getText();
                int count = textWord.length();
                if (count > 200) {
                    System.out.println("Maximum 200 characters!");
                    JOptionPane.showMessageDialog(null, "Sorry, you can write maximum 200 characters!");
                    return;
                } else if (count == 0) {
                    System.out.println("Can't send empty message!");
                    JOptionPane.showMessageDialog(null, "Say something? Your friends are waiting for you!");
                    return;
                }
                clientAPI.sendBlog(textWord);
                inputTextContent.setText("");
            }
        });
        this.getContentPane().add(btnSend);

        JButton btnSetting = new JButton();
        btnSetting.setBounds(440, 10, 30, 30);
        btnSetting.setIcon(new ImageIcon(this.getClass().getResource("Settings-icon.png")));
        this.getContentPane().add(btnSetting);
        btnSetting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                lockMainFrame();
                SettingFrame settingframe = new SettingFrame(mainFrame);
                settingframe.setVisible(true);
            }
        });

        JButton btnRefresh = new JButton();
        btnRefresh.setBounds(400, 10, 30, 30);
        btnRefresh.setIcon(new ImageIcon(this.getClass().getResource("Apps-Dialog-Refresh-icon.png")));
        this.getContentPane().add(btnRefresh);
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                clientAPI.showBlogList();
                notificationLabel.setVisible(false);
            }
        });

        JLabel avatar = new JLabel();
        avatar.setBounds(10, 5, 50, 50);
        String avatarPath = Constant.bigAvatar.get(avatarId);
        avatar.setIcon(new ImageIcon(this.getClass().getResource(avatarPath)));
        this.getContentPane().add(avatar);

        JLabel userNameLabel = new JLabel(userName);
        userNameLabel.setBounds(70, 5, 200, 50);
        this.getContentPane().add(userNameLabel);

        // draw the area for all blog messages with adaptive scroll pane
        blogBoxGroupPanel = new JLayeredPane();
        blogBoxGroupPanel.setLayout(null);
        blogBoxGroupPanel.setPreferredSize(new Dimension(485, 100));
        blogGroupPanelScrollPane = new JScrollPane(blogBoxGroupPanel);
        blogGroupPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        blogGroupPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        blogGroupPanelScrollPane.setBounds(0, 55, 485, 480);
        JPanel contentPane = new JPanel(null);
        contentPane.setLayout(null);
        contentPane.setBounds(5, 5, 485, 550);
        contentPane.add(blogGroupPanelScrollPane);
        this.getContentPane().add(contentPane);

        subButton = new SubscriptionButton(clientAPI);
        subButton.setVisible(false);
        blogBoxGroupPanel.add(subButton, new Integer(2));

        this.setResizable(false);
    }

    private void lockMainFrame() {
        this.mainFrame.setEnabled(false);
    }
}
