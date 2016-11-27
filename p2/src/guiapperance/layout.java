package guiapperance;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JDateChooser;

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
		frame = new JFrame();
		frame.setBounds(250, 50, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.NORTH);
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
        buttonOne.setBounds(600,100, 100, 50);
        card1.add(buttonOne);
//        card1.add(new JButton("Button 2"));
//        card1.add(new JButton("Button 3"));
        checkintext = new JLabel("Check in date");
		checkintext.setBounds(175, 64, 81, 23);
		card1.add(checkintext);
		checkouttext = new JLabel("Check out date");
			checkouttext.setBounds(375, 64, 101, 23);
			card1.add(checkouttext);
			tabbedPane.addTab(BUTTONPANEL, card1); 
			  JPanel card2 = new JPanel();
		tabbedPane.addTab(TEXTPANEL, card2);
      
//        card2.add(new JTextField("TextField", 20));
        
        
        JPanel card3 = new JPanel();
        tabbedPane.addTab(extraservice, card3);
        
        JPanel card4 = new JPanel();
        tabbedPane.addTab(checkout, card4);
        
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBounds(180, 100, 100, 34);
        card1.add(dateChooser);
        
        JDateChooser dateChooser_2 = new JDateChooser();
        dateChooser_2.setBounds(411, 100, 100, 34);
        card1.add(dateChooser_2);
 
 
	}
}
