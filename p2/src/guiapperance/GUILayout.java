package guiapperance;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
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

import com.toedter.calendar.JDateChooser;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JTextField;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GUILayout {

    private JFrame mainFrame;
    private final static String TRAVEL_DATE = "Travel Date";
    private final static String CHOOSE_ROOM = "Choose Room";
    private final static String EXTRA_SERVICE = "Extra Service";
    private final static String RECEIPT = "Receipt";
    private final static int extraWindowWidth = 900;
    private final static int extraWindowHeight = 500;
    private JTable chooseRoomTable;
    private DefaultTableModel chooseRoomTableModel;
    private JPanel travelDatePanel;
    private JPanel chooseRoomPanel;
    private JPanel chooseServicePanel;
    private JPanel checkOutPanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUILayout window = new GUILayout();
                    window.mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GUILayout() {
        initializeAll();
    }

    /**
     * Initialize the contents of the mainFrame.
     */
    private void initializeAll() {
        mainFrame = new JFrame(("Welcome to Sunshine Hotel!"));
        mainFrame.setBounds(250, 50, 900, 600);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // mainFrame.getContentPane().setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.SCROLL_TAB_LAYOUT);
        mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        this.initializeTravelDatePanel(tabbedPane);
        this.initializeChooseRoomPanel(tabbedPane);
        this.initializeChooseServicePanel(tabbedPane);
        this.initializeCheckOutPanel(tabbedPane);
    }

    private void initializeTravelDatePanel(final JTabbedPane tabbedPane) {
        travelDatePanel = new JPanel() {
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
        travelDatePanel.setLayout(null);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //invalidDate("Error");
                tabbedPane.setSelectedIndex(1);
            }
        });
        // searchButton.setLayout(null);
        searchButton.setBounds(600, 100, 100, 35);
        travelDatePanel.add(searchButton);
        JLabel checkInText = new JLabel("Check in date");
        checkInText.setBounds(175, 64, 100, 23);
        travelDatePanel.add(checkInText);
        JLabel checkOutText = new JLabel("Check out date");
        checkOutText.setBounds(375, 64, 120, 23);
        travelDatePanel.add(checkOutText);

        JDateChooser startDateChooser = new JDateChooser();
        startDateChooser.setBounds(180, 100, 150, 34);
        travelDatePanel.add(startDateChooser);
        JDateChooser endDateChooser = new JDateChooser();
        endDateChooser.setBounds(380, 100, 150, 34);
        travelDatePanel.add(endDateChooser);

        tabbedPane.addTab(TRAVEL_DATE, travelDatePanel);

    }

    private void initializeChooseRoomPanel(final JTabbedPane tabbedPane) {
        chooseRoomPanel = new JPanel();
        // Make the panel wider than it really needs, so
        // the window's wide enough for the tabs to stay
        // in one row.
//			public Dimension getPreferredSize() {
//                Dimension size = super.getPreferredSize();
//				size.width += extraWindowWidth;
//				size.height += extraWindowHeight;
//				return size;
//			}
//		};

        chooseRoomPanel.setLayout(null);

        this.generateRoomsTable();
        JScrollPane scrollPane = new JScrollPane(chooseRoomTable);
        chooseRoomPanel.add(scrollPane);
        scrollPane.setBounds(180, 100, 600, 300);
        //chooseRoomPanel.add(chooseRoomTable,BorderLayout.CENTER);
        JTableHeader tableHeader = chooseRoomTable.getTableHeader();
        chooseRoomPanel.add(tableHeader);
        JButton addBasketButton = new JButton("Add to Basket");
        addBasketButton.setBounds(300, 450, 300, 35);
        Image shoppingCartImg = new ImageIcon(this.getClass().getResource("shopping-cart-icon.png")).getImage();
        addBasketButton.setIcon(new ImageIcon(shoppingCartImg));
        addBasketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                tabbedPane.setSelectedIndex(2);
            }
        });
        chooseRoomPanel.add(addBasketButton);
        tabbedPane.addTab(CHOOSE_ROOM, chooseRoomPanel);
    }

    private void initializeChooseServicePanel(final JTabbedPane tabbedPane) {
        chooseServicePanel = new JPanel();
        tabbedPane.addTab(EXTRA_SERVICE, chooseServicePanel);
        chooseServicePanel.setLayout(null);

        JLabel enterEmailText = new JLabel("Please enter your Email address to confirm your booking:");
        enterEmailText.setBounds(175, 300, 500, 22);
        chooseServicePanel.add(enterEmailText);

        JLabel extraServiceText = new JLabel("Do you need extra service?");
        extraServiceText.setBounds(175, 100, 300, 30);
        chooseServicePanel.add(extraServiceText);

        JTextField emailEditField = new JTextField();
        emailEditField.setBounds(171, 332, 245, 30);
        chooseServicePanel.add(emailEditField);
        emailEditField.setColumns(10);

        //this.TestTable();
        //chooseServicePanel.add(chooseRoomTable);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(419, 433, 93, 23);
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                tabbedPane.setSelectedIndex(3);
            }
        });
        chooseServicePanel.add(btnSubmit);
    }

    private void initializeCheckOutPanel(JTabbedPane tabbedPane) {

        checkOutPanel = new JPanel();
        tabbedPane.addTab(RECEIPT, checkOutPanel);
        checkOutPanel.setLayout(null);

        JLabel failedBookingText = new JLabel("What a pity, your booking is failed.");
        failedBookingText.setBounds(175, 300, 300, 30);
        checkOutPanel.add(failedBookingText);
    }

    private void generateRoomsTable() {
        chooseRoomTable = new JTable();
        chooseRoomTableModel = new DefaultTableModel() {
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
        chooseRoomTable.setModel(chooseRoomTableModel);
        chooseRoomTableModel.addColumn("Select");
        chooseRoomTableModel.addColumn("Room Type");
        chooseRoomTableModel.addColumn("Amount");
        chooseRoomTableModel.addColumn("Price");

    }

    public void invalidDate(String errorInfo) {
        JOptionPane.showMessageDialog(null, errorInfo);
    }

    void drawConfirmMessage(String Message) {
        JOptionPane.showMessageDialog(null, Message);
    }

    public void drawTotalPrice(double totalPrice) {
        JLabel priceLabel = new JLabel();
        // Your Booking is processed successfully.Your booking ID is
        priceLabel.setText(String.valueOf(totalPrice));
        priceLabel.setBounds(175, 100, 500, 30);
        checkOutPanel.add(priceLabel);
    }

    void drawSuccessDetails(String bookingDetails) {
        JLabel successMessage = new JLabel();
        successMessage.setText(bookingDetails);
        successMessage.setBounds(175, 100, 500, 30);
        checkOutPanel.add(successMessage);
    }

    void drawFailure(String failedService) {
        JLabel failureMessage = new JLabel();
        failureMessage.setText(failedService);
        failureMessage.setBounds(175, 100, 500, 30);
        checkOutPanel.add(failureMessage);
    }
}
