package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import client.GameClient;


public class GameServer extends UnicastRemoteObject implements GameServerInterface, Runnable  {

    private int x;
    private int y;
    
    private boolean changed;

    private Map<String, String> userPasswords;

    private Map<String, Integer> userPoints;

    private List<GameClient> userInstances;

    public GameServer() throws RemoteException {
        super();
        x = 0;
        y = 0;
        userPasswords = new HashMap<>();
        userPoints = new HashMap<>();
        userInstances = new LinkedList<GameClient>();
        changed = false;
    }

    public static void main(String args[]) throws Exception {
        System.out.println("RMI server started");

        try { //special exception handler for registry creation
            LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created.");
        } catch (RemoteException e) {
            //do nothing, error means registry already exists
            System.out.println("java RMI registry already exists.");
        }

        //Instantiate RmiServer

        GameServer gameServer = new GameServer();

        // Bind this object instance to the name "RmiServer"
        Naming.rebind("//localhost/GameServer", gameServer);
        System.out.println("PeerServer bound in registry");
        

		Thread thread = new Thread(gameServer);
		thread.start();
        
    }


    /**
     *  use 2 parameters to create an account
     *  
     *  @return true, if no same username on server, otherwise false
     */
	@Override
	public boolean register(String username, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * use 2 parameter to log in
	 * 
	 * @return corresponding client instance, if both parameter are correct
	 */
    @Override
    public GameClient login(String username, String password) throws RemoteException {
        // TODO delete functionality of register(u, p)
        if (!userPasswords.containsKey(username)) {
        	userPasswords.put(username, password);
        	GameClient gameClient = new GameClient(username, password);
        	userInstances.add(gameClient);
            System.out.println("new user registered!");
            return gameClient;
        }
        else if (userPasswords.get(username).equals(password)) {
            System.out.println("user " + username + " logged in!");
            userPoints.put(username, 0);
            GameClient gameClient = new GameClient(username, password);
        	userInstances.add(gameClient);
            return gameClient;
        }
        
        return null;
    }
	
    /**
     * 
     * @return password of username
     */
    public String findPassword(String username) {
    	// TODO implement in the right way!!!
    	GameClient gameClient = userInstances.get(0);
        return gameClient.getPassword();
    }


    /**
     * generate 2 random integer for new minion
     */
    private int[] randomNewXY() {
        // TODO
        return null;
    }

    /**
     * will be remotely called by client
     */
    @Override
    public boolean sendChanges(int x, int y, String username) throws RemoteException {
        //TODO
    	this.x = x;
    	this.y = y;
    	this.changed = true;
    	/*
    	if (this.x == x && this.y == y) {
    		// find client in the list, point++
    		GameClient gameClient = userInstances.get(0);
    		// update points in map
            // randomNewXY();
    		this.x++;
    		this.y++;
    		gameClient.x++;
    		gameClient.y++;
    		
            // distributeChangesToClients();
    		return true;
    	}*/
    	return true;
    }

    /**
     * after understanding rmi callback, implement this method
     */
    private void distributeChangesToClients() throws RemoteException {
    	// TODO
        // for c : userInstances
        //      update c with new XY & point
        //      c.loadChanges()
    	for (GameClient client : this.userInstances) {
			try {
				client.changesHappened(x, y);
			} catch (RemoteException re) {
				System.out.println("removing listener -" + client);
				// Remove the listener
			}
		}
    }

    /**
     * find client instance in the list, and then return
     */
	@Override
	public GameClient loadChanges(String username) throws RemoteException {
		// TODO Auto-generated method stub

    	GameClient gameClient = userInstances.get(0);
    	
        return gameClient;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (this.changed) {
				System.out.println("Server: " + this.x + " " + this.y);
				this.changed = false;
				try {
					distributeChangesToClients();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
