package server.server;

import server.entry.BookingReq;
import server.entry.BookingResponse;
//import server.entry.Service;

import hotel.*;

import java.util.ArrayList;
import java.util.Date;

interface ServerLogic {

    ArrayList<Service> requestAvailableService(Date startDate, Date endDate);

    BookingResponse postBookingList(ArrayList<BookingReq> bookingList);

}
