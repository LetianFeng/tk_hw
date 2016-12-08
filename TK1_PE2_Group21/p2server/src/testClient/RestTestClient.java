package testClient;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestTestClient {
	
    private static final String REST_URI = "http://localhost:8080/booking/";
    private static final String AVAILABLE_PATH = "availableService/";
    private static final String BOOKING_PATH = "bookingEntry/";

    private static String testSuccBookingEntry = "[{\"serviceId\": \"77bf51e6-b658-40b4-8fb0-f38eb5a6a5ec\",\"email\": \"test@example.com\"," +
            "\"date\":\"2016-12-25\"},{\"serviceId\": \"56aff76b-0fd3-4ef3-865b-d5479a25c9f5\"," +
            "\"email\": \"test@example.com\",\"date\":\"2016-12-26\"}]";
    private static String testFailBookingEntry = "[{\"serviceId\": \"dad2b45b-1feb-471b-820f-d430ed2a74d1\",\"email\": \"test@example.com\"," +
            "\"date\":\"2016-12-25\"},{\"serviceId\": \"77bf51e6-b658-40b4-8fb0-f38eb5a6a5ec\"," +
            "\"email\": \"test@example.com\",\"date\":\"2016-12-25\"}]";
    
    private static String start = "2016-12-25";
    private static String end = "2016-12-26";

    public static void main(String[] args) throws Exception {

    	ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(REST_URI).path(AVAILABLE_PATH).path(start + "/" + end);
        String availableServices = getOutputAsJson(service);
        System.out.println(availableServices);
        
        service = client.resource(REST_URI).path(BOOKING_PATH);
        String bookingResponse = postOutputAsJson(service, testSuccBookingEntry);
        System.out.print(bookingResponse);
        
    }
    
    private static String postOutputAsJson(WebResource service, String postJson) {
        return service.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, postJson).getEntity(String.class);
    }

    private static String getOutputAsJson(WebResource service) {
        return service.accept(MediaType.APPLICATION_JSON).get(String.class);
    }

}
