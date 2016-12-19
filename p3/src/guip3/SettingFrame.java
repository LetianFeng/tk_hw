package guip3;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
	private JLabel subinfo;
	private String subinfocontent = "In the following are users and topics you have subscribed:";
	private JLabel manualadd;
	private String manualaddcontent = "Subscribe users or topics manually:"; 
    private JButton btnSavechange;
    private JTextField subcontent;
    private JTable SubTable;
    private DefaultTableModel SubTableModel;
    private JScrollPane scrollPane;
	private JFrame settingFrame;
	private JFrame mainFrame;
    
	public SettingFrame(JFrame mainFrame) {
		initialize();
		settingFrame = this;
		this.mainFrame = mainFrame;
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
		subinfo = new JLabel(subinfocontent);
		subinfo.setBounds(20,50,400, 23);
		this.getContentPane().add(subinfo);
		
		manualadd = new JLabel(manualaddcontent);
		manualadd.setBounds(20,400,400, 23);
		this.getContentPane().add(manualadd);
		
	    subcontent = new JTextField();
		subcontent.setBounds(250, 400, 105, 21);
		subcontent.setColumns(10);
		this.getContentPane().add(subcontent);
		
		btnSavechange = new JButton("Save Changes and Submit");
		btnSavechange.setBounds(250, 550, 200, 25);
		btnSavechange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// send new subscription list to control unit
				settingFrame.dispatchEvent(new WindowEvent(settingFrame, WindowEvent.WINDOW_CLOSING));
			}
		});	
		this.getContentPane().add(btnSavechange);
		
		 this.generateSubTable();
	        JScrollPane scrollPane = new JScrollPane(SubTable);
	        this.getContentPane().add(scrollPane);
	        scrollPane.setBounds(20, 90, 400, 300);
	        //chooseRoomPanel.add(chooseRoomTable,BorderLayout.CENTER);
	        JTableHeader tableHeader = SubTable.getTableHeader();

		this.setResizable(false);

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}
	 private void generateSubTable() {
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
	        SubTableModel.addColumn("Type");
	        SubTableModel.addColumn("Name");
	        
	     // for test	        
	        SubTableModel.setRowCount(0);
	        for (int i = 0; i < 4; i++) {
	           // Sublist currentSub = subList.get(i);
	            SubTableModel.addRow(new Object[0]);
	            SubTableModel.setValueAt(false, i, 0);
	            SubTableModel.setValueAt("User", i, 1);
	            SubTableModel.setValueAt("Shule", i, 2);
	            
	            
	        }

	        
	    }
/*
	    void drawSubTable(ArrayList<Sublist> subList) {
	        deawSubTableModel.setRowCount(0);
	        for (int i = 0; i < subList.size(); i++) {
	            Sublist currentSub = subList.get(i);
	            SubTableModel.addRow(new Object[0]);
	            SubTableModel.setValueAt(false, i, 0);
	            SubTableModel.setValueAt("ss", i, 1);
	            SubTableModel.setValueAt("ss", i, 2);
	            SubTableModel.setValueAt("dd", i, 3);
	        }
	    }
**/
}
