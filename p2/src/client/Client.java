package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.entry.Booking;
import server.server.ServerRest;
import server.server.ServerSoapInterface;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;

import static com.google.gson.internal.bind.util.ISO8601Utils.format;

public class Client implements ClientGUIInterface{

    ServerSoapInterface soapServer;
    ServerRest restServer;

    GUIInterface gui;

    ArrayList<server.entry.Service> serviceList;

    public  Client(GUIInterface gui) throws MalformedURLException {
        this.gui = gui;
        serviceList = new ArrayList<server.entry.Service>();

        // soap server
        URL url = new URL("http://localhost:9999/booking?wsdl");
        QName qname = new QName("http://server.server/", "ServerSoapService");
        Service service = Service.create(url, qname);
        soapServer = service.getPort(ServerSoapInterface.class);

        // TODO rest server

    }

    @Override
    public void searchRooms(Date startDate, Date endDate) throws MalformedURLException {
        // display start & end date from gui
        System.out.println("Search rooms in the following date range: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Start: " + dateFormat.format(startDate));
        System.out.println("End: " + dateFormat.format(endDate));

        // TODO check if date range is valid

        String availableServices = soapServer.getAvailableService(dateFormat.format(startDate), dateFormat.format(endDate));

        Type listType = new TypeToken<ArrayList<server.entry.Service>>() {}.getType();
        serviceList = new Gson().fromJson(availableServices, listType);
        System.out.println("Following rooms are available: ");
        for (server.entry.Service service : serviceList)
            if (service.isRoom)
                gui.drawService(service.serviceName, service.price, service.description, service.availableAmount);

        // TODO if date invalid
        //gui.invalidDate("invalid date");

    }

    @Override
    public void getExtraServices() {
        System.out.println();
        System.out.println("Get extra services: ");
        System.out.println("Following extra services are available: ");
        for (server.entry.Service service : serviceList)
            if (!service.isRoom)
                gui.drawService(service.serviceName, service.price, service.description, service.availableAmount);
    }

    @Override
    public void calculateTotalPrice(Map<String, Integer>serviceMap) {

        double totalPrice = 0;

        for (server.entry.Service service: serviceList) {

            for (String serviceName : serviceMap.keySet()) {

                if (service.serviceName.equals(serviceName) && service.availableAmount>= serviceMap.get(serviceName)) {
                    totalPrice += service.price * serviceMap.get(serviceName);
                    serviceMap.remove(serviceName);
                }
            }
        }

        gui.drawTotalPrice(totalPrice);
    }

    @Override
    public void sendBooking(Map<String, Integer>serviceMap, String email) {
        ArrayList<Booking> bookingList = new ArrayList<Booking>();

        for (server.entry.Service service : serviceList) {

            for (String serviceName : serviceMap.keySet()) {

                if (service.serviceName.equals(serviceName) && service.availableAmount>= serviceMap.get(serviceName)) {
                    // TODO make booking structure
                }
            }
        }

        // TODO call postBookingEntry(String bookingEntry) on server side
        // TODO call drawSuccessDetails(String bookingDetails) or drawFailure(String failedService) on gui side

    }

    @Override
    public void CreateNewBooking() {

    }
}
