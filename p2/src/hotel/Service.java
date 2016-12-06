package hotel;

import java.util.UUID;

public class Service {

	private UUID id;
	private String type;
	private boolean isRoom;
	private int amount;
	private float price;
	private String description;
	
	public Service(String type, boolean isRoom, int amount, float price, String description) {
		this.id = UUID.randomUUID();
		this.type = type;
		this.isRoom = isRoom;
		this.amount = amount;
		this.price = price;
		this.description = description;
	}
	
	public Service(UUID id, String type, boolean isRoom, int amount, float price, String description) {
		this.id = id;
		this.type = type;
		this.isRoom = isRoom;
		this.amount = amount;
		this.price = price;
		this.description = description;
	}
	
	public String getType() {
		return this.type;
	}
	
	public boolean isRoom() {
		return this.isRoom;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public String getDescription() {
		return this.description;
	}
}
