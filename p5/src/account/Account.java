package account;

import message.MarkerMessage;
import message.Message;
import message.TransactionMessage;
import udpClient.UdpClient;
import udpServer.UdpServer;
import gui.Gui;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Account implements Runnable {

    private int id;
    private int money;
    private int ownUdpPort;
    private int[] otherUdpPort;
    private Gui gui;
    private UdpClient[] udpClients;
    
    private boolean snapshot;
    private boolean isRecordingChannel0;
    private boolean isRecordingChannel1;
    private boolean isRecordingChannel2;
    private ArrayList<TransactionMessage> channelRecord0;
    private ArrayList<TransactionMessage> channelRecord1;
    private ArrayList<TransactionMessage> channelRecord2;
    private int localState;

    public Account(int ownUdpPort, int[] otherUdpPort, Gui gui, int id) {
        this.id = id;
        this.gui = gui;
        this.money = ThreadLocalRandom.current().nextInt(1000, 3000);
        this.ownUdpPort = ownUdpPort;
        this.otherUdpPort = otherUdpPort;
        this.udpClients = new UdpClient[otherUdpPort.length];
        gui.updateMoney(id, money);
        
        snapshot = false;
        isRecordingChannel0 = false;
        isRecordingChannel1 = false;
        isRecordingChannel2 = false;
        channelRecord0 = new ArrayList<TransactionMessage>();
        channelRecord1 = new ArrayList<TransactionMessage>();
        channelRecord2 = new ArrayList<TransactionMessage>();
        localState = this.money;
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
        if(message.isTransaction()) {
	        TransactionMessage transactionMessage = new TransactionMessage(message.getMessageBody());
	        printTransaction(transactionMessage);
	        achieveTransactionAmount(transactionMessage.getAmount());
        	if(snapshot) {
        		if(transactionMessage.getAccountId() == 0 && isRecordingChannel0)
        			channelRecord0.add(transactionMessage);
        		if(transactionMessage.getAccountId() == 1 && isRecordingChannel1)
        			channelRecord1.add(transactionMessage);
        		if(transactionMessage.getAccountId() == 2 && isRecordingChannel2)
        			channelRecord2.add(transactionMessage);
        	}
        }
        else {
            MarkerMessage markerMessage = new MarkerMessage(message.getMessageBody());
            if(markerMessage.getAccountId() == 3)
            	gui.output("Snapshot: Initiator Account" + (this.id + 1));
            else
            	printMarker(markerMessage);
            responseMarker(markerMessage);
        }
    }

    public synchronized void responseMarker(MarkerMessage message) {
    	if(message.getAccountId() == 3) {
    		snapshot = true;
    		isRecordingChannel0 = true;
    		isRecordingChannel1 = true;
    		isRecordingChannel2 = true;
    		if(this.id == 0) {
    			isRecordingChannel0 = false;
    		}
    		if(this.id == 1) {
    			isRecordingChannel1 = false;
    		}
    		if(this.id == 2) {
    			isRecordingChannel2 = false;
    		}
        	localState = this.money;
    		sendMarkerMessage();
    	}
    	else {
	    	if(snapshot) {
	    		if(message.getAccountId() == 0) {
	    			isRecordingChannel0 = false;
	    		}
	    		if(message.getAccountId() == 1) {
	    			isRecordingChannel1 = false;
	    		}
	    		if(message.getAccountId() == 2) {
	    			isRecordingChannel2 = false;
	    		}
	    		if(!isRecordingChannel0 && !isRecordingChannel1 && !isRecordingChannel2) {
	    			snapshot = false;
	    			printLocalState();
	    			channelRecord0.clear();
	    			channelRecord1.clear();
	    			channelRecord2.clear();
	    		}
	    	}
	    	else {
	    		snapshot = true;
	    		isRecordingChannel0 = true;
	    		isRecordingChannel1 = true;
	    		isRecordingChannel2 = true;
	    		if(message.getAccountId() == 0 || this.id == 0) {
	    			isRecordingChannel0 = false;
	    		}
	    		if(message.getAccountId() == 1 || this.id == 1) {
	    			isRecordingChannel1 = false;
	    		}
	    		if(message.getAccountId() == 2 || this.id == 2) {
	    			isRecordingChannel2 = false;
	    		}
	        	localState = this.money;
	    		sendMarkerMessage();
	    	}
    	}
    }
    
    private void printLocalState() {
    	String localStateOutput = null;
    	if(this.id == 0) {
    		localStateOutput = "Account0 local state: " + this.localState + ", C10: " +
    				sum(channelRecord1) + ", C20: " + sum(channelRecord2);
    	}
    	if(this.id == 1) {
    		localStateOutput = "Account1 local state: " + this.localState + ", C01: " +
    				sum(channelRecord0) + ", C21: " + sum(channelRecord2);
    	}
    	if(this.id == 2) {
    		localStateOutput = "Account2 local state: " + this.localState + ", C02: " +
    				sum(channelRecord0) + ", C12: " + sum(channelRecord1);
    	}
    	gui.output(localStateOutput);
    }
    
    private int sum(ArrayList<TransactionMessage> list) {
    	int result = 0;
    	for(TransactionMessage msg : list) {
    		result += msg.getAmount();
    	}
    	return result;
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
        for(UdpClient udpClient : udpClients) {
            udpClient.sendMessage(message);
        }
    }
}
