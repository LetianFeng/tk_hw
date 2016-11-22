package gui;

import client.GameClientGuiInterface;
import client.GameClientInterface;
import game.*;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.Iterator;

public class GameGui implements GameGuiInterface, Runnable{

	//private int minionID;

	private GameClientGuiInterface client;

	public GameGui(GameClientGuiInterface client) {
		this.client = client;
	}

	@Override
	public void openLoginWindow() {
		System.out.println("GUI: open login Window: ");

		Random random = new Random();
		String username = "test_user";
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
	public void openGameWindow(Room room) {
		System.out.println("Client: Login succeed! Entered room " + room.getID());
		System.out.println("GUI: open game window:");
		Iterator it = room.getMinions().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<UUID, Minion> pair = (Map.Entry<UUID, Minion>)it.next();
			Minion minion_t = pair.getValue();
			drawMinion(minion_t.getX(), minion_t.getY(), minion_t.getMinionID());
		}
		/*for (Minion minion : room.getMinions()) {
			drawMinion(minion.getX(), minion.getY(), minion.getMinionID());		
		}*/
		
		drawScores(room.getPlayers());

		// click minion for 10 times
		try {
			for (int i=0; i<10; i++) {
				Thread.sleep(1000);
				client.feedMinion(room.getMinions().entrySet().iterator().next().getKey());
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
	public void drawMinion(float x, float y, UUID minionId) {
		System.out.println("GUI: receive a new minion: x: " + x + ", y: " + y + ", ID: " + minionId);
		//this.minionID = minionId;
	}

	@Override
	public void drawScores(Map<String, Player> players) {

		System.out.println("GUI: Prepare for the Scores(" + players.size() + "):");
		Map<String, Player> players_t = Util.sortPlayerByScoreDesc(players);
		
		try {
			int gamerOrder = 1;
			Iterator it = players_t.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Player> pair = (Map.Entry<String, Player>)it.next();
				Player player_t = pair.getValue();
				drawScore(player_t.getName(), player_t.getScore(), gamerOrder);
				gamerOrder++;
			}
			/*for (Player player : players) {
				drawScore(player.getName(), player.getScore(), gamerOrder);
				gamerOrder++;
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		// sort scores and draw it
		/*int userScoresSize = userScores.size();
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
		}*/
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
