package manager;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 10; // добавил константу

    //Добавление задач в историю
    @Override
    public void add(Task task) {
        if(task != null) {
            history.add(task);
            if (history.size() > MAX_HISTORY_SIZE ) {
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
