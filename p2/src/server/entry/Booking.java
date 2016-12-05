package server.entry;

import java.time.LocalDate;

public class Booking {

    public final int serviceId;
    public final String email;
    public final LocalDate date;

    public Booking(int serviceId, String email, LocalDate date) {
        this.serviceId = serviceId;
        this.email = email;
        this.date = date;
    }

}
