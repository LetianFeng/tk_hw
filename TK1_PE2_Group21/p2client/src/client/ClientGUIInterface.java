package client;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

public interface ClientGUIInterface {

    void searchRooms(Date startDate, Date endDate) throws MalformedURLException;

    // <String serviceName, Integer serviceAmount>
    // call success or failure method in gui depending on feedback from server
    void sendBooking(Map<String, Integer>serviceMap, String email);
}
