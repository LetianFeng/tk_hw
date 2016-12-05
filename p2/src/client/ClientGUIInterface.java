package client;

import server.entry.Booking;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

public interface ClientGUIInterface {

    void searchRooms(Date startDate, Date endDate) throws MalformedURLException;

    // change to next tab and draw services
    void getExtraServices();

    // calculate total price with a button in gui or caused by number changing in gui
    void calculateTotalPrice(Map<String, Integer> serviceMap);

    //  <String serviceName, Integer serviceAmount>
    // call success or failure method in gui depending on feedback from server
    void sendBooking(Map<String, Integer>serviceMap, String email);

    // save temporary booking to client's booking buffer & clean temporary booking
    // booking buffer is reserved for booking review in gui after implementation of all basic features
    void CreateNewBooking();

}
