package Client;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import GUI.GameGui;
import GUI.GameGuiInterface;
import Server.GameServerInterface;
import Server.Minion;

public class GameClient extends UnicastRemoteObject implements GameClientInterface, GameClientGuiInterface{

	private String serverURL;
	public String username;

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
        gameGui.drawMinion(newX, newY, newID);
		gameGui.drawScores(userScores);
	}

	@Override
	public boolean login(String username) {

		try {
			this.username = username;
            Minion minion = server.login(this);
			if (minion != null) {
				System.out.println("Client: Login succeed!");
				gameGui.closeLoginWindow();
				gameGui.openGameWindow(minion);
				return true;
			} else {
				this.username = null;
				System.out.println("Username already exist, try another!");
				return false;
			}
		} catch (Exception e) {
            e.printStackTrace();
			System.out.println("Client meets problem with connecting server!");
			return false;
		}
	}

	@Override
	public boolean logout() {
        try {
            boolean logoutSuccess = server.logout(this);
            System.out.println(logoutSuccess ? "logout successful" : "logout failure");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

			//Thread.sleep(1000);

			//System.out.println(server.logout(client) ? "logout successful" : "logout failure");

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
