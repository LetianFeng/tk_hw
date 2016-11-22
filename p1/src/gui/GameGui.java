package gui;

import client.GameClientGuiInterface;
import client.GameClientInterface;
import server.Minion;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GameGui implements GameGuiInterface, Runnable{

	private int minionID;
	private GameClientGuiInterface client;
	private LoginFrame loginFrame;
	private GameFrame gameFrame;
	private int listX, listY, minionX, minionY;

	public GameGui(GameClientGuiInterface client) {
		this.client = client;
		listX = listY = 10;
		minionX = minionY = 0;
	}

	@Override
	public void openLoginWindow() {
		System.out.println("GUI: open login Window: ");
		loginFrame = new LoginFrame(client);
		//loginFrame.setVisible(true);
		
		/*
		Random random = new Random();
		String username = Float.toString(random.nextFloat());
		System.out.println("GUI: input username: " + username);

		System.out.println("GUI: push login button");
		if (!client.login(username)) {
			System.out.println("GUI: Login failed, try another username or check network!");
			System.out.println();
		}
		*/
	}

	@Override
	public void closeLoginWindow() {
		loginFrame.dispose();
		/*
		System.out.println("GUI: Login succeed!");
		System.out.println("GUI: close login window:");
		System.out.println();
		*/
	}

	@Override
	public void openGameWindow(Minion minion) {
		gameFrame = new GameFrame(client);
		gameFrame.setVisible(true);
		drawMinion(minion.x, minion.y, minion.minionID);
        drawScores(minion.userScores);
		/*
		System.out.println("GUI: open game window:");
		drawMinion(minion.x, minion.y, minion.minionID);
		drawScores(minion.userScores);

		// click minion for 10 times
		try {
			for (int i=0; i<10; i++) {
				Thread.sleep(1000);
				client.feedMinion(minionID);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		//closeGameWindow();
	}

	@Override
	public void closeGameWindow() {
		client.logout();
		gameFrame.dispose();
		System.out.println("GUI: close game window!");
	}

	@Override
	public void drawMinion(int x, int y, int minionId) {
		System.out.println("GUI: receive a new minion: x: " + x + ", y: " + y + ", ID: " + minionId);
		//this.minionID = minionId;
		final String id = Integer.toString(minionId);
		final JLabel minionLabel = new JLabel(id);
		minionLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				client.feedMinion(Integer.valueOf(id));
				minionLabel.setVisible(false);
			}
		}
		);
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource("Minion-Dancing-icon-small.png"));
		minionLabel.setIcon(imgIcon);
		minionLabel.setBounds(x, y, 128, 134);
		gameFrame.getContentPane().add(minionLabel);
	}

	@Override
	public void drawScores(Map<GameClientInterface, Integer> userScores){

		System.out.println("GUI: Prepare for the Scores:");
		int gamerOrder = 1;

		// sort scores and draw it
		int userScoresSize = userScores.size();
		for (int i=0; i<userScoresSize; i++, gamerOrder ++) {
			Map.Entry<GameClientInterface, Integer> highestScore = null;

			for (Map.Entry<GameClientInterface, Integer> entry : userScores.entrySet()) {
				if (highestScore == null)
					highestScore = entry;
				highestScore = (highestScore.getValue() > entry.getValue()) ? highestScore : entry;
			}
			userScores.remove(highestScore.getKey());

			GameClientInterface gamer = highestScore.getKey();
			try {
				drawScore(gamer.getUsername(), highestScore.getValue(), gamerOrder);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	private void drawScore(String username, int score, int gamerOrder) {
		System.out.println("GUI: username: " + username + ", score: " + score + ", game oder: " + gamerOrder);
		
		JLabel lbGamerOrder = new JLabel(Integer.toString(gamerOrder));
		lbGamerOrder.setBounds(listX, listY, 14, 15);
		gameFrame.getContentPane().add(lbGamerOrder);
		System.out.printf("lbPlayer added"+ lbGamerOrder.getText()+"\n");
		listX = listX + 20;
		JLabel lbPlayer = new JLabel(username+": ");
		lbPlayer.setBounds(listX, listY, 54, 15);
		gameFrame.getContentPane().add(lbPlayer);
		System.out.printf("lbPlayer added"+ lbPlayer.getText()+"\n");
		listX = listX + 100;
		JLabel lbScore = new JLabel(Integer.toString(score));
		lbScore.setBounds(listX, listY, 54, 15);
		gameFrame.getContentPane().add(lbScore);
		System.out.printf("lblScore added"+ lbScore.getText()+"\n");
		listX = 10;
		listY = listY + 20;
		
		gameFrame.revalidate();
		gameFrame.repaint();
	}

	@Override
	public void cleanScreen() {
		System.out.println("clean screen");
		listX = listY = 10;
		minionX = minionY = 0;
		gameFrame.getContentPane().removeAll();
	}

	@Override
	public void drawNotification(String notification) {
		System.out.println("GUI: " + notification);
		MessageFrame notifyFrame = new MessageFrame(notification);
	}

	@Override
	public void run() {

	}
}
