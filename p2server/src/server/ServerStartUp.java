package server;

import java.io.IOException;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import javax.xml.ws.Endpoint;

public class ServerStartUp extends Server {

    public static void main(String[] args) {
        try {
            String ipAddress;
            if(args.length!=0)
                ipAddress = args[0];
            else
                ipAddress = "localhost";
            String restUrl = "http://" + ipAddress + ":8080/";
            String soapUrl = "http://" + ipAddress + ":8090/booking";
            System.out.print(restUrl);
            System.out.print(soapUrl);
            Endpoint.publish(soapUrl, new ServerSoap());
            HttpServer restServer = HttpServerFactory.create(restUrl);
            restServer.start();
            System.out.println("Both Server Start Up Successfully.");
//          restServer.stop(0);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
