package GUI;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Login {

	private JFrame frame;
	private JLabel lblUsername;
	private JTextField textField;
	private JLabel lblScore;
	private JTextField textField_1;
	private JLabel lblH;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnLogIn = new JButton("Log in");
		btnLogIn.setForeground(Color.BLACK);
		btnLogIn.setBackground(Color.LIGHT_GRAY);
		btnLogIn.setFont(new Font("Ebrima", Font.PLAIN, 12));
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			frame.dispose();
			GameFrame gameFrame = new GameFrame();
			gameFrame.Gframe.setVisible(true);
			
			//JOptionPane.showMessageDialog(null, "Hi,Shule");
			//lblUsername.setText("Hi, Xin");
			
			}
		});
		btnLogIn.setBounds(172, 158, 105, 35);
		frame.getContentPane().add(btnLogIn);
		
		lblUsername = new JLabel("UserName:");
		lblUsername.setBounds(175, 64, 81, 23);
		frame.getContentPane().add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(266, 65, 105, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblScore = new JLabel("Password:");
		lblScore.setBounds(175, 97, 81, 22);
		frame.getContentPane().add(lblScore);
		
		textField_1 = new JTextField();
		textField_1.setBounds(266, 100, 105, 24);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		lblH = new JLabel("");
	    Image img = new ImageIcon(this.getClass().getResource("Minion-Hello-icon.png")).getImage();
	    lblH.setIcon(new ImageIcon(img));
		lblH.setBounds(32, 43, 133, 128);
		frame.getContentPane().add(lblH);
	}
}
