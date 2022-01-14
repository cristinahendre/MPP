
import javafx.scene.control.Alert;

public class Notification {
    public  static void showNotification(String message, Alert.AlertType type){
        Alert alert=new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.setWidth(1000);
        alert.setHeight(400);
        alert.showAndWait();
    }
}

