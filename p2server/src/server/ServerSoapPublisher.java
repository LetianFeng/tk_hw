package server;

import javax.xml.ws.Endpoint;

//Endpoint publisher
public class ServerSoapPublisher {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:9999/booking", new ServerSoap());
    }
}
