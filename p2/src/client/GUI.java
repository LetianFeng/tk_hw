package client;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GUI implements  GUIInterface{

    public static void main(String[] args) throws MalformedURLException {
        GUI gui = new GUI();
        Client client = new Client(gui);
        Date startDate = new Date(2016-1900, 12-1, 1);
        Date endDate = new Date(2018-1900, 2-1, 23);
        client.searchRooms(startDate, endDate);
        client.getExtraServices();
        Map<String, Integer> serviceMap= new HashMap<String, Integer>();
        serviceMap.put("single rooms", 7);
        client.calculateTotalPrice(serviceMap);
    }

    @Override
    public void invalidDate(String errorInfo) {
        System.out.println("Date range is invalid: " + errorInfo);
    }

    @Override
    public void changeToTab(String tabName) {

    }

    @Override
    public void initializeTab(String tabName) {

    }

    @Override
    public void drawService(String serviceName, double price, String description, int availableAmount) {
        System.out.println("Name: " + serviceName + "; Price: " + price + "; Amount: " + availableAmount + "; Description: " + description);
    }

    @Override
    public void drawTotalPrice(double totalPrice) {
        System.out.println("Total price: " + totalPrice);
    }

    @Override
    public void lockGUIUntilServerFeedBack() {

    }

    @Override
    public void invalidBooking(String errorInfo) {

    }

    @Override
    public void unlockGUI() {

    }

    @Override
    public void drawSuccessDetails(String bookingDetails) {

    }

    @Override
    public void drawFailure(String failedService) {

    }

    @Override
    public void initializeAll() {

    }
}
