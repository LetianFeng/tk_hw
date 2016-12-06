package server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
@WebService(name = "BookingService")
@SOAPBinding(style = Style.RPC)
public interface ServerSoapInterface {

    @WebMethod String getAvailableService(String startDate, String endDate);

    @WebMethod String postBookingEntry(String bookingEntry);

}
