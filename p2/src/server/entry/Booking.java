package server.entry;

import java.time.LocalDate;
import java.util.Date;

public class Booking {

    public final int serviceId;
    public final String email;
    public final Date date;

    public Booking(int serviceId, String email, Date date) {
        this.serviceId = serviceId;
        this.email = email;
        this.date = date;
    }

}
