package udpServer;

import account.Account;
import message.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpServer implements Runnable {

    private int udpPort;
    private Account account;

    public UdpServer(int udpPort, Account account) {

        this.udpPort = udpPort;
        this.account = account;
    }

    @Override
    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(udpPort);

            while(true)
            {
                byte[] receiveData = new byte[1500];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String datagramMessage = new String(receivePacket.getData());

                // todo: Letian, put datagram messages to the FIFO of receiver, process messages inside FIFO.

                Message message = new Message(datagramMessage);
                account.responseServerMessage(message);

                byte[] sendData = "Ok".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
