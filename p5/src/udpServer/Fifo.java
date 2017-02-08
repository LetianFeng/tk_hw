package udpServer;

import account.Account;
import message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Fifo implements Runnable{

    private Account account; // receiver
    private List<Message> messageQueue; // first-in-first-out queue

    // initialize channel without first message
    public Fifo(Account account) {
        this.account = account;
        this.messageQueue = new ArrayList<>();
    }

    // initialize channel with first message
    public Fifo(Account account, Message firstMessage) {
        this(account);
        this.messageQueue.add(firstMessage);
    }

    // add message at end of the message queue
    public void add(Message message) {
        this.messageQueue.add(message);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // wait random time
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));

                // if no message in queue, do nothing
                if (messageQueue.isEmpty())
                    continue;

                // otherwise send message to receiver
                account.responseServerMessage(messageQueue.remove(0));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
