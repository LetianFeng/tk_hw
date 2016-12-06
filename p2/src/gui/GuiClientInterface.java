package gui;

import java.util.ArrayList;
import hotel.Service;

public interface GuiClientInterface {

    void invalidDate(String errorInfo); // remind user date input errors, such as end date should be after start date

    void drawService(ArrayList<Service> serviceList);

    void lockGUIUntilServerFeedBack(); // after sending a booking to server, gui should not be able to change itself till sth. returns

    void unlockGUI(); // unlock it for changing invalid booking or creating a new booking

    void drawSuccessDetails(String bookingDetails);

    void drawFailure(String failedService);

    void invalidBooking(String errorInfo); // booking is invalid, e.g. more amount than available

    void initializeAll(); // prepare for a new booking, simply call constructor
    
    //void drawTotalPrice(double totalPrice);

}