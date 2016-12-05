package server.server;

import server.entry.Booking;
import server.entry.BookingResponse;
import server.entry.Service;

import java.util.ArrayList;
import java.util.Date;

interface ServerLogic {

    ArrayList<Service> requestAvailableService(Date startDate, Date endDate);

    BookingResponse postBookingList(ArrayList<Booking> bookingList);

}
