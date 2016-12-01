package server.server;

import server.entry.Booking;
import server.entry.BookingResponse;
import server.entry.Service;

import java.time.LocalDate;
import java.util.ArrayList;

interface ServerLogic {

    ArrayList<Service> requestAvailableService(LocalDate startDate, LocalDate endDate);

    BookingResponse postBookingList(ArrayList<Booking> bookingList);

}
