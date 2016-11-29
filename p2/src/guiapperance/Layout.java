package guiapperance;

import java.awt.EventQueue;
import java.awt.Image;

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
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class Layout {

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Layout window = new Layout();
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
	public Layout() {
		initialize();
	}
	


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(("Welcome to Sunshine Hotel!"));
		frame.setBounds(250, 50, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		JPanel card1 = new JPanel() {
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                size.height += extraWindowHeight;
                return size;
            }
        };
        card1.setLayout(null); 
   //     card1.add(new JButton("Button 1"));
        JButton buttonOne = new JButton("Search");
   //     buttonOne.setLayout(null);
        buttonOne.setBounds(600,100, 100, 35);
        card1.add(buttonOne);
//        card1.add(new JButton("Button 2"));
//        card1.add(new JButton("Button 3"));
        checkintext = new JLabel("Check in date");
		checkintext.setBounds(175, 64, 81, 23);
		card1.add(checkintext);
		checkouttext = new JLabel("Check out date");
			checkouttext.setBounds(375, 64, 101, 23);
			card1.add(checkouttext);
	    
			JDateChooser dateChooser = new JDateChooser();
	        dateChooser.setBounds(180, 100, 100, 34);
	        card1.add(dateChooser);
	        
	        JDateChooser dateChooser_2 = new JDateChooser();
	        dateChooser_2.setBounds(411, 100, 100, 34);
	        card1.add(dateChooser_2);
	 
			tabbedPane.addTab(BUTTONPANEL, card1); 
		
						
			
			JPanel card2 = new JPanel(){
		            //Make the panel wider than it really needs, so
		            //the window's wide enough for the tabs to stay
		            //in one row.
		            public Dimension getPreferredSize() {
		                Dimension size = super.getPreferredSize();
		                size.width += extraWindowWidth;
		                size.height += extraWindowHeight;
		                return size;
		            }
		        };
		//	  card2.setLayout(null);
				
	    Object[] columnNames = { "Room type","Amount","Price","Book"};
//		String[][] data ={{"Double room","2","60", "0"},{"Single room","1","40","1"},{"Double room","2","60", "0"},{"Double room","2","60", "0"}};
//		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table = new JTable();
			
/*		public boolean isCellEditable(int data,int columns){
				return false;
		}
		};
		**/
		table.setPreferredScrollableViewportSize(new Dimension(450,63));
	//	table.setFillsViewportHeight(true);
		DefaultTableModel model = new DefaultTableModel()
		{
			public Class<?> getColumnClass(int column){
			
			switch(column)
			{
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
 //   model.addColumn(columns);
//	model.addColumn(new Object [0]);
//	model.setValueAt()
	model.addColumn("select");
	model.addColumn("Room type");
	model.addColumn("Amount");
	model.addColumn("Price");
	
    for(int i=0;i<4;i++){
    	 model.addRow(new Object[0]);
    	 model.setValueAt(false,i,0);
    	 model.setValueAt("Our Row",i, 1);
    	 model.setValueAt("Column 2",i, 2);
    	 model.setValueAt("Column 3",i, 3);
     }


		
//		JScrollPane jps = new JScrollPane(table);
//    	card2.add(jps);
		card2.add(table);
		 JButton buttonTwo = new JButton("Add to Basket");
		   //     buttonOne.setLayout(null);
		        buttonTwo.setBounds(600,400, 300, 35);
		        Image img = new ImageIcon(this.getClass().getResource("shopping-cart-icon.png")).getImage();
			    buttonTwo.setIcon(new ImageIcon(img));
		        card2.add(buttonTwo);
      
//        card2.add(new JTextField("TextField", 20));
        
		tabbedPane.addTab(TEXTPANEL, card2);
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
        
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(419, 433, 93, 23);
        card3.add(btnSubmit);
        
        JPanel card4 = new JPanel();
        tabbedPane.addTab(checkout, card4);
        card4.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("Your Booking is processed successfully.Your booking ID is");
        lblNewLabel_1.setBounds(175, 100, 300, 30);
        card4.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("What a pity, your booking is failed.");
        lblNewLabel_2.setBounds(175, 300, 300, 30);
        card4.add(lblNewLabel_2);
        

 
	}
}
