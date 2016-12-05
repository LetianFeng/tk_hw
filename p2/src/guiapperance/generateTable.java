package guiapperance;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.BorderLayout;

public class generateTable {

		private JFrame frame;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						generateTable window = new generateTable();
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
		public generateTable() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frame = new JFrame();
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//	frame.getContentPane().setLayout(null);
			
			
			String[] columnNames = { "Room type", "Amount", "Price", "Book" };
			Object[][] data ={{"Double room",new Integer(3),"60",new Boolean(true)},{"Single room","1","40","1"},{"Double room","2","60", "0"},{"Double room","2","60", "0"}};
		    JTable table = new JTable(data, columnNames);

			frame.getContentPane().add(table,BorderLayout.CENTER);
			JTableHeader tableheader = table.getTableHeader();

			frame.getContentPane().add(tableheader,BorderLayout.NORTH);
		
	}
	}	
	

