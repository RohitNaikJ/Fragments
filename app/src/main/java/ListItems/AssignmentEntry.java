package ListItems;

/**
 * Created by Rohit on 3/24/16.
 */
public class AssignmentEntry {
    private int id;
    private String name;
    private String deadline;

    public AssignmentEntry(int id, String name, String deadline) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeadline() {
        return deadline;
    }
}
