package Server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

import Client.GameClientInterface;

public class GameServer extends UnicastRemoteObject implements GameServerInterface, Runnable {

	private Random random;
	private volatile int x;
	private volatile int y;
	//private GameLogic gl;
	//private List<GameClientInterface> list = new Vector<>();

	private Map<GameClientInterface, Integer> userScores;
	// mapping from client to username maybe better, otherwise one client will request the client name from all other clients

	private GameServer() throws RemoteException {
		super();
		//gl = new GameLogic();
		// Initial the Score Mapping
        userScores = new HashTable<>();
        // Produce the initial Minion Coordinate
		random = new Random();
        x = random.nextInt();
		y = random.nextInt();
	}

	@Override
	public boolean checkMinion(int x, int y, int minionId, GameClientInterface client) throws RemoteException {
		if (validClick(x, y)) {
			userScores.put(client, userScores.get(client)+1);
			reproduceMinion();
			// Notify registered listeners
			try {
				notifyClients();
			} catch (MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
			return true;
		}
		else
			return false;
	}

	/**
	 * check whether the coordinate from client x, y is a valid click, could be optimize the distance algorithms.
	 */
	private boolean validClick(int client_x, int client_y) {

		return ((this.x-10 < client_x) && (client_x < this.x+10) && (this.y-10 < client_y) && (client_y < this.y+10));
	}

	/**
	 * reproduce the minion with coordinate when a click from client is valid.
	 */
	private void reproduceMinion() {
		x = random.nextInt();
		y = random.nextInt();
	}

	@Override
	public boolean login(GameClientInterface client) throws RemoteException {
		System.out.println("user logged in: " + client.getUsername());
		//list.add(client);
        userScores.put(client, 0);
		try {
			notifyClients();
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean logout(GameClientInterface client) throws RemoteException {
		System.out.println("user logged out: " + client.getUsername());
		//list.remove(client);
		userScores.remove(client);
		return true;
	}

	@Override
	public void run() {

	}

	private void notifyClients() throws MalformedURLException, NotBoundException {
		for (GameClientInterface client : userScores.) {
			// Notify, if possible a listener
			try {
				client.minionChanged(1, x, y, userScores);
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

			Thread thread = new Thread(gameServer);
			thread.start();
		} catch (RemoteException re) {
			System.err.println("Remote Error - " + re);
		} catch (Exception e) {
			System.err.println("Error - " + e);
		}
	}
}
