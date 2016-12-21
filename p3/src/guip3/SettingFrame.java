package guip3;

import client.ClientConfig;

import client.TopicMgtItem;

import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class SettingFrame extends JFrame {
    private String title = "Settings and Configuration";
    private String subInfoContent1 = "Users you have subscribed:";
    private String subInfoContent2 = "Topics you have subscribed:";
    private String manualAddContent1 = "Subscribe users manually:";
    private String manualAddContent2 = "Subscribe topics manually:";
    private JTextField subContent1;
    private JTextField subContent2;
    private DefaultTableModel SubTableModel1;
    private DefaultTableModel SubTableModel2;
    private JFrame settingFrame;
    private WeiboFrame mainFrame;
    private List<TopicMgtItem> topicMgtItemList;
    private List<TopicMgtItem> userMgtItemList;

    public SettingFrame(WeiboFrame mainFrame) {
        settingFrame = this;
        this.mainFrame = mainFrame;
        initialize();
    }

    protected void initialize() {
        this.setTitle(title);
        this.setBounds(Constant.settingFrameX, Constant.settingFrameY, Constant.settingFrameWidth,
                Constant.settingFrameHeight);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mainFrame.setEnabled(true);

            }
        });
        this.getContentPane().setLayout(null);

        topicMgtItemList = mainFrame.getClientAPI().getTopicManagementList(ClientConfig.TOPIC_PREFIX);
        userMgtItemList = mainFrame.getClientAPI().getTopicManagementList(ClientConfig.USER_PREFEX);

        JLabel subInfo1 = new JLabel(subInfoContent1);
        subInfo1.setBounds(20, 50, 400, 23);
        this.getContentPane().add(subInfo1);

        JLabel subInfo2 = new JLabel(subInfoContent2);
        subInfo2.setBounds(250, 50, 400, 23);
        this.getContentPane().add(subInfo2);

        JLabel manualAdd1 = new JLabel(manualAddContent1);
        manualAdd1.setBounds(20, 400, 400, 23);
        this.getContentPane().add(manualAdd1);

        JLabel manualAdd2 = new JLabel(manualAddContent2);
        manualAdd2.setBounds(20, 450, 400, 23);
        this.getContentPane().add(manualAdd2);

        subContent1 = new JTextField();
        subContent1.setBounds(250, 400, 105, 21);
        subContent1.setColumns(10);
        this.getContentPane().add(subContent1);

        subContent2 = new JTextField();
        subContent2.setBounds(250, 450, 105, 21);
        subContent2.setColumns(10);
        this.getContentPane().add(subContent2);

        JButton btnTick1 = new JButton();
        btnTick1.setBounds(400, 400, 30, 30);
        Image settingImg = new ImageIcon(this.getClass().getResource("tick_green.png")).getImage();
        btnTick1.setIcon(new ImageIcon(settingImg));
        btnTick1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String subUser = subContent1.getText();
                int count = subUser.length();
                if (count == 0) {
                    System.out.println("Can't send empty message!");
                    JOptionPane.showMessageDialog(null, "Can't send empty message!");
                    return;
                } else {
                    SubTableModel1.addRow(new Object[0]);
                    SubTableModel1.setValueAt(true, userMgtItemList.size(), 0);
                    SubTableModel1.setValueAt(subContent1.getText(), userMgtItemList.size(), 1);
                    userMgtItemList.add(new TopicMgtItem(subContent1.getText(), true));
                    subContent1.setText("");
                }

            }
        });
        this.getContentPane().add(btnTick1);

        JButton btnTick2 = new JButton();
        btnTick2.setBounds(400, 450, 30, 30);
        Image settingImg2 = new ImageIcon(this.getClass().getResource("tick_green.png")).getImage();
        btnTick2.setIcon(new ImageIcon(settingImg2));
        btnTick2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String subTopic = subContent2.getText();
                int count = subTopic.length();
                if (count == 0) {
                    System.out.println("Can't send empty message!");
                    JOptionPane.showMessageDialog(null, "Can't send empty message!");
                    return;
                } else {
                    SubTableModel2.addRow(new Object[0]);
                    SubTableModel2.setValueAt(true, topicMgtItemList.size(), 0);
                    SubTableModel2.setValueAt(subContent2.getText(), topicMgtItemList.size(), 1);
                    topicMgtItemList.add(new TopicMgtItem(subContent2.getText(), true));
                    subContent2.setText("");
                }
            }

        });
        this.getContentPane().add(btnTick2);

        JTable SubTable1 = new JTable();
        SubTableModel1 = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {

                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        SubTable1.setModel(SubTableModel1);
        SubTableModel1.addColumn("Status");
        SubTableModel1.addColumn("User Name");
        SubTableModel1.setRowCount(0);

        for (int i = 0; i < userMgtItemList.size(); i++) {
            SubTableModel1.addRow(new Object[0]);
            SubTableModel1.setValueAt(userMgtItemList.get(i).isSubscribed(), i, 0);
            SubTableModel1.setValueAt(userMgtItemList.get(i).getTopic(), i, 1);
        }

        SubTableModel1.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if ((column == 0) && row < userMgtItemList.size()) {
                        if (userMgtItemList.get(row).isSubscribed())
                            userMgtItemList.get(row).unsubscribe();
                        else {
                            userMgtItemList.get(row).subscribe();
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane1 = new JScrollPane(SubTable1);
        this.getContentPane().add(scrollPane1);
        scrollPane1.setBounds(20, 90, 200, 300);

        JTable SubTable2 = new JTable();
        SubTableModel2 = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {

                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        SubTable2.setModel(SubTableModel2);
        SubTableModel2.addColumn("Status");
        SubTableModel2.addColumn("Topics");
        SubTableModel2.setRowCount(0);

        for (int j = 0; j < topicMgtItemList.size(); j++) {
            SubTableModel2.addRow(new Object[0]);
            SubTableModel2.setValueAt(topicMgtItemList.get(j).isSubscribed(), j, 0);
            SubTableModel2.setValueAt(topicMgtItemList.get(j).getTopic(), j, 1);
        }
        SubTableModel2.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if (column == 0 && row < topicMgtItemList.size()) {
                        if (topicMgtItemList.get(row).isSubscribed())
                            topicMgtItemList.get(row).unsubscribe();
                        else {
                            topicMgtItemList.get(row).subscribe();
                        }
                    }
                }
            }
        });
        JScrollPane scrollPane2 = new JScrollPane(SubTable2);
        this.getContentPane().add(scrollPane2);
        scrollPane2.setBounds(250, 90, 200, 300);

        JButton btnSaveChange = new JButton("Save Changes and Submit");
        btnSaveChange.setBounds(250, 550, 200, 25);
        btnSaveChange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                for (TopicMgtItem currentTopic : topicMgtItemList) {
                    if (currentTopic.isSubscribed())
                        mainFrame.getClientAPI().subscribeTopic(currentTopic.getTopic());
                    else {
                        mainFrame.getClientAPI().unSubscribeTopic(currentTopic.getTopic());
                    }
                }

                for (TopicMgtItem currentUser : userMgtItemList) {
                    if (currentUser.isSubscribed())
                        mainFrame.getClientAPI().followPerson(currentUser.getTopic());
                    else {
                        mainFrame.getClientAPI().unFollowPerson(currentUser.getTopic());
                    }
                }
                settingFrame.dispatchEvent(new WindowEvent(settingFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        this.getContentPane().add(btnSaveChange);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
