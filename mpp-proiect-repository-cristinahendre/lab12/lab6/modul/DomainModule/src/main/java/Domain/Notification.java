package Domain;


public class Notification  {
    private NotificationType type;
    private String user, sender, messageText;
    private Donatie d;

    public Donatie getDonatie() {
        return d;
    }

    public void setDonatie(Donatie d) {
        this.d = d;
    }

    public Notification() {
    }

    public Notification(NotificationType type) {

        this.type = type;
    }

    public Notification(NotificationType type, Donatie d) {
        this.type = type;
        this.d = d;
    }


    public Notification(NotificationType type, String sender, String messageText) {
        this.type = type;
        this.sender=sender;
        this.messageText=messageText;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTextMessage() {
        return messageText;
    }

    public void setTextMessage(String message) {
        this.messageText = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "Notification{ " +
                "type=" + type +
                ", donatie=" + d +
                ", sender=" + sender +
                ", message=" + messageText +
                '}';
    }
}
