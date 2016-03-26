package ListItems;

/**
 * Created by Rohit on 3/27/16.
 */
public class NotificationEntry {
    private int id;
    private String notification;
    private String createdOn;

    public NotificationEntry() {
    }

    public NotificationEntry(int id, String notification, String createdOn) {
        this.id = id;
        this.notification = notification;
        this.createdOn = createdOn;
    }

    public int getId() {
        return id;
    }

    public String getNotification() {
        return notification;
    }

    public String getCreatedOn() {
        return createdOn;
    }
}
