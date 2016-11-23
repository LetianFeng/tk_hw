package game;

import java.util.UUID;
import java.io.Serializable;

public class Minion implements Serializable{

	private static final long serialVersionUID = -1164179147851424143L;
	
	private UUID minionID;
	private float coord_x;
	private float coord_y;
	private String texture;
	private Facing face;
	private boolean minionChanged;
	
	public enum Facing {UP, DOWN, RIGHT, LEFT, FRONT};
	
	public Minion(UUID minionID) {
		this.minionID = minionID;
		this.coord_x = 0;
		this.coord_y = 0;
		this.texture = Util.texture;
		this.face = Facing.FRONT;
		this.minionChanged = false;
	}
	
	public Minion(float x, float y) {
		this.minionID = UUID.randomUUID();
		this.coord_x = x;
		this.coord_y = y;
		this.texture = Util.texture;
		this.face = Facing.FRONT;
		this.minionChanged = false;
	}
	
	public Minion(float x, float y, String texture, Facing face) {
		this.minionID = UUID.randomUUID();
		this.coord_x = x;
		this.coord_y = y;
		this.texture = texture;
		this.face = face;
		this.minionChanged = false;
	}
	
	public UUID getMinionID(){
		return this.minionID;
	}
	
	public boolean getStatus() {
		return this.minionChanged;
	}
	
	public void setStatus(boolean flag) {
		this.minionChanged = flag;
	}
	
	public float getX() {
		return this.coord_x;
	}
	
	public float getY() {
		return this.coord_y;
	}
	
	public void setX(float x) {
		this.coord_x = x;
	}
	
	public void setY(float y) {
		this.coord_y = y;
	}
	
	public void setTexture(String texture) {
		this.texture = texture;
	}
	
	public void setFacing(Facing face) {
		this.face = face;
	}
	
	@Override
	public boolean equals(Object minion) {
		if (!(minion instanceof Minion)) {
			return false;
		}
		if (this == minion) {
			return true;
		}
		Minion r = (Minion) minion;
		return (r.minionID == this.minionID);
	}
}