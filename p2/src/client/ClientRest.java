package client;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import gui.Gui;
import gui.GuiClientInterface;
import hotel.Service;
import server.entry.BookingReq;
import server.entry.BookingResponse;

public class ClientRest implements ClientGUIInterface {

    private static final String REST_URI = "http://localhost:9999/booking/";
    private static final String AVAILABLE_PATH = "availableService/";
    private static final String BOOKING_PATH = "bookingEntry/";
    private GuiClientInterface gui;
    private Date startDate;
    private Date endDate;
    private ArrayList<Service> serviceList;

    public static void main(String[] args) throws MalformedURLException {
        ClientRest clientRest = new ClientRest();
        clientRest.gui.initializeAll();
    }

    public ClientRest() throws MalformedURLException {
        this.gui = new Gui(this);
        startDate = null;
        endDate = null;
        serviceList = new ArrayList<>();
    }

    @Override
    public void searchRooms(Date startDate, Date endDate) throws MalformedURLException {

        this.startDate = startDate;
        this.endDate = endDate;

        dateSetNull(startDate);
        dateSetNull(endDate);
        // display start & end date from gui
        System.out.println("Search rooms in the following date range: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Start: " + dateFormat.format(startDate));
        System.out.println("End: " + dateFormat.format(endDate));

        if (dateBefore(startDate, endDate)) {

            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            WebResource service = client.resource(REST_URI).path(AVAILABLE_PATH).path(dateFormat.format(startDate) + "/" + dateFormat.format(endDate));
            String availableServices = getOutputAsJson(service);

            Type listType = new TypeToken<ArrayList<hotel.Service>>() {
            }.getType();
            serviceList = new Gson().fromJson(availableServices, listType);
            System.out.println("Following rooms are available: ");
            gui.drawService(serviceList);

        } else
            gui.invalidDate("invalid date");
    }

    private void dateSetNull(Date date) {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
    }

    @Override
    public void sendBooking(Map<String, Integer> serviceMap, String email) {
        ArrayList<BookingReq> bookingList = new ArrayList<>();

        for (hotel.Service service : serviceList) {

            for (String serviceName : serviceMap.keySet()) {

                if (service.getType().equals(serviceName) && service.getAmount()>= serviceMap.get(serviceName)) {
                    Date bookDate = (Date) startDate.clone();
                    while (dateBefore(bookDate, endDate)) {
                        for (int i = 0; i < serviceMap.get(serviceName); i++) {
                            BookingReq booking = new BookingReq(service.getId(), email, bookDate);
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
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String bookingResponse = postOutputAsJson(service, gson.toJson(bookingList));
        BookingResponse response = gson.fromJson(bookingResponse, BookingResponse.class);
        if (!response.isBookingState())
            gui.drawFailure(bookingResponse);
        else
            gui.drawSuccessDetails(bookingResponse);
    }

    private static String postOutputAsJson(WebResource service, String postJson) {
        return service.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, postJson).getEntity(String.class);
    }

    private static String getOutputAsJson(WebResource service) {
        return service.accept(MediaType.APPLICATION_JSON).get(String.class);
    }

    private boolean dateBefore(Date startDate, Date endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String start = dateFormat.format(startDate);
        String end = dateFormat.format(endDate);
        start = start.replaceAll("-", "");
        end = end.replaceAll("-", "");
        int startInt = Integer.parseInt(start);
        int endInt = Integer.parseInt(end);
        return startInt < endInt;
    }

}
