package server;

import bookingEntry.BookingReq;
import bookingEntry.BookingResponse;

import hotel.*;

import java.util.ArrayList;
import java.util.Date;

interface ServerLogic {

    ArrayList<Service> requestAvailableService(Date startDate, Date endDate);

    BookingResponse postBookingList(ArrayList<BookingReq> bookingList);

}
