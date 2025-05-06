import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description, Status status, int id) {
        super(title, description, status, id);
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", id=" + getId() +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}
