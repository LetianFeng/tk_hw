package guiapperance;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class layout {

	private JFrame frame;
	final static String BUTTONPANEL = "Travel date";
	final static String TEXTPANEL = "Choose Room";
	final static String extraservice = "Extra Service";
	final static String checkout = "Check out";
	final static int extraWindowWidth = 900;
	final static int extraWindowHeight = 500;
	private JLabel checkintext;
	private JLabel checkouttext;
	private JTable table;
	private JTextField textField;
	public int servicetablerow = 0;
	public DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					layout window = new layout();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public layout() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(("Welcome to Sunshine Hotel!"));
		frame.setBounds(250, 50, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.SCROLL_TAB_LAYOUT);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		this.initializeTravelDatePanel(tabbedPane);
		this.initializeChooseRoom(tabbedPane);
		this.initializeChooseService(tabbedPane);
		this.initializeCheckOut(tabbedPane);

	}


	private void initializeTravelDatePanel(JTabbedPane tabbedPane) {
		JPanel card1 = new JPanel() {
			// Make the panel wider than it really needs, so
			// the window's wide enough for the tabs to stay
			// in one row.
			public Dimension getPreferredSize() {
				Dimension size = super.getPreferredSize();
				size.width += extraWindowWidth;
				size.height += extraWindowHeight;
				return size;
			}
		};
		card1.setLayout(null);
		// card1.add(new JButton("Button 1"));
		JButton buttonOne = new JButton("Search");
		buttonOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedIndex(1);
			}
		});
		// buttonOne.setLayout(null);
		buttonOne.setBounds(600, 100, 100, 35);
		card1.add(buttonOne);
		// card1.add(new JButton("Button 2"));
		// card1.add(new JButton("Button 3"));
		checkintext = new JLabel("Check in date");
		checkintext.setBounds(175, 64, 100, 23);
		card1.add(checkintext);
		checkouttext = new JLabel("Check out date");
		checkouttext.setBounds(375, 64, 120, 23);
		card1.add(checkouttext);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(180, 100, 150, 34);
		card1.add(dateChooser);

		JDateChooser dateChooser_2 = new JDateChooser();
		dateChooser_2.setBounds(380, 100, 150, 34);
		card1.add(dateChooser_2);

		tabbedPane.addTab(BUTTONPANEL, card1);

	}
	private void initializeChooseRoom(JTabbedPane tabbedPane) {
		JPanel card2 = new JPanel() {
			// Make the panel wider than it really needs, so
			// the window's wide enough for the tabs to stay
			// in one row.
			public Dimension getPreferredSize() {
				Dimension size = super.getPreferredSize();
				size.width += extraWindowWidth;
				size.height += extraWindowHeight;
				return size;
			}
		};
		// card2.setLayout(null);

		
        this.generateTable();
		// JScrollPane jps = new JScrollPane(table);
		// card2.add(jps);
		card2.add(table,BorderLayout.CENTER);
		JTableHeader tableheader = table.getTableHeader();
		card2.add(tableheader,BorderLayout.NORTH);
		JButton buttonTwo = new JButton("Add to Basket");
		buttonTwo.setBounds(400, 400, 300, 35);
		Image img = new ImageIcon(this.getClass().getResource("shopping-cart-icon.png")).getImage();
		buttonTwo.setIcon(new ImageIcon(img));
		buttonTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		card2.add(buttonTwo);
		tabbedPane.addTab(TEXTPANEL, card2);
	}
	
	private void initializeChooseService(JTabbedPane tabbedPane) {
		JPanel card3 = new JPanel();
		tabbedPane.addTab(extraservice, card3);
		card3.setLayout(null);

		JLabel lblPleaseEnterYour = new JLabel("Please enter your Email address to confirm your booking:");
		lblPleaseEnterYour.setBounds(175, 300, 500, 22);
		card3.add(lblPleaseEnterYour);

		JLabel lblNewLabel = new JLabel("Do you need extra service?");
		lblNewLabel.setBounds(175, 100, 300, 30);
		card3.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(171, 332, 245, 30);
		card3.add(textField);
		textField.setColumns(10);
        
		//this.generateTable();
		//card3.add(table);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(419, 433, 93, 23);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedIndex(3);
			}
		});
		card3.add(btnSubmit);
	
	}
	private void initializeCheckOut(JTabbedPane tabbedPane) {

		JPanel card4 = new JPanel();
		tabbedPane.addTab(checkout, card4);
		card4.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel();
		// Your Booking is processed successfully.Your booking ID is
		lblNewLabel_1.setText("abc");
		lblNewLabel_1.setBounds(175, 100, 500, 30);
		card4.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("What a pity, your booking is failed.");
		lblNewLabel_2.setBounds(175, 300, 300, 30);
		card4.add(lblNewLabel_2);
		
	}
	private void generateTable(){
		
	//	Object[] columnNames = { "Room type", "Amount", "Price", "Book" };
		// String[][] data ={{"Double room","2","60", "0"},{"Single
		// room","1","40","1"},{"Double room","2","60", "0"},{"Double
		// room","2","60", "0"}};
		// DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table = new JTable();

		/*
		 * public boolean isCellEditable(int data,int columns){ return false; }
		 * };
		 **/
		table.setPreferredScrollableViewportSize(new Dimension(450, 63));
		// table.setFillsViewportHeight(true);
		model = new DefaultTableModel() {
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
		table.setModel(model);
		// model.addColumn(columns);
		// model.addColumn(new Object [0]);
		// model.setValueAt()
		model.addColumn("select");
		model.addColumn("Room type");
		model.addColumn("Amount");
		model.addColumn("Price");
        this.drawservicetable("double room", 200, "good", 2);
		
		//for (int i = 0; i < 8; i++) {
			
		}
	
	//	}
public void drawservicetable(String serviceName, double price, String description, int availableAmount){
	//to do
	model.addRow(new Object[0]);
	model.setValueAt(false, servicetablerow, 0);
	model.setValueAt(serviceName, servicetablerow, 1);
	model.setValueAt(String.valueOf(availableAmount), servicetablerow, 2);
	model.setValueAt(String.valueOf(price), servicetablerow, 3);
	servicetablerow ++;
	
}
	}
