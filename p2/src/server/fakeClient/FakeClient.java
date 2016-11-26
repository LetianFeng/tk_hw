package server.fakeClient;

import server.server.ServerSoapInterface;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class FakeClient {

    private static String testBookingEntry = "[{\"serviceId\": 1,\"email\": \"test@example.com\"," +
            "\"date\":{\"year\": 2016,\"month\": 12,\"day\": 10}},{\"serviceId\": 3," +
            "\"email\": \"test@example.com\",\"date\":{\"year\": 2016,\"month\": 12,\"day\": 11}}]";

    public static void main(String[] args) throws Exception {

        URL url = new URL("http://localhost:9999/booking?wsdl");
        QName qname = new QName("http://server.server/", "ServerSoapService");

        Service service = Service.create(url, qname);
        ServerSoapInterface hello = service.getPort(ServerSoapInterface.class);

        System.out.println(hello.getAvailableService("2015-11-11", "2015-11-12"));
        System.out.println(hello.postBookingEntry(FakeClient.testBookingEntry));
    }

}
