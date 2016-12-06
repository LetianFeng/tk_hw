package hotel;

import java.util.UUID;
import java.util.Date;

public class Booking {
	
	private UUID id;
	private UUID bookingId;
	private UUID serviceId;
	private Date date;
	private String email;
	
	public Booking(UUID bookingId, UUID serviceId, Date date, String email) {
		this.id = UUID.randomUUID();
		this.bookingId = bookingId;
		this.serviceId = serviceId;
		this.date = date;
		this.email = email;
	}
	
	public Booking(UUID id, UUID bookingId, UUID serviceId, Date date, String email) {
		this.id = id;
		this.id = UUID.randomUUID();
		this.bookingId = bookingId;
		this.serviceId = serviceId;
		this.date = date;
		this.email = email;
	}
	
	public UUID getId() {
		return this.id;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public UUID getBookingId() {
		return this.bookingId;
	}
	
	public UUID getServiceId() {
		return this.serviceId;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	@Override
	public String toString() {
		return "serviceId: " + this.serviceId.toString()
			 + " date: " + this.date.toString()
			 + " email: " + this.email;
	}
}
