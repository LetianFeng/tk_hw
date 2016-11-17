package GUI;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter; 

public class GameFrame {

	public JFrame Gframe;

	/**
	 * Launch the application.
	 */
	public static void gameFrame(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame window = new GameFrame();
					window.Gframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		
		Gframe = new JFrame();
		Gframe.setBounds(400, 100, 650, 400);
		Gframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Gframe.getContentPane().setLayout(null);

		
		JLabel lblPlayer = new JLabel("Player1:");
		lblPlayer.setBounds(10, 10, 54, 15);
		Gframe.getContentPane().add(lblPlayer);
		
		JLabel lblPlayer_1 = new JLabel("Player2:");
		lblPlayer_1.setBounds(10, 32, 54, 15);
		Gframe.getContentPane().add(lblPlayer_1);
		
		JLabel lblName = new JLabel("Name1");
		lblName.setBounds(74, 10, 54, 15);
		Gframe.getContentPane().add(lblName);
		
		JLabel lblName_1 = new JLabel("Name2");
		lblName_1.setBounds(74, 32, 54, 15);
		Gframe.getContentPane().add(lblName_1);
		
		JLabel lblScore = new JLabel("Score:");
		lblScore.setBounds(129, 10, 54, 15);
		Gframe.getContentPane().add(lblScore);
		
		JLabel lblScore_2 = new JLabel("Score:");
		lblScore_2.setBounds(129, 32, 41, 15);
		Gframe.getContentPane().add(lblScore_2);
		
		JLabel lblScore_1 = new JLabel("Score1");
		lblScore_1.setBounds(180, 10, 54, 15);
		Gframe.getContentPane().add(lblScore_1);
		
		JLabel lblScore_3 = new JLabel("Score2");
		lblScore_3.setBounds(180, 32, 54, 15);
		Gframe.getContentPane().add(lblScore_3);
		  
		
        Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("E:/TK1project/p1/src/GUI/bananas-icon.png");
		Point cursorHotSpot = new Point(16, 16);
		Cursor c = toolkit.createCustomCursor(image ,cursorHotSpot, "img");
		Gframe.setCursor (c);
	
		
		  int i;
	      for( i = 0; i < 5; i++ ){
		JLabel lblM = new JLabel("");
/*		lblM.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				Cursor cursor = new Cursor(Cursor.);
			}
		});
		**/
		lblM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblM.setVisible(false);
			}
		});
		
		Image img = new ImageIcon(this.getClass().getResource("Minion-Dancing-icon-small.png")).getImage();
	    lblM.setIcon(new ImageIcon(img));
        Number x = new Number();
        Number y = new Number();
		lblM.setBounds(x.generatex(), y.generatey(), 128, 134);
	    //lblM.setBounds(200, 260, 128, 134);
	    //x=0-580,y=0-260
		Gframe.getContentPane().add(lblM);
	}
	}
	
}
