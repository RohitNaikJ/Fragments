package ListItems;

/**
 * Created by Rohit on 3/28/16.
 */
public class ThreadEntry {
    private int id;
    private String title;
    private String updatedOn;

    public ThreadEntry(int id, String title, String updatedOn) {
        this.id = id;
        this.title = title;
        this.updatedOn = updatedOn;
    }

    public ThreadEntry() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }
}
