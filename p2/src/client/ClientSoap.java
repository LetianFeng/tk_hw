package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gui.Gui;
import gui.GuiClientInterface;
import server.entry.BookingReq;
import server.entry.BookingResponse;
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
    private GuiClientInterface gui;
    private Date startDate;
    private Date endDate;
    private ArrayList<hotel.Service> serviceList;

    public static void main(String[] args) throws MalformedURLException {
        ClientSoap clientSoap = new ClientSoap();
        clientSoap.gui.initializeAll();
    }

    public ClientSoap() throws MalformedURLException {
        this.gui = new Gui(this);
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

            Type listType = new TypeToken<ArrayList<hotel.Service>>() {
            }.getType();
            serviceList = new Gson().fromJson(availableServices, listType);
            System.out.println("Following rooms are available: ");
            gui.drawService(serviceList);

        } else
            gui.invalidDate("invalid date");

    }

    @Override
    public void getExtraServices() {
        System.out.println();
        System.out.println("Get extra services: ");
        System.out.println("Following extra services are available: ");
        //gui.drawExtraServices(serviceList);
    }

    @Override
    public void sendBooking(Map<String, Integer>serviceMap, String email) {
        ArrayList<BookingReq> bookingReqList = new ArrayList<>();

        for (hotel.Service service : serviceList) {

            for (String serviceName : serviceMap.keySet()) {

                if (service.getType().equals(serviceName) && service.getAmount()>= serviceMap.get(serviceName)) {
                    Date bookDate = (Date) startDate.clone();
                    while (bookDate.before(endDate)) {
                        for (int i = 0; i < serviceMap.get(serviceName); i++) {
                            BookingReq bookingReq = new BookingReq(service.getId(), email, bookDate);
                            bookingReqList.add(bookingReq);
                        }
                        bookDate.setDate(bookDate.getDate()+1);
                    }
                }
            }
        }
        Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String bookingResponse = soapServer.postBookingEntry(gson.toJson(bookingReqList));
        BookingResponse response = gson.fromJson(bookingResponse, BookingResponse.class);
        if (!response.isBookingState())
            gui.drawFailure(bookingResponse);
        else
            gui.drawSuccessDetails(bookingResponse);

    }

    @Override
    public void CreateNewBooking() {

    }
}
