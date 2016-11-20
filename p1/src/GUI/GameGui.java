package GUI;

import Client.GameClientGuiInterface;
import Client.GameClientInterface;
import Server.Minion;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Random;

public class GameGui implements GameGuiInterface, Runnable{

	private int minionID;

	private GameClientGuiInterface client;

	public GameGui(GameClientGuiInterface client) {
		this.client = client;
	}

	@Override
	public void openLoginWindow() {
		System.out.println("GUI: open login Window: ");

		Random random = new Random();
		String username = Float.toString(random.nextFloat());
		System.out.println("GUI: input username: " + username);

		System.out.println("GUI: push login button");
		if (!client.login(username)) {
			System.out.println("GUI: Login failed, try another username or check network!");
			System.out.println();
		}
	}

	@Override
	public void closeLoginWindow() {
		System.out.println("GUI: Login succeed!");
		System.out.println("GUI: close login window:");
		System.out.println();
	}

	@Override
	public void openGameWindow(Minion minion) {
		System.out.println("GUI: open game window:");
		drawMinion(minion.x, minion.y, minion.minionID);
		drawScores(minion.userScores);

		try {
			for (int i=0; i<10; i++) {
				Thread.sleep(1000);
				client.feedMinion(minionID);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		closeGameWindow();
	}

	@Override
	public void closeGameWindow() {
		client.logout();
		System.out.println("GUI: close game window!");
	}

	@Override
	public void drawMinion(int x, int y, int minionId) {
		System.out.println("GUI: receive a new minion: x: " + x + ", y: " + y + ", ID: " + minionId);
		this.minionID = minionId;
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
	}

	@Override
	public void cleanScreen() {
		System.out.println();
	}

	@Override
	public void drawNotification(String notification) {
		System.out.println("GUI: " + notification);
	}

	@Override
	public void run() {

	}
}
