package manager;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;
import util.Status;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    public void testAddAndGetDifferentTaskTypes() {
        // Добавляем Task
        TaskManager taskManager = Manager.getDefault();
        Task task = new Task("Задача1", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewTask(task);
        assertEquals(task, taskManager.getTaskById(task.getId()), "Нельзя получить Task по id");

        // Добавляем Epic
        Epic epic = new Epic("Эпик1", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewEpic(epic);
        assertEquals(epic, taskManager.getEpicById(epic.getId()), "Нельзя получить Epic по id");

        // Добавляем Subtask в созданный Epic
        Subtask sub = new Subtask("Подзадача1", "Описание", Status.NEW, taskManager.generateNewId(),
                epic.getId());
        taskManager.addSubtaskInEpic(epic, sub);
        assertEquals(sub, taskManager.getSubtaskById(sub.getId()), "Нельзя получить Subtask по id");
    }

}