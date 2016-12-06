package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.HashMap;
import com.toedter.calendar.JDateChooser;
import server.entry.Service;
import client.ClientGUIInterface;

public class Gui implements GuiClientInterface, Runnable {
	private BookingFrame bookingFrame;
	private JPanel panel;
	private JScrollPane jsp;
	private GridBagLayout layout;
	private GridBagConstraints c;
	private ArrayList<Service> roomList;
	private ClientGUIInterface client;
	private JLabel invalidDateLabel;
	private Service breakfastService;
	private Service rentCarService;
	private JDateChooser dateChooser1 = new JDateChooser();
	private JDateChooser dateChooser2 = new JDateChooser();
	private HashMap<String, Integer> booking = new HashMap<String, Integer>();

	public Gui(ClientGUIInterface client) {
		this.client = client;
		bookingFrame = new BookingFrame();
		panel = new JPanel(new GridBagLayout());
		roomList = new ArrayList<Service>();
		c = new GridBagConstraints();
		layout = new GridBagLayout();
		jsp = new JScrollPane();
		invalidDateLabel = new JLabel();
		invalidDateLabel.setVisible(false);
		invalidDateLabel.setForeground(Color.RED);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		c.fill = GridBagConstraints.NONE;
		panel.setLayout(layout);
		jsp.setViewportView(panel);
		bookingFrame.getContentPane().add(jsp);
	}
	
	public Gui() {
		bookingFrame = new BookingFrame();
		panel = new JPanel(new GridBagLayout());
		roomList = new ArrayList<Service>();
		c = new GridBagConstraints();
		invalidDateLabel = new JLabel();
		invalidDateLabel.setVisible(false);
		invalidDateLabel.setBackground(Color.RED);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		layout = new GridBagLayout();
		c.fill = GridBagConstraints.NONE;
		panel.setLayout(layout);
		jsp = new JScrollPane();
		jsp.setViewportView(panel);
		bookingFrame.getContentPane().add(jsp);
	}

	@Override
	public void drawService(ArrayList<Service> serviceList) {
		serviceManage(serviceList);
		panel.removeAll();
		JPanel datePanel = getDatePanel();
		JPanel roomPanel = getRoomPanel();
		panel.add(datePanel);
		panel.add(roomPanel);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 0.5;
		layout.setConstraints(datePanel, c);
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 1;
		layout.setConstraints(roomPanel, c);
		bookingFrame.revalidate();
		bookingFrame.repaint();
	}

	public void initializeAll() {
		panel.removeAll();
		JPanel datePanel = getDatePanel();
		JPanel adPanel = getAdPanel();
		panel.add(datePanel);
		panel.add(adPanel);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weighty = 0.5;
		layout.setConstraints(datePanel, c);
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1;
		layout.setConstraints(adPanel, c);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bookingFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public void drawSuccessDetails(String bookingDetails) {
		getNotificationPanel(bookingDetails, "New Booking");
		bookingFrame.revalidate();
		bookingFrame.repaint();
	}
	
	@Override
    public void drawFailure(String failedService) {
		getNotificationPanel(failedService, "New Booking");
		bookingFrame.revalidate();
		bookingFrame.repaint();
	}

	@Override
    public void invalidBooking(String errorInfo) {
		getNotificationPanel(errorInfo, "New Booking");
		bookingFrame.revalidate();
		bookingFrame.repaint();
    }
	
	@Override
	public void invalidDate(String errorInfo) {
		invalidDateLabel.setText(errorInfo);
		invalidDateLabel.setForeground(Color.RED);
		invalidDateLabel.setVisible(true);
		dateChooser1.setEnabled(true);
		dateChooser2.setEnabled(true);
		bookingFrame.revalidate();
		bookingFrame.repaint();
	}
	
	@Override
	public void lockGUIUntilServerFeedBack() {
		this.bookingFrame.setEnabled(false);
	}
	
	@Override
	public void unlockGUI() {
		this.bookingFrame.setEnabled(true);
	}

	@Override
	public void run() {

	}

	private JPanel getDatePanel() {
		JLabel label1 = new JLabel("Check-in");
		JLabel label2 = new JLabel("Check-out");
		JButton btn1 = new JButton("Submit");
		JLabel welcomeLabel = new JLabel("Welcome to Booking System", JLabel.CENTER);
		//JLabel notifyLabel = new JLabel("Select Checkin and Checkout Date");
		JPanel datePanel = new JPanel(new GridBagLayout());
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		invalidDateLabel.setVisible(false);
		dateChooser1.setEnabled(true);
		dateChooser2.setEnabled(true);
		datePanel.setLayout(layout);
		c.insets = new Insets(0,10,0,10);
		c.gridx = 0;
		c.gridy = 2;
		layout.setConstraints(label1, c);
		datePanel.add(label1);
		c.gridx = 1;
		c.gridy = 2;
		layout.setConstraints(label2, c);
		datePanel.add(label2);
		c.gridx = 0;
		c.gridy = 3;
		layout.setConstraints(dateChooser1, c);
		datePanel.add(dateChooser1);
		c.gridx = 1;
		c.gridy = 3;
		layout.setConstraints(dateChooser2, c);
		datePanel.add(dateChooser2);
		c.gridx = 0;
		c.gridy = 4;
		layout.setConstraints(invalidDateLabel, c);
		datePanel.add(invalidDateLabel);
		c.insets.set(10, 0, 0, 0);
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(btn1, c);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				invalidDateLabel.setVisible(false);
				Date checkinDate = dateChooser1.getDate();
				Date checkoutDate = dateChooser2.getDate();
				if(checkinDate == null || checkoutDate == null) {
					invalidDate("Please choose date");
				}
				else {
					dateChooser1.setEnabled(false);
					dateChooser2.setEnabled(false);
					System.out.println("date1: \n"+checkinDate.getDate());
					System.out.println("date1: \n"+checkoutDate.getDate());
					/*
					client.searchRooms(checkinDate, checkoutDate);
					*/
				}
			}
		});
		datePanel.add(btn1);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(welcomeLabel, c);
		welcomeLabel.setPreferredSize(new Dimension(200, 100));
		//welcomeLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		datePanel.add(welcomeLabel);
		/*
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(notifyLabel, c);
		datePanel.add(notifyLabel);
		*/
		datePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		datePanel.setBackground(java.awt.Color.YELLOW);
		return datePanel;
	}

	private JPanel getAdPanel() {
		JPanel adPanel = new JPanel(new GridBagLayout());
		GridBagLayout layout = new GridBagLayout();
		adPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		JLabel label1, label2, label3;
		try {
			URL url = getClass().getResource("image1.jpg");
			BufferedImage myImage = ImageIO.read(new File(url.getPath()));
			label1 = new JLabel(new ImageIcon(myImage));
			url = getClass().getResource("image2.jpg");
			myImage = ImageIO.read(new File(url.getPath()));
			label2 = new JLabel(new ImageIcon(myImage));
			url = getClass().getResource("image3.jpg");
			myImage = ImageIO.read(new File(url.getPath()));
			label3 = new JLabel(new ImageIcon(myImage));
		} catch (IOException ex) {
			System.out.printf("IO Exception\n");
			return null;
		}
		JTextArea textArea1 = new JTextArea();
		JTextArea textArea2 = new JTextArea();
		JTextArea textArea3 = new JTextArea();
		textArea1.setLineWrap(true);
		textArea1.setWrapStyleWord(true);
		textArea1.setEditable(false);
		textArea2.setLineWrap(true);
		textArea2.setWrapStyleWord(true);
		textArea2.setEditable(false);
		textArea3.setLineWrap(true);
		textArea3.setWrapStyleWord(true);
		textArea3.setEditable(false);
		textArea1.setText(Constants.ad1);
		textArea2.setText(Constants.ad2);
		textArea3.setText(Constants.ad3);
		c.gridx = 0;
		c.gridy = 0;
		layout.setConstraints(label1, c);
		adPanel.add(label1);
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(textArea1, c);
		adPanel.add(textArea1);
		c.gridx = 0;
		c.gridy = 2;
		layout.setConstraints(label2, c);
		adPanel.add(label2);
		c.gridx = 0;
		c.gridy = 3;
		layout.setConstraints(textArea2, c);
		adPanel.add(textArea2);
		c.gridx = 0;
		c.gridy = 4;
		layout.setConstraints(label3, c);
		adPanel.add(label3);
		c.gridx = 0;
		c.gridy = 5;
		layout.setConstraints(textArea3, c);
		adPanel.add(textArea3);
		adPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		return adPanel;
	}

	private JPanel getRoomPanel() {
		JPanel roomPanel = new JPanel(new GridBagLayout());
		GridBagLayout layout = new GridBagLayout();
		roomPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		JLabel label1 = new JLabel("RoomType");
		JLabel label2 = new JLabel("Price");
		JLabel label3 = new JLabel("MaxAmount");
		JLabel label4 = new JLabel("BookAmount");
		JLabel label5 = new JLabel("Confirm");
		JLabel label6 = new JLabel("ExtraService");
		final JLabel label7 = new JLabel("Please Enter Your Email Address:");
		final JCheckBox check1  = new JCheckBox("Rent Car");
		final JCheckBox check2  = new JCheckBox("Breakfast");
		final JComboBox<String> combo1 = new JComboBox<String>();
		final JComboBox<String> combo2 = new JComboBox<String>();
		final JTextField textField = new JTextField();
		if(rentCarService != null) {
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(getComboArray(rentCarService.availableAmount));
			combo1.setModel(model);
		}
		else {
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(getComboArray(0));
			combo1.setModel(model);
		}
		if(breakfastService != null) {
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(getComboArray(breakfastService.availableAmount));
			combo2.setModel(model);
		}
		else {
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(getComboArray(0));
			combo2.setModel(model);
		}
		combo1.setEnabled(false);
		combo2.setEnabled(false);
		//if(rentCarService == null) {
			check1.setEnabled(false);
		//}
		//if(breakfastService == null) {
			check2.setEnabled(false);
		//}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,10,0,10);
		layout.setConstraints(label1, c);
		roomPanel.add(label1);
		c.gridx = 1;
		c.gridy = 0;
		layout.setConstraints(label2, c);
		roomPanel.add(label2);
		c.gridx = 2;
		c.gridy = 0;
		layout.setConstraints(label3, c);
		roomPanel.add(label3);
		c.gridx = 3;
		c.gridy = 0;
		layout.setConstraints(label4, c);
		roomPanel.add(label4);
		c.gridx = 4;
		c.gridy = 0;
		layout.setConstraints(label5, c);
		roomPanel.add(label5);
		//JLabel label1 = new JLabel("RoomType");
		final ButtonGroup group = new ButtonGroup();
		final HashMap<String, JComboBox<String>> roomMap = new HashMap<String, JComboBox<String>>();
		int num = roomList.size();
		for(int i = 1; i < num + 1; i++) {
			c.gridx = 0;
			c.gridy = i;
			JRadioButton radioBtn = new JRadioButton(roomList.get(i-1).serviceName);
			group.add(radioBtn);
			radioBtn.addItemListener(new ItemListener() {
			    public void itemStateChanged(ItemEvent e) {
			    	if(rentCarService != null) {
						check1.setEnabled(true);
					}
					if(breakfastService != null) {
						check2.setEnabled(true);
					}
			    }
			});
			layout.setConstraints(radioBtn, c);
			roomPanel.add(radioBtn);
			c.gridx = 1;
			c.gridy = i;
			JLabel labelPrice = new JLabel(Double.toString(roomList.get(i-1).price));
			layout.setConstraints(labelPrice, c);
			roomPanel.add(labelPrice);
			c.gridx = 2;
			c.gridy = i;
			JLabel labelAmount = new JLabel(Double.toString(roomList.get(i-1).availableAmount));
			layout.setConstraints(labelAmount, c);
			roomPanel.add(labelAmount);
			c.gridx = 3;
			c.gridy = i;
			JComboBox<String> combo = new JComboBox<String>(getComboArray(roomList.get(i-1).availableAmount));
			layout.setConstraints(combo, c);
			roomPanel.add(combo);
			roomMap.put(radioBtn.getText(), combo);
		}
		c.gridx = 4;
		c.gridy = 1;
		JButton confirmBtn = new JButton("Reserve");
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				booking.clear();
				Enumeration<AbstractButton> enumRadioButton = group.getElements();
				while(enumRadioButton.hasMoreElements()) {
					AbstractButton btn = enumRadioButton.nextElement();
					if(btn.isSelected()) {
						String roomName = btn.getText();
						int amount = Integer.valueOf(roomMap.get(btn.getText()).getSelectedItem().toString());
						System.out.printf("Selected is: "+roomName+"\n");
						System.out.printf("Amount is: "+amount+"\n");
						booking.put(roomName, amount);
						if(check1.isSelected()) {
							amount = Integer.valueOf(combo1.getSelectedItem().toString());
							System.out.printf("rent car number: "+amount+"\n");
							booking.put(check1.getText(), amount);
						}
						if(check2.isSelected()) {
							amount = Integer.valueOf(combo2.getSelectedItem().toString());
							System.out.printf("breakfast number: "+amount+"\n");
							booking.put(check2.getText(), amount);
						}
						if(textField.getText().equals("")) {
							System.out.printf("email empty\n");
							label7.setForeground(Color.RED);
							bookingFrame.revalidate();
							bookingFrame.repaint();
							return;
						}
						else {
							label7.setForeground(Color.black);
						}
						//client.sendBooking(booking, textField.getText());
						Iterator<String> tempIter = booking.keySet().iterator();
						while(tempIter.hasNext()) {
							String key = tempIter.next();
							System.out.println("Service name: "+key+" Service amount: "+booking.get(key)+"\n");
						}
						System.out.println("Email Address is: "+textField.getText()+"\n");
						return;
					}
				}
				System.out.printf("no button selected\n");
			}
		});
		layout.setConstraints(confirmBtn, c);
		roomPanel.add(confirmBtn);
		c.gridx = 0;
		c.gridy = num+1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(label6, c);
		roomPanel.add(label6);
		c.gridx = 0;
		c.gridy = num+2;
		c.gridwidth = 1;
		layout.setConstraints(check1, c);
		roomPanel.add(check1);
		c.gridx = 1;
		c.gridy = num+2;
		layout.setConstraints(combo1, c);
		roomPanel.add(combo1);
		c.gridx = 2;
		c.gridy = num+2;
		layout.setConstraints(check2, c);
		roomPanel.add(check2);
		c.gridx = 3;
		c.gridy = num+2;
		layout.setConstraints(combo2, c);
		roomPanel.add(combo2);
		check1.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {
		            combo1.setEnabled(true);
		        } else {
		            combo1.setEnabled(false);
		        };
		    }
		});
		check2.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if(e.getStateChange() == ItemEvent.SELECTED) {
		            combo2.setEnabled(true);
		        } else {
		            combo2.setEnabled(false);
		        };
		    }
		});
		c.gridx = 0;
		c.gridy = num+3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(label7, c);
		roomPanel.add(label7);
		c.gridx = 0;
		c.gridy = num+4;
		layout.setConstraints(textField, c);
		roomPanel.add(textField);
		return roomPanel;
	}
	
	public void getNotificationPanel(String msg, String btnStr) {
		panel.removeAll();
		GridBagConstraints c = new GridBagConstraints();
		JTextArea textArea = new JTextArea();
		JButton btn1 = new JButton(btnStr);
		JButton btn2 = new JButton("EXIT");
		JScrollPane textJsp = new JScrollPane();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText(msg);
		textArea.setEditable(false);
		Font font = new Font("Serif",0,30);
		textArea.setFont(font);
		textJsp.setViewportView(textArea);
		int width = 150;//Math.max(btn1.getWidth(), btn2.getWidth());
		int height = 20;//Math.max(btn1.getHeight(), btn2.getHeight());
		//System.out.println("width is: "+width+", height is: "+height+"\n");
		btn1.setPreferredSize(new Dimension(width,height));
		btn2.setPreferredSize(new Dimension(width,height));
		textJsp.setPreferredSize(new Dimension((int)(width/0.3), 300));
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initializeAll();
			}
		});
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		c.gridx = 0;
		c.gridy = 1;
		layout.setConstraints(btn1, c);
		panel.add(btn1);
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.SOUTHEAST;
		layout.setConstraints(btn2, c);
		panel.add(btn2);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(textJsp, c);
		panel.add(textJsp);
		return;
	}
	
	private void serviceManage(ArrayList<Service> serviceList) {
		roomList.clear();
		breakfastService = null;
		rentCarService = null;
		for(Service service : serviceList) {
			if(service.isRoom) {
				roomList.add(service);
			}
			else if(service.serviceName == "rentCar")//question
				rentCarService = service;
			else if(service.serviceName == "breakfast")
				breakfastService = service;
			else
				System.out.println("Wrong service type, service name is: "+service.serviceName);
		}
	}
	
	private String[] getComboArray(int max) {
		if(max <0) {
			System.out.println("Wrong upper limit\n");
			return null;
		}
		if(max == 0) {
			String[] strArray = new String[1];
			strArray[0] = "0";
			return strArray;
		}
			
		String[] strArray = new String[max];
		for(int i = 0; i < max; i++) {
			strArray[i] = Integer.toString(i+1);
		}
		return strArray;
	}

}
