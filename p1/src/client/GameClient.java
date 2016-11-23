package client;

import java.rmi.*;
import java.rmi.server.*;
import java.util.UUID;

import gui.GameGui;
import gui.GameGuiInterface;
import server.GameServerInterface;
import game.*;

public class GameClient extends UnicastRemoteObject implements GameClientInterface, GameClientGuiInterface{

	private static final long serialVersionUID = 3088746649237351506L;

	public Room local_room = new Room(UUID.randomUUID());
	private GameGuiInterface gameGui;
	private static GameServerInterface server;

	private GameClient() throws RemoteException {
		super();
		try{
			gameGui = new GameGui(this);
			gameGui.openLoginWindow();
		}catch(Exception e) {
			server.logout(this, this.local_room.getID());
			e.printStackTrace();
		}
	}

    @Override
    public void sendNotification(String notification) {
        gameGui.drawNotification(notification);
    }

	@Override
	public void minionChanged(Minion minion, Player player) {
		if(gameGui.getGameFrame() != null) {
	        gameGui.cleanScreen();
	        if (minion == null && player != null) { 	
	        	// player has left the room
	        	if (this.local_room.getPlayers().containsKey(player.getName())) {
	        		this.local_room.getPlayers().remove(player.getName());
	        		gameGui.drawNotification("Player " + player.getName() 
	        										   + " has left the room.");
	        	} 
	        	// new play has entered the room
	        	else {
	        		this.local_room.getPlayers().put(player.getName(), player);
	        		gameGui.drawNotification("Player " + player.getName() 
	        									       + " has joined the room.");
	        	}
	        } 
	        // a play has fed a minion
	        else if (minion != null && player != null){
	        	gameGui.drawMinion(minion.getX(), minion.getY(), minion.getMinionID());
	        	Player player_t = local_room.getPlayers().get(player.getName());
	        	player_t.setScore(player_t.getScore() + 1);
	        }
	        gameGui.drawScores(this.local_room.getPlayers());
		}
	}

	@Override
	public boolean login(String username) {

		try {
            Room room = server.login(this, username, null);
			if (room != null) {
				System.out.println("Client: Login succeed! Entered room " + room.getID());
				// room entered will be copied to the local object only once at login
				// thereafter, only value update on the local copy
				this.local_room = room;
				gameGui.closeLoginWindow();
				gameGui.openGameWindow(this.local_room);
				return true;
			} else {
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
            boolean logoutSuccess = server.logout(this, local_room.getID());
            System.out.println(logoutSuccess ? "logout successful" : "logout failure");
            System.exit(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
	}

	@Override
	public void feedMinion(UUID minionID) {
        try {
            server.checkMinion(local_room.getID(), minionID, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
	}

    public static void main(String args[]) {
    	
		System.out.println("Looking for game server");
		try {
			String hostname = Util.defaultHost;
			String servername = Util.defaultHost;
			if (args.length > 1){
	            servername = args[0];
	            hostname = args[1];
	        }
			System.setProperty("java.rmi.server.hostname", hostname);
			server =  (GameServerInterface) Naming.lookup("rmi://" + servername 
																   + "/GameServer");
			GameClient client = new GameClient();
		} catch (NotBoundException nbe) {
			System.out.println("No game server available");
		} catch (RemoteException re) {
			System.out.println("RMI Error - " + re);
		} catch (Exception e) {
			System.out.println("Error - " + e);
		}
	}
}
