package server.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import server.entry.Booking;
import server.entry.BookingResponse;
import server.entry.Service;

import javax.jws.WebService;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Service Implementation
@WebService(endpointInterface = "server.server.ServerSoapInterface")
public class ServerSoap implements ServerSoapInterface{

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Server server = new Server();

    @Override
    public String getAvailableService(String startDate, String endDate) {
        try {
            Date start = formatter.parse(startDate);
            Date end = formatter.parse(endDate);
            ArrayList<Service> serviceList = server.getServerLogic().requestAvailableService(start, end);
            return new Gson().toJson(serviceList);
        } catch (Exception e) {
            System.out.print("invalid input: ");
            System.out.print(startDate);
            System.out.print(", ");
            System.out.println(endDate);
        }
        return "invalid input: " + startDate + ", " + endDate;
    }

    @Override
    public String postBookingEntry(String bookingEntry) {
        try {
            Type listType = new TypeToken<ArrayList<Booking>>(){}.getType();
            ArrayList<Booking> bookingList = new Gson().fromJson(bookingEntry, listType);
            BookingResponse response = server.getServerLogic().postBookingList(bookingList);
            return new Gson().toJson(response);
        } catch (Exception e) {
            System.out.print("convert failed!");
        }
        return "invalid booking entry";
    }
}