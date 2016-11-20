package Server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

import Client.GameClientGuiInterface;
import Client.GameClientInterface;

public class GameServer extends UnicastRemoteObject implements GameServerInterface {

	private Random random;
	private volatile int x;
	private volatile int y;
	private volatile int minionID;
	private boolean minionChanged;
	//private GameLogic gl;
	//private List<GameClientInterface> list = new Vector<>();

	private Map<GameClientInterface, Integer> userScores;
	// mapping from client to username maybe better, otherwise one client will request the client name from all other clients

	private GameServer() throws RemoteException {
		super();
		//gl = new GameLogic();
		// Initial the Score Mapping
        userScores = new HashMap<>();
        // Produce the initial Minion Coordinate
		random = new Random();
        x = random.nextInt();
		y = random.nextInt();
		minionID = random.nextInt();
		minionChanged = false;
	}

	@Override
	public void checkMinion(int minionID, GameClientInterface client) throws RemoteException {

		if (this.minionID == minionID && !minionChanged) {
			minionChanged = true;
			reproduceMinion();
			userScores.put(client, userScores.get(client)+1);
			client.sendNotification("Nice, you got it!");
			// Notify registered listeners
			try {
				notifyClients();
			} catch (MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
			minionChanged = false;
		} else
			client.sendNotification("Sorry, somebody is faster. :( ");
	}

	/*
	 * check whether the coordinate from client x, y is a valid click, could be optimize the distance algorithms.
	 */
	/*
	private boolean validClick(int client_x, int client_y) {

		return ((this.x-10 < client_x) && (client_x < this.x+10) && (this.y-10 < client_y) && (client_y < this.y+10));
	}*/

	/**
	 * reproduce the minion with coordinate when a click from client is valid.
	 */
	private void reproduceMinion() {
		x = random.nextInt();
		y = random.nextInt();
		minionID = random.nextInt();
	}

	@Override
	public Minion login(GameClientInterface client) throws RemoteException {
		if (!userScores.keySet().contains(client)) {
			System.out.println("user logged in: " + client.getUsername());
			try {
				notifyClients();
			} catch (MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
			userScores.put(client, 0);
			return new Minion(x, y, minionID, userScores);
		} else {
			return null;
		}
	}

	@Override
	public boolean logout(GameClientInterface client) throws RemoteException {
		System.out.println("user logged out: " + client.getUsername());
		//list.remove(client);
		userScores.remove(client);
		return true;
	}

	private void notifyClients() throws MalformedURLException, NotBoundException {

		for (GameClientInterface client : userScores.keySet()) {;
			// Notify, if possible a listener
			try {
				client.minionChanged(minionID, x, y, userScores);
			} catch (RemoteException re) {
				// Remove the listener
			}
		}
	}

	/*
	@Override
	public Map<GameClientInterface, Integer> pushScoresToClient() throws RemoteException {
		return this.userScores;
	}*/

	public static void main(String args[]) {
		System.out.println("Loading game server service");

		try {
			LocateRegistry.createRegistry(1099);
			GameServer gameServer = new GameServer();
			Naming.rebind("rmi://localhost/GameServer", gameServer);
		} catch (RemoteException re) {
			System.err.println("Remote Error - " + re);
		} catch (Exception e) {
			System.err.println("Error - " + e);
		}
	}
}
