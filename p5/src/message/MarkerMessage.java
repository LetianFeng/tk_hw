package message;

public class MarkerMessage extends Message {

    public MarkerMessage(int accountId) {
        super();
        this.accountId = accountId;
        String message = "marker " + String.valueOf(accountId);
        this.setMessageBody(message);
    }

    public MarkerMessage(String message) {
        super(message);
        int space = message.indexOf(' ');
        this.accountId = Integer.valueOf(message.substring(space+1));
    }
}
