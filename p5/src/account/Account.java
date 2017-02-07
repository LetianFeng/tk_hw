package account;

import message.MarkerMessage;
import message.Message;
import message.TransactionMessage;
import udpClient.UdpClient;
import udpServer.UdpServer;
import gui.Gui;

import java.util.concurrent.ThreadLocalRandom;

public class Account implements Runnable {

    private int id;
    private int money;
    private int ownUdpPort;
    private int[] otherUdpPort;
    private Gui gui;
    private UdpClient[] udpClients;

    public Account(int ownUdpPort, int[] otherUdpPort, Gui gui, int id) {
        this.id = id;
        this.gui = gui;
        this.money = ThreadLocalRandom.current().nextInt(1000, 3000);
        this.ownUdpPort = ownUdpPort;
        this.otherUdpPort = otherUdpPort;
        this.udpClients = new UdpClient[otherUdpPort.length];
        gui.updateMoney(id, money);
    }

    @Override
    public void run() {

        (new Thread(new UdpServer(ownUdpPort, this))).start();
        for (int i = 0; i < otherUdpPort.length; i++) {
            udpClients[i] = new UdpClient(otherUdpPort[i]);
            (new Thread(udpClients[i])).start();
        }

        try {
            Thread.sleep(1000);

            while (true) {

                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));

                int randomClient = ThreadLocalRandom.current().nextInt(0, udpClients.length);
                int randomAmount = generateTransactionAmount();
                if (randomAmount!=0)
                    udpClients[randomClient].sendMessage(new TransactionMessage(this.id, randomAmount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int generateTransactionAmount() {
        int randomAmount = ThreadLocalRandom.current().nextInt(0, 101);
        if (money < randomAmount)
            return 0;
        else {
            money = money - randomAmount;
            return randomAmount;
        }
    }

    public void responseServerMessage(Message message) {
        // todo: Zhen, record the channel state when the snapshot algorithm starts
        if(message.isTransaction()) {
            TransactionMessage transactionMessage = new TransactionMessage(message.getMessageBody());
            printTransaction(transactionMessage);
            achieveTransactionAmount(transactionMessage.getAmount());
        }
        else {
            MarkerMessage markerMessage = new MarkerMessage(message.getMessageBody());
            printMarker(markerMessage);
            responseMarker(markerMessage);
        }
    }

    // todo: Zhen, add marker message response service, show the current snapshot of process and change its state
    public void responseMarker(MarkerMessage message) {

    }

    public boolean achieveTransactionAmount(int amount) {
        money = money + amount;
        gui.updateMoney(id, money);
        return true;
    }

    public void printTransaction(TransactionMessage message) {
        gui.output("from " + String.valueOf(message.getAccountId()+1) + " to " + String.valueOf(id+1)
                + " : $" + String.valueOf(message.getAmount()));
    }

    public void printMarker(MarkerMessage message) {
        gui.output("from " + String.valueOf(message.getAccountId()+1) + " to " + String.valueOf(id+1)
                + " marker.");
    }

    public int getMoney() {
        return money;
    }

    public void sendMarkerMessage() {
        MarkerMessage message = new MarkerMessage(id);
        for(UdpClient udpClient : udpClients)
            udpClient.sendMessage(message);
    }
}
