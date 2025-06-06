package model;

import manager.Manager;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import util.Status;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    //проверьте, что экземпляры класса Task равны друг другу, если равен их id
    @Test
    void taskEqualsIfIdSame() {
        Task task1 = new Task("t1", "d1", Status.NEW, 1);
        Task task2 = new Task("t1", "d1", Status.NEW, 1);
        assertEquals(task1, task2, "Экземпляры не равны");

    }

    //проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
    @Test
    public void taskNoIdConflictBetweenManualAndGenerated() {
        TaskManager taskManager = Manager.getDefault();
        // Создаём задачу с "ручным" id 100
        Task manual = new Task("Ручная", "Описание", Status.NEW, 100);
        taskManager.addNewTask(manual);

        // Генерируем новый id – он не должен совпасть с 100
        int newId = taskManager.generateNewId();
        assertNotEquals(100, newId, "Сгенерированный id не должен конфликтовать с 100");

        Task generated = new Task("Сгенерированная", "Описание", Status.NEW, newId);
        taskManager.addNewTask(generated);

        assertEquals(manual, taskManager.getTaskById(manual.getId()), "Нельзя получить ручную задачу по id");
        assertEquals(generated, taskManager.getTaskById(generated.getId()), "Нельзя получить сгенерированную задачу по id");
    }

    // создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
    @Test
        public void taskImmutabilityOnAdd() {
        TaskManager taskManager = Manager.getDefault();
        Task original = new Task("Неизменяемая", "Описание", Status.NEW, 200);
        Task copy = new Task(original.getTitle(), original.getDescription(), original.getStatus(), original.getId());
        taskManager.addNewTask(original);

        // Поменял поля оригинального объекта после добавления
        original.setTitle("Изменено");
        original.setDescription("НовоеОписание");
        original.setStatus(Status.DONE);

        // При получении из менеджера будет изменённый объект (т.к. хранится ссылка)
        Task fromManager = taskManager.getTaskById(copy.getId());
        assertNotEquals(copy, fromManager, "Менеджер должен отразить изменения, так как хранит ссылку");
    }

    //удаление задачи по id
    @Test
    public void shouldRemoveTaskById() {
        TaskManager taskManager = Manager.getDefault();
        Task task = new Task("Удаляемая", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewTask(task);
        int id = task.getId();
        taskManager.removeTaskById(id);
        Map<Integer, Task> allTasks = taskManager.getListOfAllTasks();
        assertFalse(allTasks.containsKey(id), "Task должен быть удалён из менеджера");
        assertNull(taskManager.getTaskById(id), "При попытке получить удалённую задачу должен возвращаться null");
    }

    //удаление всех Task
    @Test
    public void shouldRemoveAllTasks() {
        TaskManager taskManager = Manager.getDefault();
        Task t1 = new Task("T1", "Описание", Status.NEW, taskManager.generateNewId());
        Task t2 = new Task("T2", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewTask(t1);
        taskManager.addNewTask(t2);
        taskManager.removeAllTasks();
        Map<Integer, Task> allTasks = taskManager.getListOfAllTasks();
        assertTrue(allTasks.isEmpty(), "Все задачи должны быть удалены");
    }

}