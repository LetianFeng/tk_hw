package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gui.Gui;
import gui.GuiClientInterface;
import server.BookingService;
import server.ServerSoapService;
import bookingEntry.BookingReq;
import bookingEntry.BookingResponse;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ClientSoap implements ClientGUIInterface{

    private BookingService soapServer;
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

        ServerSoapService serverSoapService = new ServerSoapService();
        soapServer = serverSoapService.getServerSoapPort();
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

            String availableServices = soapServer.getAvailableService(dateFormat.format(startDate), dateFormat.format(endDate));

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
    public void sendBooking(Map<String, Integer>serviceMap, String email) {

        System.out.println();
        System.out.println("Gui request send booking:");
        ArrayList<BookingReq> bookingReqList = new ArrayList<>();

        for (hotel.Service service : serviceList) {

            for (String serviceName : serviceMap.keySet()) {

                if (service.getType().equals(serviceName) && service.getAmount()>= serviceMap.get(serviceName)) {

                    Date bookDate = (Date) startDate.clone();
                    while (dateBefore(bookDate, endDate)) {
                        for (int i = 0; i < serviceMap.get(serviceName); i++) {
                            BookingReq bookingReq = new BookingReq(service.getId(), email, (Date) bookDate.clone());
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
            gui.drawFailure(response.getInfo());
        else
            gui.drawSuccessDetails(response.getInfo());

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
