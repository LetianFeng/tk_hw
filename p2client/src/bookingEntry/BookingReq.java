package bookingEntry;

import java.util.Date;
import java.util.UUID;

public class BookingReq {

    public final UUID serviceId;
    public final String email;
    public final Date date;

    public BookingReq(UUID serviceId, String email, Date date) {
        this.serviceId = serviceId;
        this.email = email;
        this.date = date;
    }
    
    @Override
    public String toString() {
    	return "Id: " + serviceId.toString() + " email: " + email + " date: " + date.toString();
    }

}
