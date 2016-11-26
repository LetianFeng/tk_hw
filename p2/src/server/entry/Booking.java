package server.entry;

import java.time.LocalDate;

public class Booking {

    private final int serviceId;
    private final String email;
    private final LocalDate date;

    public Booking(int serviceId, String email, LocalDate date) {
        this.serviceId = serviceId;
        this.email = email;
        this.date = date;
    }

}
