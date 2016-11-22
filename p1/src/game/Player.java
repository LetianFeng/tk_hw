package game;

import java.util.UUID;
import java.io.Serializable;

public class Player implements Serializable{
	
	private UUID userID;
	private String username;
	private int score;
	
	public Player(String name) {
		this.userID = UUID.randomUUID();
		this.username = name;
		this.score = 0;
	}
	
	public Player(String name, int score) {
		this.userID = UUID.randomUUID();
		this.username = name;
		this.score = score;
	}
	
	public UUID getUserID() {
		return this.userID;
	}
	
	public String getName() {
		return this.username;
	}
	
	public void assignName(String username) {
		this.username = username;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	} 
	
	@Override
	public boolean equals(Object player) {
		if (!(player instanceof Player)) {
			return false;
		}
		if (this == player) {
			return true;
		}
		Player p = (Player) player;
		return (p.username == this.username);
	}
}
