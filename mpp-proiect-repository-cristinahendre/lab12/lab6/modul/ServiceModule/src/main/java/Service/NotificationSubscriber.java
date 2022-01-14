package Service;

import Domain.Notification;

public interface NotificationSubscriber {
    void notificationReceived(Notification notif);
}
