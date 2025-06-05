package manager;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();

    //Добавление задач в историю
    @Override
    public void add(Task task) {
        if(task != null) {
            history.add(task);
            if (history.size() > 10 ) {
                history.removeFirst();
            }
        }
    }

    //История просмотров задач
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
