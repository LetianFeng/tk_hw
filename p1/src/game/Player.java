package game;

public class Player {
	
	private String username;
	private int score;
	
	public Player(String name) {
		this.username = name;
		this.score = 0;
	}
	
	public Player(String name, int score) {
		this.username = name;
		this.score = score;
	}
	
	public String getName() {
		return this.username;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	} 

}
