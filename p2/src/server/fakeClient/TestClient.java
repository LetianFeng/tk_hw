package server.fakeClient;

import server.server.ServerSoapInterface;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class TestClient {

    private static String testSuccBookingEntry = "[{\"serviceId\": \"77bf51e6-b658-40b4-8fb0-f38eb5a6a5ec\",\"email\": \"test@example.com\"," +
            "\"date\":\"2016-12-25\"},{\"serviceId\": \"56aff76b-0fd3-4ef3-865b-d5479a25c9f5\"," +
            "\"email\": \"test@example.com\",\"date\":\"2016-12-26\"}]";
    private static String testFailBookingEntry = "[{\"serviceId\": \"dad2b45b-1feb-471b-820f-d430ed2a74d1\",\"email\": \"test@example.com\"," +
            "\"date\":\"2016-12-25\"},{\"serviceId\": \"77bf51e6-b658-40b4-8fb0-f38eb5a6a5ec\"," +
            "\"email\": \"test@example.com\",\"date\":\"2016-12-25\"}]";

    public static void main(String[] args) throws Exception {

        URL url = new URL("http://localhost:9999/booking?wsdl");
        QName qname = new QName("http://server.server/", "ServerSoapService");

        Service service = Service.create(url, qname);
        ServerSoapInterface soap = service.getPort(ServerSoapInterface.class);

        //System.out.println(soap.getAvailableService("2016-12-25", "2016-12-25"));
        System.out.println(soap.postBookingEntry(TestClient.testFailBookingEntry));
    }

}
