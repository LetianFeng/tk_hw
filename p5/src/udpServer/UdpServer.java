package udpServer;

import account.Account;
import message.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class UdpServer implements Runnable {

    private int udpPort;
    private Account account;

    private Map<Integer, Fifo> senderIdChannelMap;

    public UdpServer(int udpPort, Account account) {
        this.udpPort = udpPort;
        this.account = account;
        this.senderIdChannelMap = new HashMap<>();
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

                // rebuild message from string
                Message message = new Message(datagramMessage);

                /*
                    append to channel according to sender id in the message
                    if there is no channel corresponding to the sender id,
                    initialize a new channel & put it into the map & start its thread
                 */
                int senderId = message.getAccountId();
                if (!senderIdChannelMap.keySet().contains(senderId)) {
                    Fifo channel = new Fifo(this.account, message);
                    senderIdChannelMap.put(senderId, channel);
                    // run thread of the new channel
                    (new Thread(channel)).start();
                } else {
                    Fifo channel = senderIdChannelMap.get(senderId);
                    channel.add(message);
                }


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
