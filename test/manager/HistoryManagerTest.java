package manager;

import model.Task;
import org.junit.jupiter.api.Test;
import util.Status;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    // убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    public void testHistoryPreservesPreviousVersion() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("История1", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewTask(task);

        // Получаем задачу в историю (задача добавляется в historyManager)
        Task fetched = taskManager.getTaskById(task.getId());

        // Обновляем задачу в менеджере
        Task updated = new Task("История11.", "Описание", Status.IN_PROGRESS, task.getId());
        taskManager.updateTask(task.getId(), updated);

        // Проверяем, что в истории осталась прежняя версия
        List<Task> history = taskManager.getHistory();
        assertFalse(history.isEmpty(), "История не должна быть пустой");
        Task historyTask = history.get(0);
        assertEquals(fetched, historyTask, "В истории должна храниться старая версия задачи");
        assertNotEquals(updated, historyTask, "В истории не должна храниться обновлённая версия");
    }

}