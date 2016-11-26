package server.entry;

public class BookingResponse {

    private final boolean bookingState;
    private final String failedService;

    public BookingResponse(boolean bookingState, String failedService) {
        this.bookingState = bookingState;
        this.failedService = failedService;
    }

}
