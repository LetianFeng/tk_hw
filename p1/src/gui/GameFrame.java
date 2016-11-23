package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import client.GameClientGuiInterface;

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
			this.setBounds(Constant.gameFrameX, Constant.gameFrameY, Constant.gameFrameLength, Constant.gameFrameWidth);
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			Toolkit toolkit = Toolkit.getDefaultToolkit();

			System.out.print(System.getProperty("user.dir"));
		Image image = toolkit.getImage("../src/gui/bananas-icon.png");
		Point cursorHotSpot = new Point(16, 16);
		Cursor c = toolkit.createCustomCursor(image ,cursorHotSpot, "img");

		this.setCursor (c);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					client.logout();
				}
			}
			);
			this.getContentPane().setLayout(null);
		}
}
