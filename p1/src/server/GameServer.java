package server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

import client.GameClientInterface;
import game.*;

public class GameServer extends UnicastRemoteObject implements GameServerInterface {

	private Random random;
	private volatile int x;
	private volatile int y;
	private volatile int minionID;
	private boolean minionChanged;
	//private GameLogic gl;

	//private Map<GameClientInterface, Integer> userScores;
	// mapping from client to username maybe better, otherwise one client will request the client name from all other clients
	private Map<GameClientInterface, Player> player_list;
	private Map<UUID, Room> room_list;
	
	private GameServer() throws RemoteException {
		super();
		//gl = new GameLogic();
		// Initial the Score Mapping
        //userScores = new HashMap<>();
        // Produce the initial Minion Coordinate
		/*random = new Random();
        x = random.nextInt();
		y = random.nextInt();
		minionID = random.nextInt();
		minionChanged = false;*/
		
		initialization();
	}

	private void initialization() {
		this.room_list = new HashMap<UUID, Room>();
		this.player_list = new HashMap<GameClientInterface, Player>();
		Room room = new Room();
		this.room_list.put(room.getID(), room);
	}
	
	public Room joinRoom(GameClientInterface client, Player player, Room room) throws RemoteException {
		
		Room r = room;
		if (room_list.isEmpty()) {
			
			// room_list is empty, fill in a default room
			r = new Room();
			room_list.put(r.getID(), r);
		}	
		if(r == null || room_list.get(r.getID()) == null) {
			
			// room_list doesn't contains room or room is null
			// join the first as default
			r = room_list.entrySet().iterator().next().getValue();
		}
		
		if (r.getPlayers().containsKey(player.getName())) {
			player.assignName(Util.resolveDuplicateName(player.getName(), r.getPlayers()));
		}
		
		player_list.put(client, player);
		r.getPlayers().put(player.getName(), player);
		client.sendNotification("User with id: " + player.getName() + " joined room " + r.getID());
		
		return r;
	}
	
	@Override
	public void checkMinion(UUID roomID, UUID minionID, GameClientInterface client) throws RemoteException {
		
		Player player = player_list.get(client);
		if (player == null) {
			client.sendNotification("You haven't login.");
			return;
		}
		
		Room room = room_list.get(roomID);
		if (room == null) {
			client.sendNotification("The room with id, " + roomID + " doesn't exist.");
			String rooms = "";
		    Iterator it = room_list.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        rooms += pair.getKey() + " ";
		        //it.remove(); // avoids a ConcurrentModificationException
		    }
		    client.sendNotification("Available rooms are " + rooms);
			return;
		}
		
		Minion minion = null;
		if (room.getMinions().containsKey(minionID)) {
			minion = room.getMinions().get(minionID);
		}
		if (minion == null) {
			client.sendNotification("The minion doesn't exist.");
			return;
		}	
		
		if (!minion.getStatus()) {
			minion.setStatus(Util.minionIsBeingAccessed);
			client.sendNotification("You got the minion with id: " + minion.getMinionID() 
																   + ", at position (" 
																   + minion.getX()
																   + ", "
																   + minion.getY()
																   + ")");
			Util.updateMinionPosition(minion);
			Util.incrementPlayerScore(player.getName(), roomID, room_list);
			
			try {
				notifyClients(minion, player);
			} catch (MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
			minion.setStatus(Util.minionIsAvailableForAccess);
		} else {
			client.sendNotification("Player " + player.getName() + " has a faster hand.");
		}
		
		/*if (this.minionID == minionID && !minionChanged) {
			// basic protection from conflict, can be implemented with thread in future
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
			client.sendNotification("Sorry, somebody is faster. :( ");*/
	}

	/**
	 * reproduce the minion with coordinate when a click from client is valid.
	 */
	private void reproduceMinion() {
		x = random.nextInt();
		y = random.nextInt();
		minionID = random.nextInt();
	}

	@Override
	public Room login(GameClientInterface client, String username, UUID roomID) throws RemoteException {
		
		Player player = new Player(username);
		
		// join the first room by default
		// int index = room_list.indexOf(new Room(roomID));
		// if (index != -1) room = room_list.get(index);
		
		Room room = joinRoom(client, player, room_list.get(roomID));
		
		try {
			notifyClients(null, player);
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return null;
		}
		
		return room;
		
		/* if (!userScores.keySet().contains(client)) {
			System.out.println("user logged in: " + client.getUsername());
			// notify all clients except for the new one
			try {
				notifyClients();
			} catch (MalformedURLException | NotBoundException e) {
				e.printStackTrace();
			}
			userScores.put(client, 0);
			// notify the new client with return value
			return new Minion(x, y, minionID, userScores);
		} else {
			return null;
		}*/
	}

	@Override
	public boolean logout(GameClientInterface client, UUID roomID) throws RemoteException {
		
		Player player = player_list.get(client);
		Room room = room_list.get(roomID);
		
		System.out.println("user exit room " + room.getID() + ": " + player.getName());
		room.getPlayers().remove(player.getName());
		
		//System.out.println("user logged out: " + client.getUsername());
		System.out.println("user disconnected: " + player_list.get(client).getName());
		//userScores.remove(client);
		player_list.remove(client);
		return true;
	}

	private void notifyClients(Minion minion, Player player) throws MalformedURLException, NotBoundException {

		for (GameClientInterface client : player_list.keySet()) {
			// Notify, if possible a listener
			try {
				client.minionChanged(minion, player);
			} catch (RemoteException re) {
				// Remove the listener
			}
		}
	}

	public static void main(String args[]) {
		System.out.println("Loading game server service");

		try {
			System.setProperty("java.rmi.server.hostname","192.168.0.201");
			LocateRegistry.createRegistry(1099);
			GameServer gameServer = new GameServer();
			Naming.rebind("rmi://192.168.0.201/GameServer", gameServer);
		} catch (RemoteException re) {
			System.err.println("Remote Error - " + re);
		} catch (Exception e) {
			System.err.println("Error - " + e);
		}
	}
}
