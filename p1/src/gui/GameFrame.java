package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import client.GameClientGuiInterface;
import server.Minion;

public class GameFrame extends JFrame{
	//components
		private JTextField textUsername;
	    private JPasswordField textPassword;
	    private JLabel lbUsername;
	    private JLabel lbPassword;
	    private JButton btnLogin;
	    private JButton btnCancel;
		
	    private GameClientGuiInterface client;

		public GameFrame(GameClientGuiInterface client) {
			this.client = client;
			initialize();
		}
		
		protected void initialize() {
			this.setBounds(100, 100, 650, 400);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.getContentPane().setLayout(null);
		}
}
