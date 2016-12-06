package server;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import bookingEntry.BookingReq;
import bookingEntry.BookingResponse;
import hotel.*;

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
            //throw new WebApplicationException(400);
            return new Gson().toJson(new ArrayList<Service>());
        }
    }

    @POST
    @Path("/bookingEntry")
    @Produces(MediaType.APPLICATION_JSON)
    public String postBookingEntry(String bookingEntry) {

        try {
            Type listType = new TypeToken<ArrayList<BookingReq>>(){}.getType();
            Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            ArrayList<BookingReq> bookingList = gson.fromJson(bookingEntry, listType);
            BookingResponse response = server.getServerLogic().postBookingList(bookingList);
            return new Gson().toJson(response);
        } catch (Exception e) {
            System.out.print("convert failed!");
            //throw new WebApplicationException(400);
            return new Gson().toJson(new BookingResponse(false, "An error has occured."));
        }
    }

}
