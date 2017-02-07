package udpClient;

import message.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient implements Runnable {

    private int udpPort;

    public UdpClient(int udpPort) {

        this.udpPort = udpPort;
    }

    @Override
    public void run() {

    }

    public void sendMessage(Message message) {

        try
        {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress host = InetAddress.getByName("localhost");

            String datagram = message.getMessageBody();
            byte[] bytes = datagram.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(bytes , bytes.length , host , udpPort);
            clientSocket.send(sendPacket);

            byte[] buffer = new byte[1500];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(reply);

            byte[] data = reply.getData();
            datagram = new String(data, 0, reply.getLength());
            if(!datagram.equals("Ok"))
                System.out.println("Transaction Failed!");

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
