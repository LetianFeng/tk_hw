package server.server;

import server.entry.BookingReq;
import server.entry.BookingResponse;
//import server.entry.Service;
import hotel.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

class FakeServerLogic implements ServerLogic {

    private ArrayList<Service> fakeList = new ArrayList<>();

    FakeServerLogic() {
        Service singleRoom = new Service(UUID.randomUUID(), "Single Room", true, 2, 50.99, "rooms for one person");
        Service doubleRoom = new Service(UUID.randomUUID(), "Double Room", true, 1, 50.99, "rooms for two persons");
        Service rentCar = new Service(UUID.randomUUID(), "Rental Car", false, 20, 50.99, "rent for a car");
        Service breakfast = new Service(UUID.randomUUID(), "Breakfast", false, Integer.MAX_VALUE, 50.99, "reserve the breakfast");
        fakeList.add(singleRoom);
        fakeList.add(doubleRoom);
        fakeList.add(rentCar);
        fakeList.add(breakfast);
    }

    @Override
    public BookingResponse postBookingList(ArrayList<BookingReq> bookingList) {
        return new BookingResponse(true, null);
        //return new BookingResponse(false, "rent car");
    }

    @Override
    public ArrayList<Service> requestAvailableService(Date startDate, Date endDate) {
        return fakeList;
    }

}
