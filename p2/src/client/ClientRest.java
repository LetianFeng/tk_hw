package client;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import server.entry.Booking;
import server.entry.Service;

public class ClientRest implements ClientGUIInterface {

    private static final String REST_URI = "http://localhost:9999/booking/";
    private static final String AVAILABLE_PATH = "availableService/";
    private static final String BOOKING_PATH = "bookingEntry/";
    private GUIInterface gui;
    private Date startDate;
    private Date endDate;
    private ArrayList<Service> serviceList;

    public ClientRest(GUIInterface gui) throws MalformedURLException {
        this.gui = gui;
        startDate = null;
        endDate = null;
        serviceList = new ArrayList<>();
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

            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            WebResource service = client.resource(REST_URI).path(AVAILABLE_PATH).path(dateFormat.format(startDate) + "/" + dateFormat.format(endDate));
            String availableServices = getOutputAsJson(service);

            Type listType = new TypeToken<ArrayList<Service>>() {
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
    public void calculateTotalPrice(Map<String, Integer> serviceMap) {

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
    public void sendBooking(Map<String, Integer> serviceMap, String email) {
        ArrayList<Booking> bookingList = new ArrayList<>();

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

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(REST_URI).path(BOOKING_PATH);

        String bookingResponce = postOutputAsJson(service, new Gson().toJson(bookingList));
        if (bookingResponce.equals("invalid booking entry"))
            gui.drawFailure(bookingResponce);
        else
            gui.drawSuccessDetails(bookingResponce);
    }

    @Override
    public void CreateNewBooking() {

    }

    private static String postOutputAsJson(WebResource service, String postJson) {
        return service.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, postJson).getEntity(String.class);
    }

    private static String getOutputAsJson(WebResource service) {
        return service.accept(MediaType.APPLICATION_JSON).get(String.class);
    }

}
