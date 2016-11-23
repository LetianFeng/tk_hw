package server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;

import client.GameClientInterface;
import game.*;

public class GameServer extends UnicastRemoteObject implements GameServerInterface {

	private static final long serialVersionUID = -8495779138119939506L;
	private Map<GameClientInterface, Player> player_list;
	private Map<UUID, Room> room_list;
	
	private GameServer() throws RemoteException {
		super();
		initialization();
	}

	private void initialization() {
		
		this.room_list = new HashMap<UUID, Room>();
		this.player_list = new HashMap<GameClientInterface, Player>();
		Room room = new Room();
		this.room_list.put(room.getID(), room);
	}
	
	public Room joinRoom(GameClientInterface client, Player player, Room room)
			throws RemoteException {	
		
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
		client.sendNotification("User with id: " 
									+ player.getName()
									+ " joined room "
									);
		
		return r;
	}
	
	@Override
	public void checkMinion(UUID roomID, UUID minionID, GameClientInterface client)
			throws RemoteException {
		
		Player player = player_list.get(client);
		if (player == null) {
			client.sendNotification("You haven't login.");
			return;
		}
		
		Room room = room_list.get(roomID);
		if (room == null) {
			client.sendNotification("The room with id, " 
										+ " doesn't exist.");
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
			try {
				minion.setStatus(Util.minionIsBeingAccessed);
				client.sendNotification("You got the minion" );
				Util.updateMinionPosition(minion);
				Util.incrementPlayerScore(player.getName(), roomID, room_list);
				notifyClients(minion, player);
				minion.setStatus(Util.minionIsAvailableForAccess);
			} catch (MalformedURLException | NotBoundException e) {
				minion.setStatus(Util.minionIsAvailableForAccess);
				e.printStackTrace();
			}
		} else {
			client.sendNotification("Player " 
										+ player.getName() 
										+ " has a faster hand.");
		}
	}

	@Override
	public Room login(GameClientInterface client, String username, UUID roomID)
			throws RemoteException {
		
		Player player = new Player(username);
		
		// join the first room by default
		// int index = room_list.indexOf(new Room(roomID));
		// if (index != -1) room = room_list.get(index);
		
		Room room = joinRoom(client, player, room_list.get(roomID));		
		System.out.println("user " + player.getName() + " has logged in and entered room  " + room.getID().toString());
		
		try {
			notifyClients(null, player);
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
			return null;
		}
		
		return room;
	}

	@Override
	public boolean logout(GameClientInterface client, UUID roomID) throws RemoteException {
		
		Player player = player_list.get(client);
		Room room = room_list.get(roomID);
		
		System.out.println("user exit room " + room.getID() + ": " + player.getName());
		room.getPlayers().remove(player.getName());
		System.out.println("user disconnected: " + player_list.get(client).getName());
		player_list.remove(client);
		
		try {
			notifyClients(null, player);
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	private void notifyClients(Minion minion, Player player) throws MalformedURLException, NotBoundException {

		for (GameClientInterface client : player_list.keySet()) {
			try {
				client.minionChanged(minion, player);
			} catch (RemoteException re) {
				continue;
			}
		}
	}

	public static void main(String args[]) {
		
		System.out.println("Loading game server service");
		try {
			String hostname = Util.defaultHost;
			if (args.length > 0){
	            hostname = args[0];
	        }
			
			System.setProperty("java.rmi.server.hostname", hostname);
			LocateRegistry.createRegistry(1099);
			GameServer gameServer = new GameServer();
			Naming.rebind("rmi://" + hostname + "/GameServer", gameServer);
		} catch (RemoteException re) {
			System.err.println("Remote Error - " + re);
		} catch (Exception e) {
			System.err.println("Error - " + e);
		}
	}
}
