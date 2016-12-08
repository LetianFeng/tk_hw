package gui;

import java.util.ArrayList;
import hotel.Service;

public interface GuiClientInterface {

    void invalidDate(String errorInfo);

    void drawService(ArrayList<Service> serviceList);

    void lockGUIUntilServerFeedBack(); 

    void unlockGUI();

    void drawSuccessDetails(String bookingDetails);

    void drawFailure(String failedService);

    void invalidBooking(String errorInfo);

    void initializeAll();

}