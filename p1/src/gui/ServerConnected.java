package gui;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerConnected {
	private JFrame frame;
	private JLabel lblH;
	private JLabel ServerConnected;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerConnected window = new ServerConnected();
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
	public ServerConnected() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ServerConnected = new JLabel("Server is connected successfully");
		ServerConnected.setFont(new Font("Arial", Font.BOLD, 12));
		ServerConnected.setBounds(175, 64, 200, 100);
		frame.getContentPane().add(ServerConnected);
		
		lblH = new JLabel("");
	    Image img = new ImageIcon(this.getClass().getResource("Minion-Happy-icon.png")).getImage();
	    lblH.setIcon(new ImageIcon(img));
		lblH.setBounds(52, 63, 200, 150);
		frame.getContentPane().add(lblH);
	}
}
