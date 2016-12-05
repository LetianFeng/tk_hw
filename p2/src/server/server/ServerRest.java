package server.server;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.entry.Booking;
import server.entry.BookingResponse;
import server.entry.Service;

@Path("/booking")
public class ServerRest {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private Server server = new Server();

    @GET
    @Path("/availableService/{startDate}/{endDate}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAvailableService(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {

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
            throw new WebApplicationException(400);
        }
    }

    @POST
    @Path("/bookingEntry")
    @Produces(MediaType.APPLICATION_JSON)
    public String postBookingEntry(String bookingEntry) {

        try {
            Type listType = new TypeToken<ArrayList<Booking>>(){}.getType();
            ArrayList<Booking> bookingList = new Gson().fromJson(bookingEntry, listType);
            BookingResponse response = server.getServerLogic().postBookingList(bookingList);
            return new Gson().toJson(response);
        } catch (Exception e) {
            System.out.print("convert failed!");
            throw new WebApplicationException(400);
        }
    }

}
