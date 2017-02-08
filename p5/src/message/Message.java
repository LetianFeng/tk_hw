package message;

public class Message {

    private String messageBody;
    protected int accountId;

    public Message() {
        messageBody = "";
    }

    public Message(String messageBody) {
        int end = messageBody.indexOf('\0');
        if(end != -1)
            this.messageBody = messageBody.substring(0,end);
    }

    public String getMessageBody() {
        return messageBody;
    }

    protected void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public int getAccountId() {
        return accountId;
    }

    public boolean isTransaction() {
        if(messageBody.length() > 6)
            if(messageBody.substring(0,6).equals("marker"))
                return false;
        return true;
    }

}
