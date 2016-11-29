package client;

public interface GUIInterface {

    void invalidDate(String errorInfo); // remind user date input errors, such as end date should be after start date

    void changeToTab(String tabName); // change from any tab to the tab according to the tabName

    void initializeTab(String tabName); // clean tab and draw necessary things, such as hints for input, buttons...

    void drawService(String serviceName, double price, String description, int availableAmount); // draw room or extra service

    void drawTotalPrice(double totalPrice);

    void lockGUIUntilServerFeedBack(); // after sending a booking to server, gui should not be able to change itself till sth. returns

    void invalidBooking(String errorInfo); // booking is invalid, e.g. more amount than available

    void unlockGUI(); // unlock it for changing invalid booking or creating a new booking

    void drawSuccessDetails(String bookingDetails);

    void drawFailure(String failedService);

    void initializeAll(); // prepare for a new booking, simply call constructor

}
