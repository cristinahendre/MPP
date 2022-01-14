import Domain.Donatie;
import Domain.Notification;
import Domain.NotificationType;
import Service.INotification;
import org.springframework.jms.core.JmsOperations;


public class NotificationServiceI implements INotification {
    private JmsOperations jmsOperations;


    public NotificationServiceI(JmsOperations operations) {
        jmsOperations=operations;
    }

    @Override
    public void refresh(Donatie d) {
        System.out.println("refresh in notification");
        Notification notif=new Notification(NotificationType.REFRESH,d);
        jmsOperations.convertAndSend(notif);
        System.out.println("Sent message to ActiveMQ... " +notif);
    }



}
