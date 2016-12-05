package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.entry.Booking;
import server.server.ServerSoapInterface;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ClientSoap implements ClientGUIInterface{

    private ServerSoapInterface soapServer;
    private GUIInterface gui;
    private Date startDate;
    private Date endDate;
    private ArrayList<server.entry.Service> serviceList;

    public ClientSoap(GUIInterface gui) throws MalformedURLException {
        this.gui = gui;
        startDate = null;
        endDate = null;
        serviceList = new ArrayList<>();

        // soap server
        URL url = new URL("http://localhost:9999/booking?wsdl");
        QName qname = new QName("http://server.server/", "ServerSoapService");
        Service service = Service.create(url, qname);
        soapServer = service.getPort(ServerSoapInterface.class);

    }

    @Override
    public void searchRooms(Date startDate, Date endDate) throws MalformedURLException {

        this.startDate = startDate;
        this.endDate = endDate;

        // display start & end date from gui
        System.out.println("Search rooms in the following date range: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Start: " + dateFormat.format(startDate));
        System.out.println("End: " + dateFormat.format(endDate));

        if (startDate.before(endDate)) {

            String availableServices = soapServer.getAvailableService(dateFormat.format(startDate), dateFormat.format(endDate));

            Type listType = new TypeToken<ArrayList<server.entry.Service>>() {
            }.getType();
            serviceList = new Gson().fromJson(availableServices, listType);
            System.out.println("Following rooms are available: ");
            gui.drawRooms(serviceList);

        } else
            gui.invalidDate("invalid date");

    }

    @Override
    public void getExtraServices() {
        System.out.println();
        System.out.println("Get extra services: ");
        System.out.println("Following extra services are available: ");
        gui.drawExtraServices(serviceList);
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

        long diff = endDate.getTime() - startDate.getTime();
        long days = diff / (1000 * 60 * 60 * 24);

        gui.drawTotalPrice(totalPrice * days);
    }

    @Override
    public void sendBooking(Map<String, Integer>serviceMap, String email) {
        ArrayList<Booking> bookingList = new ArrayList<Booking>();

        for (server.entry.Service service : serviceList) {

            for (String serviceName : serviceMap.keySet()) {

                if (service.serviceName.equals(serviceName) && service.availableAmount>= serviceMap.get(serviceName)) {
                    Date bookDate = (Date) startDate.clone();
                    while (bookDate.before(endDate)) {
                        for (int i = 0; i < serviceMap.get(serviceName); i++) {
                            Booking booking = new Booking(service.serviceId, email, bookDate);
                            bookingList.add(booking);
                        }
                        bookDate.setDate(bookDate.getDate()+1);
                    }
                }
            }
        }

        String bookingResponce = soapServer.postBookingEntry(new Gson().toJson(bookingList));
        if (bookingResponce.equals("invalid booking entry"))
            gui.drawFailure(bookingResponce);
        else
            gui.drawSuccessDetails(bookingResponce);

    }

    @Override
    public void CreateNewBooking() {

    }
}
