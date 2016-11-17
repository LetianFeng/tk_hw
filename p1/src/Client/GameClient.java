package Client;


import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import Server.GameServerInterface;

public class GameClient extends UnicastRemoteObject implements
GameClientInterface{

	private String serverURL;
	private String username;
	
	private Map<String, Integer> competitor;
	
	public GameClient(String url, String name) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		serverURL = url;
		username = name;
		competitor = new HashMap<String, Integer>();
	}
	
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public void minionChanged(int x, int y) throws RemoteException, MalformedURLException, NotBoundException {
		// TODO Auto-generated method stub
		System.out.println("New x : " + x + " y: " + y);
		
		GameServerInterface server = (GameServerInterface) Naming.lookup(serverURL);
		
		// simulate a user feed the minion & send it to server
		if (x % 10 == 0 || y % 10 == 0)
			server.setMinion(x+10, y+10);
		
		if (x >= 30 || y >= 30)
			System.out.println(server.logout(this) ? "logout successful" : "logout failure");
	}
	
	public void login() throws MalformedURLException, RemoteException, NotBoundException {
		GameServerInterface server = (GameServerInterface) Naming.lookup(serverURL);

		int[] xy = server.login(this);
		System.out.println("Original x : " + xy[0] + " y: " + xy[1]);
	}

	public static void main(String args[]) {
		System.out.println("Looking for game server");
		
		try {
			
			GameClient client = new GameClient("rmi://localhost/TemperatureSensor", "abc");
			client.login();
			
		} catch (NotBoundException nbe) {
			System.out.println("No sensors available");
		} catch (RemoteException re) {
			System.out.println("RMI Error - " + re);
		} catch (Exception e) {
			System.out.println("Error - " + e);
		}
	}
    
}
