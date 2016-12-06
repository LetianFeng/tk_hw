package client;

import hotel.Service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GUI implements  GUIInterface{

    public static void main(String[] args) throws MalformedURLException {
        GUI gui = new GUI();
        ClientSoap client = new ClientSoap(gui);
//      ClientRest client = new ClientRest(gui);
        Date startDate = new Date(2016-1900, 12-1, 3);
        Date endDate = new Date(2016-1900, 12-1, 4);
        client.searchRooms(startDate, endDate);
        client.getExtraServices();
        Map<String, Integer> serviceMap= new HashMap<String, Integer>();
        serviceMap.put("Double Room", 1);
        client.sendBooking(serviceMap, "abc@my.email.com");
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
    public void drawRooms(ArrayList<hotel.Service> serviceList) {
        for (hotel.Service room : serviceList)
            if (room.isRoom())
                System.out.println("Name: " + room.getType() + "; Price: " + room.getPrice() + "; Amount: " +
                        room.getAmount() + "; Description: " + room.getDescription());
    }

    @Override
    public void drawExtraServices(ArrayList<Service> serviceList) {
        for (Service service : serviceList)
            if (!service.isRoom())
                System.out.println("Name: " + service.getType() + "; Price: " + service.getPrice()+ "; Amount: " +
                        service.getAmount()+ "; Description: " + service.getDescription());
    }

    @Override
    public void drawTotalPrice(double totalPrice) {
        System.out.println();
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
        System.out.println();
        System.out.println("Success: " + bookingDetails);
    }

    @Override
    public void drawFailure(String failedService) {
        System.out.println();
        System.out.println("Failed: " + failedService);
    }

    @Override
    public void initializeAll() {

    }
}
