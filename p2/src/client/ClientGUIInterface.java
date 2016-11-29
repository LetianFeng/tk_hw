package client;

import server.entry.Booking;

import java.util.Date;
import java.util.Map;

public interface ClientGUIInterface {

    // search rooms according to the date range, change gui to room tab and call relevant methods for drawing
    void searchRooms(int startYear, int startMonth, int startDate, int endYear, int endMonth, int endDate);

    // change to next tab and draw services
    void getExtraServices();

    // calculate total price with a button in gui or caused by number changing in gui
    void calculateTotalPrice();

    //  <String roomName, Integer roomAmount>, <Date rentCarDate, Integer rentCarAmount>, analogous to breakfast
    // call success or failure method in gui depending on feedback from server
    void sendBooking(Map<String, Integer> roomMap, Map<Date, Integer> carMap, Map<Date, Integer> breakfastMap, String email);

    // save temporary booking to client's booking buffer & clean temporary booking
    // booking buffer is reserved for booking review in gui after implementation of all basic features
    void CreateNewBooking();

}
