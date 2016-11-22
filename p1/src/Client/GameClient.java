package client;

import java.rmi.*;
import java.rmi.server.*;
import java.util.UUID;

import gui.GameGui;
import gui.GameGuiInterface;
import server.GameServerInterface;
import game.*;

public class GameClient extends UnicastRemoteObject implements GameClientInterface, GameClientGuiInterface{

	//private String serverURL;
	//public String username;
	public Room local_room = new Room(UUID.randomUUID());

	private GameGuiInterface gameGui;

	private static GameServerInterface server;

	private GameClient(String url) throws RemoteException {
		super();
		//serverURL = url;
		gameGui = new GameGui(this);
		gameGui.openLoginWindow();
	}

	/*public String getUsername() {
		return this.username;
	}*/

    @Override
    public void sendNotification(String notification) {
        gameGui.drawNotification(notification);
    }

	@Override
	public void minionChanged(Minion minion, Player player) {
        gameGui.cleanScreen();
        if (minion == null && player != null) {
        	// new player has entered the room
        	this.local_room.getPlayers().put(player.getName(), player);
        } else if (minion != null && player != null){
        	// a player has fed minion
        	gameGui.drawMinion(minion.getX(), minion.getY(), minion.getMinionID());
        	Player player_t = local_room.getPlayers().get(player.getName());
        	player_t.setScore(player_t.getScore() + 1);
        }
        gameGui.drawScores(this.local_room.getPlayers());
	}

	@Override
	public boolean login(String username) {

		try {
			//this.username = username;
            Room room = server.login(this, username, null);
			if (room != null) {
				System.out.println("Client: Login succeed! Entered room " + room.getID());
				// room entered will be copied to the local object only once at login
				// thereafter, only value update on the local copy
				this.local_room = room;
				gameGui.closeLoginWindow();
				gameGui.openGameWindow(this.local_room);
			} 
			
			/* else {
				this.username = null;
				System.out.println("Username already exist, try another!");
				return false;
			}*/
		} catch (Exception e) {
            e.printStackTrace();
			System.out.println("Client meets problem with connecting server!");
			return false;
		}
		
		return true;
	}

	@Override
	public boolean logout() {
        try {
            boolean logoutSuccess = server.logout(this, local_room.getID());
            System.out.println(logoutSuccess ? "logout successful" : "logout failure");
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

			server =  (GameServerInterface) Naming.lookup("rmi://localhost/GameServer");

			GameClient client = new GameClient("rmi://localhost/GameServer");

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
