package Server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

import Client.GameClientInterface;

public class GameServer extends UnicastRemoteObject implements
		GameServerInterface, Runnable {

	private volatile int x;
	private volatile int y;
	private List<GameClientInterface> list = new Vector<GameClientInterface>();

	private Map<String, Integer> userPoints;
	private Map<String, GameClientInterface> userInstances;
	
	public GameServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		x = 0;
		y = 0;
		userPoints = new HashMap<String, Integer>();
		userInstances = new HashMap<String, GameClientInterface>();
	}
	@Override
	public boolean setMinion(int x, int y) throws RemoteException {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		return true;
	}

	@Override
	public int[] login(GameClientInterface client) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("user logged in: " + client.getUsername());
		list.add(client);
		return new int[] {x, y};
	}

	@Override
	public boolean logout(GameClientInterface client) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("user logged out: " + client.getUsername());
		list.remove(client);
		return true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Random r = new Random();
		for (;;) {
			try {
				// Sleep for a random amount of time
				int duration = 500;
				// Check to see if negative, if so, reverse
				if (duration < 0)
					duration = duration * -1;
				Thread.sleep(duration);
			} catch (InterruptedException ie) {
			}
			// Get a number, to see if temp goes up or down
			int num = r.nextInt();
			if (num < 0) {
				x += 1;
			} else {
				y += 1;
			}
			// Notify registered listeners
			try {
				
				notifyClients();
			} catch (MalformedURLException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void notifyClients() throws MalformedURLException, NotBoundException {
		// TODO Auto-generated method stub
		if (list.isEmpty())
			return;
		for (GameClientInterface client : list) {
			// Notify, if possible a listener
			try {
				client.minionChanged(x, y);
			} catch (RemoteException re) {
				// Remove the listener
			}
		}
	}

	public static void main(String args[]) {
		System.out.println("Loading game server service");
		// Only required for dynamic class loading
		// System.setSecurityManager ( new RMISecurityManager() );
		try {
			LocateRegistry.createRegistry(1099);
			// Load the service
			GameServer sensor = new GameServer();
			// Check to see if a registry was specified
			// Registration format //registry_hostname:port service
			// Note the :port field is optional
			String registration = "rmi://localhost/TemperatureSensor";
			// Register with service so that clients can
			// find us
			Naming.rebind(registration, sensor);
			// Create a thread, and pass the sensor server.
			// This will activate the run() method, and
			// trigger regular temperature changes.
			Thread thread = new Thread(sensor);
			thread.start();
		} catch (RemoteException re) {
			System.err.println("Remote Error - " + re);
		} catch (Exception e) {
			System.err.println("Error - " + e);
		}
	}

/*
	@Override
	public int[] getMinion() throws RemoteException {
		// TODO Auto-generated method stub
		int xy[] = { x, y };
		return xy;
	}
*/

}
