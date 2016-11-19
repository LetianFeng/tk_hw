package Client;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import GUI.GameGui;
import GUI.GameGuiInterface;
import Server.GameServerInterface;

public class GameClient extends UnicastRemoteObject implements GameClientInterface, GameClientGuiInterface{

	private String serverURL;
	private String username;

	// These two values are only used to test communication between server and client
	private int minionID;
	private int x;
	private int y;

	private GameGuiInterface gameGui;

	private static GameServerInterface server;

	private GameClient(String url) throws RemoteException {
		super();
		serverURL = url;
		gameGui = new GameGui(this);
		gameGui.openLoginWindow();
	}

	public String getUsername() {
		return this.username;
	}

	/*
	private void refreshMinion(int x, int y) {
		gameGui.drawMinion(x, y, 1);
	}*/

	@Override
	public void minionChanged(int newID, int newX, int newY, Map<GameClientInterface, Integer> userScores) throws RemoteException, MalformedURLException, NotBoundException {

		System.out.println();
		// paint new minion
		this.minionID = newID;
        this.x = newX;
        this.y = newY;
		drawScores(userScores);
	}


	private void drawScores(Map<GameClientInterface, Integer> userScores) throws RemoteException {

		System.out.println("Prepare for the Scores.");
		int gamerOrder = 0;
		// TODO need to sort entries
		for (Map.Entry<GameClientInterface, Integer> entry : userScores.entrySet()) {
			GameClientInterface gamer = entry.getKey();
			int score = entry.getValue();
			// paint the score on screen
			//System.out.print(gamer.getUsername()+": ");
			//System.out.println(score);
			gameGui.drawScore(gamer.getUsername(), score, gamerOrder);
			gamerOrder += 1;
		}
	}

	@Override
	public boolean login(String username) {

		try {
			this.username = username;
			if (server.login(this)) {
				System.out.println("Client: Login succeed!");
				gameGui.closeLoginWindow();
				gameGui.openGameWindow();
				return true;
			} else {
				this.username = null;
				System.out.println("Username already exist, try another!");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Client meets problem with connecting server!");
			return false;
		}
	}

	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public void feedMinion(int minionID) {
        String notification;
        try {
            if (server.checkMinion(minionID, this))
                notification = "Nice, you got it!";
            else
                notification = "Sorry, somebody is faster. :( ";
            gameGui.drawNotification(notification);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

	}

	public static void main(String args[]) {
		System.out.println("Looking for game server");

		try {

			server =  (GameServerInterface) Naming.lookup("rmi://localhost/GameServer");

			GameClient client = new GameClient("rmi://localhost/GameServer");

			client.feedMinion(client.minionID);

			Thread.sleep(1000);

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
