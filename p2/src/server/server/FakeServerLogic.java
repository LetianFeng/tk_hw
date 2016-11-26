package server.server;

import server.entry.Booking;
import server.entry.BookingResponse;
import server.entry.Service;

import java.time.LocalDate;
import java.util.ArrayList;

class FakeServerLogic implements ServerLogic {

    private ArrayList<Service> fakeList = new ArrayList<>();

    FakeServerLogic() {
        Service singleRoom = new Service(1, "single rooms", 20.99, true, "rooms for one person", 8);
        Service doubleRoom = new Service(2, "double rooms", 30.99, true, "rooms for two persons", 11);
        Service rentCar = new Service(3, "rent car", 20.00, false, "rent for a car", 3);
        Service breakfast = new Service(4, "breakfast", 3.00, false, "reserve the breakfast", Integer.MAX_VALUE);
        fakeList.add(singleRoom);
        fakeList.add(doubleRoom);
        fakeList.add(rentCar);
        fakeList.add(breakfast);
    }

    @Override
    public BookingResponse postBookingList(ArrayList<Booking> bookingList) {
        return new BookingResponse(true, null);
        //return new BookingResponse(false, "rent car");
    }

    @Override
    public ArrayList<Service> requestAvailableService(LocalDate startDate, LocalDate endDate) {
        return fakeList;
    }

}
