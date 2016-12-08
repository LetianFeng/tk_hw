package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import bookingEntry.BookingReq;
import bookingEntry.BookingResponse;
import hotel.*;

import javax.jws.WebService;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Service Implementation
@WebService(endpointInterface = "server.ServerSoapInterface")
public class ServerSoap implements ServerSoapInterface{

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Server server = new Server();

    @Override
    public String getAvailableService(String startDate, String endDate) {
        try {
            Date start = formatter.parse(startDate);
            Date end = formatter.parse(endDate);
            ArrayList<Service> serviceList = server.getServerLogic().requestAvailableService(start, end);
            System.out.println("SOAP: request served, result: " + (new Gson().toJson(serviceList)));
            return new Gson().toJson(serviceList);
        } catch (Exception e) {
            System.out.print("invalid input: ");
            System.out.print(startDate);
            System.out.print(", ");
            System.out.println(endDate);
        }
        return new Gson().toJson(new ArrayList<Service>());
    }

    @Override
    public String postBookingEntry(String bookingEntry) {
        try {
        	System.out.println("SOAP: request received: " + bookingEntry);
            Type listType = new TypeToken<ArrayList<BookingReq>>(){}.getType();
            Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            ArrayList<BookingReq> bookingList = gson.fromJson(bookingEntry, listType);
            BookingResponse response = server.getServerLogic().postBookingList(bookingList);
            return new Gson().toJson(response);
        } catch (Exception e) {
            System.out.print("convert failed!");
            e.printStackTrace();
        }
        return new Gson().toJson(new BookingResponse(false, "An error has occured."));
    }
}
