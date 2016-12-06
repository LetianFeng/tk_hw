package server.entry;

public class BookingResponse {

    private final boolean bookingState;
    private final String info;

    public BookingResponse(boolean bookingState, String info) {
        this.bookingState = bookingState;
        this.info = info;
    }

    public boolean isBookingState() {
        return bookingState;
    }

    public String getInfo() {
        return info;
    }
}
