package GUI;

import Client.GameClientGuiInterface;
import Client.GameClientInterface;

import java.util.Random;

public class GameGui implements GameGuiInterface{

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
		System.out.println("GUI username: " + username);

		if (client.login(username)) {
			System.out.println("GUI: Login succeed!");
		} else {
			System.out.println("GUI: Login failed, try another username!");
		}
	}

	@Override
	public void closeLoginWindow() {
		System.out.println("GUI: close login window:");
	}

	@Override
	public void openGameWindow() {
		System.out.println("GUI: open game window:");
		loginOrGameWindow = false;
	}

	@Override
	public void closeGameWindow() {

	}

	@Override
	public void drawMinion(int x, int y, int minionId) {

	}

	@Override
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
}
