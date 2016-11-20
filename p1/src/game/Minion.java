package game;

public class Minion {

	private int coord_x;
	private int coord_y;
	private String texture;
	private Facing face;
	
	public enum Facing {UP, DOWN, RIGHT, LEFT, FRONT};
	
	public Minion(int x, int y) {
		this.coord_x = x;
		this.coord_y = y;
		this.texture = Util.texture;
		this.face = Facing.FRONT;
	}
	
	public Minion(int x, int y, String texture, Facing face) {
		this.coord_x = x;
		this.coord_y = y;
		this.texture = texture;
		this.face = face;
	}
	
	public Coord getCoord() {
		return new Coord(this.coord_x, this.coord_y);
	}
	
	public void setX(int x) {
		this.coord_x = x;
	}
	
	public void setY(int y) {
		this.coord_y = y;
	}
	
	public void setTexture(String texture) {
		this.texture = texture;
	}
	
	public void setFacing(Facing face) {
		this.face = face;
	}
}

final class Coord {
	private int x;
	private int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
