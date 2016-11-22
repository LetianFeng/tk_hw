package game;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

public class Room implements Serializable {
	
	private UUID roomID;
	private Map<UUID, Minion> minions;
	private Map<String, Player> players;
	private String RMIUrl;
	
	public Room() {
		this.roomID = UUID.randomUUID();
		this.players = new HashMap<String, Player>();
		this.minions = new HashMap<UUID, Minion>();
		for (int i = 0; i < Util.numOfMinions; i++) {
			Minion m = Util.createNewMinion();
			minions.put(m.getMinionID(), m);
		}
		this.RMIUrl = Util.defaultRMI;
	}
	
	public Room(UUID roomID) {
		this.roomID = (roomID == null) ? UUID.randomUUID() : roomID;
		this.players = new HashMap<String, Player>();
		this.minions = new HashMap<UUID, Minion>();
		this.RMIUrl = Util.defaultRMI;
	}
	
	public UUID getID() {
		return this.roomID;
	}
	
	public Map<String, Player> getPlayers() {
		return this.players;
	}
	
	public Map<UUID, Minion> getMinions() {
		return this.minions;
	}
	
	public void addPlayer(String username) {
		
	}
	
	@Override
	public boolean equals(Object room) {
		if (!(room instanceof Room)) {
			return false;
		}
		if (this == room) {
			return true;
		}
		Room r = (Room) room;
		return (r.roomID == this.roomID);
	}
}
