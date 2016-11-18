package Client;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import GUI.GameGui;
import GUI.GameGuiInterface;
import Server.GameServerInterface;

public class GameClient extends UnicastRemoteObject implements GameClientInterface{

	private String serverURL;
	private String username;

	// These two values are only used to test communication between server and client
	private int x_from_server;
	private int y_from_server;

	private GameGuiInterface gameGui;

	private static GameServerInterface server;
	
	private GameClient(String url, String name) throws RemoteException {
		super();
		serverURL = url;
		username = name;
		gameGui = new GameGui();
		gameGui.initialGUI();
	}
	
	public String getUsername() {
		return this.username;
	}

	private void refreshMinion(int x, int y) {
		gameGui.drawMinion(x, y, 1);
	}

	@Override
	public void minionChanged(int x, int y) throws RemoteException, MalformedURLException, NotBoundException {
		// try to pull all the information from the server
		System.out.println("New x : " + x + " y: " + y);
		// paint new minion
		refreshMinion(x, y);
		// pull the score list
		Map<GameClientInterface, Integer> userScores = server.pushScoresToClient();
		System.out.println(userScores.size());
		drawScores(userScores);
	}

	private void drawScores(Map<GameClientInterface, Integer> userScores) throws RemoteException {
		System.out.println("Prepare for the Scores.");
		int gamerOrder = 0;
		for (Map.Entry<GameClientInterface, Integer> entry : userScores.entrySet()) {
			GameClientInterface gamer = entry.getKey();
			int score = entry.getValue();
			// paint the score on screen
			System.out.print(gamer.getUsername()+": ");
			System.out.println(score);
			gameGui.drawScore(gamer.getUsername()+":", score, gamerOrder);
			gamerOrder += 1;
		}
	}

	private void feedValidClick(int x, int y, int minion_id, GameServerInterface server, GameClientInterface client) throws RemoteException {
		String notification;
		if (server.checkMinion(x, y, minion_id, client))
			notification = "Nice, you got it!";

		else
			notification = "Sorry, somebody is faster. :( ";
		System.out.println(notification);
		gameGui.drawNotification(notification);
	}
	
	private void login(GameServerInterface server) throws MalformedURLException, RemoteException, NotBoundException {

		int[] xy = server.login(this);

		this.x_from_server = xy[0];
		this.y_from_server = xy[1];

		//System.out.println("Original x : " + xy[0] + " y: " + xy[1]);
		//Map<GameClientInterface, Integer> userScores = server.pushScoresToClient();
		//drawScores(userScores);
	}

	public static void main(String args[]) {
		System.out.println("Looking for game server");
		
		try {
			Random random = new Random();

			GameClient client = new GameClient("rmi://localhost/GameServer", Float.toString(random.nextFloat()));

			server =  (GameServerInterface) Naming.lookup(client.serverURL);

			client.login(server);

			Thread.sleep(3000);

			client.feedValidClick(client.x_from_server, client.y_from_server, 1, server, client);

			Thread.sleep(4000);

			System.out.println(server.logout(client) ? "logout successful" : "logout failure");

			System.exit(0);

		} catch (NotBoundException nbe) {
			System.out.println("No game server available");
		} catch (RemoteException re) {
			System.out.println("RMI Error - " + re);
		} catch (Exception e) {
			System.out.println("Error - " + e);
		}
	}
    
}
