package Service;

public interface NotificationReceiver {
    void start(NotificationSubscriber subscriber);
    void stop();
}

