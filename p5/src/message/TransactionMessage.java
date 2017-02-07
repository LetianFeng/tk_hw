package message;

public class TransactionMessage extends Message {

    private int accountId;
    private int amount;

    public TransactionMessage(int accountId, int amount) {
        super();
        this.accountId = accountId;
        this.amount = amount;
        String message = String.valueOf(accountId) + ',' + String.valueOf(amount);
        this.setMessageBody(message);
    }

    public TransactionMessage(String message) {
        super(message);
        int comma = message.indexOf(',');
        this.accountId = Integer.valueOf(message.substring(0, comma));
        this.amount = Integer.valueOf(message.substring(comma+1));
    }

    public int getAccountId() {
        return accountId;
    }

    public int getAmount() {
        return amount;
    }
}
