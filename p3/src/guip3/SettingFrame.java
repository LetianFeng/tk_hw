package guip3;

import client.ClientConfig;
import org.omg.CORBA.PRIVATE_MEMBER;

import client.TopicMgtItem;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;



public class SettingFrame  extends JFrame{
	private String title = "Settings and Configuration";
	private JLabel subInfo1;
	private JLabel subInfo2;
	private String subInfoContent1 = "Users you have subscribed:";
	private String subInfoContent2 = "Topics you have subscribed:";
	private JLabel manualAdd1;
	private JLabel manualAdd2;
	private String manualAddContent1 = "Subscribe users manually:"; 
	private String manualAddContent2 = "Subscribe topics manually:"; 
    private JButton btnSavechange;
    private JButton btnTick1;
    private JButton btnTick2;
    private JTextField subContent1;
    private JTextField subContent2;
    private JTable SubTable;
    private DefaultTableModel SubTableModel;
    private JScrollPane scrollPane;
	private JFrame settingFrame;
	private WeiboFrame mainFrame;
    
	public SettingFrame(WeiboFrame mainFrame) {
		settingFrame = this;
		this.mainFrame = mainFrame;
		initialize();
	}
	
	//main function for test
	 public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    SettingFrame settingframe = new SettingFrame();
	                    settingframe.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }

	public SettingFrame() {
		initialize();

	}
	
	
	protected void initialize() {
		this.setTitle(title);
		this.setBounds(Constant.settingFrameX, Constant.settingFrameY, Constant.settingFrameWidth, Constant.settingFrameHeight);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mainFrame.setEnabled(true);
		
			}
		});
		this.getContentPane().setLayout(null);
		subInfo1 = new JLabel(subInfoContent1);
		subInfo1.setBounds(20,50,400, 23);
		this.getContentPane().add(subInfo1);
		
		subInfo2 = new JLabel(subInfoContent2);
		subInfo2.setBounds(250,50,400, 23);
		this.getContentPane().add(subInfo2);
		
		manualAdd1 = new JLabel(manualAddContent1);
		manualAdd1.setBounds(20,400,400, 23);
		this.getContentPane().add(manualAdd1);
		
		manualAdd2 = new JLabel(manualAddContent2);
		manualAdd2.setBounds(20,450,400, 23);
		this.getContentPane().add(manualAdd2);
		
	    subContent1 = new JTextField();
		subContent1.setBounds(250, 400, 105, 21);
		subContent1.setColumns(10);
		this.getContentPane().add(subContent1);

	    subContent2 = new JTextField();
		subContent2.setBounds(250, 450, 105, 21);
		subContent2.setColumns(10);
		this.getContentPane().add(subContent2);
		
		btnTick1 = new JButton();
		btnTick1.setBounds(400, 400, 30, 30);
		Image settingImg = new ImageIcon(this.getClass().getResource("tick_green.png")).getImage();
        btnTick1.setIcon(new ImageIcon(settingImg));
		this.getContentPane().add(btnTick1);
		
		btnTick2 = new JButton();
		btnTick2.setBounds(400, 450, 30, 30);
		Image settingImg2 = new ImageIcon(this.getClass().getResource("tick_green.png")).getImage();
        btnTick2.setIcon(new ImageIcon(settingImg2));
		this.getContentPane().add(btnTick2);
		
		btnSavechange = new JButton("Save Changes and Submit");
		btnSavechange.setBounds(250, 550, 200, 25);
		btnSavechange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// send new subscription list to control unit
				settingFrame.dispatchEvent(new WindowEvent(settingFrame, WindowEvent.WINDOW_CLOSING));
			}
		});	
		this.getContentPane().add(btnSavechange);
		
		    this.generateSubTable("User Name", ClientConfig.USER_PREFEX);
	        JScrollPane scrollPane = new JScrollPane(SubTable);
	        this.getContentPane().add(scrollPane);
	        scrollPane.setBounds(20, 90, 200, 300);
	        //chooseRoomPanel.add(chooseRoomTable,BorderLayout.CENTER);
	        JTableHeader tableHeader = SubTable.getTableHeader();
	        
	        this.generateSubTable("Topics", ClientConfig.TOPIC_PREFIX);
	        JScrollPane scrollPane2 = new JScrollPane(SubTable);
	        this.getContentPane().add(scrollPane2);
	        scrollPane2.setBounds(250, 90, 200, 300);
	        //chooseRoomPanel.add(chooseRoomTable,BorderLayout.CENTER);
	        JTableHeader tableHeader2 = SubTable.getTableHeader();

		this.setResizable(false);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}
	 private void generateSubTable(String ColumnName, String topicPrefix) {
	        SubTable = new JTable();
	        SubTableModel = new DefaultTableModel() {
	            public Class<?> getColumnClass(int column) {

	                switch (column) {
	                    case 0:
	                        return Boolean.class;
	                    case 1:
	                        return String.class;
	                    case 2:
	                        return String.class;
	                    case 3:
	                        return String.class;
	                    default:
	                        return String.class;
	                }
	            }
	        };
	        SubTable.setModel(SubTableModel);
	        SubTableModel.addColumn("Status");
	  
	        SubTableModel.addColumn(ColumnName);
	        
	     // for test	        
	        SubTableModel.setRowCount(0);

		 	List<TopicMgtItem> topicMgtItemList = mainFrame.getClientAPI().getTopicManagementList(topicPrefix);

		 	for (int i =0; i < topicMgtItemList.size(); i++) {
				SubTableModel.addRow(new Object[0]);
				SubTableModel.setValueAt(topicMgtItemList.get(i).isSubscribed(), i, 0);
				SubTableModel.setValueAt(topicMgtItemList.get(i).getTopic(), i, 1);
			}

		}
}
