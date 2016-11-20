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

	private boolean loginOrGameWindow;

	public GameGui(GameClientGuiInterface client) {
		this.client = client;
	}

	@Override
	public void openLoginWindow() {
		System.out.println("GUI: open login Window: ");
		loginOrGameWindow = true;

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
		loginOrGameWindow = false;
		drawMinion(minion.x, minion.y, minion.minionID);
		drawScores(minion.userScores);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client.feedMinion(minionID);
		closeGameWindow();
	}

	@Override
	public void closeGameWindow() {
		client.logout();
		System.out.println("GUI: close game window!");
	}

	@Override
	public void drawMinion(int x, int y, int minionId) {
		System.out.println("GUI: recerve a new minion: x: " + x + ", y: " + y + ", ID: " + minionId);
		this.minionID = minionId;
	}

	@Override
	public void drawScores(Map<GameClientInterface, Integer> userScores){

		System.out.println("GUI: Prepare for the Scores:");
		int gamerOrder = 0;
		// TODO need to sort entries
		for (Map.Entry<GameClientInterface, Integer> entry : userScores.entrySet()) {
			GameClientInterface gamer = entry.getKey();
			int score = entry.getValue();
			// paint the score on screen
			//System.out.print(gamer.getUsername()+": ");
			//System.out.println(score);
			try {
				drawScore(gamer.getUsername(), score, gamerOrder);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			gamerOrder += 1;
		}
	}

	public void drawScore(String username, int score, int gamerOrder) {
		System.out.println("GUI: username: " + username + ", score: " + score + ", game oder: " + gamerOrder);
	}

	@Override
	public void cleanScreen() {

	}

	@Override
	public void drawNotification(String notification) {
		System.out.println("GUI: " + notification);
	}

	@Override
	public boolean getWindowType() {
		return loginOrGameWindow;
	}

	@Override
	public void run() {

	}
}
