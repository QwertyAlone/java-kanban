package model;

import manager.InMemoryTaskManager;
import manager.Manager;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import util.Status;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    //проверьте, что наследники класса Task равны друг другу, если равен их id
    @Test
    void epicEqualsIfIdSame() {
        Epic epic1 = new Epic("t", "d", Status.NEW, 1);
        Epic epic2 = new Epic("t", "d", Status.NEW, 1);
        assertEquals(epic1, epic2, "Экземпляры не равны");

        //равенство будет только в том случае, если совпадает всё, потому что переопределен метод equals()
    }


    //исправлено
    //проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи
    @Test
    void epicСantBeAddedToItselfAsSubtask() {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        // Создаем эпик с id = 1
        Epic epic = new Epic("t", "d", Status.NEW, 1);
        manager.addNewEpic(epic);

        // id подзадачи = id эпика = 1
        Subtask subtask = new Subtask("t", "d", Status.NEW, 1, 1);
        manager.addSubtaskInEpic(epic, subtask);


        // Проверяем, что подзадача не добавилась
        assertTrue(epic.getSubtaskIds().isEmpty(), "Epic не должен содержать подзадачу с собственным ID");
        assertNull(manager.getSubtaskById(1), "Subtask с ID эпика не должен быть добавлен в менеджер");
    }

    //удаление Epic по id с удалением связанных Subtask
    @Test
    public void shouldRemoveEpicByIdWithSubtasks() {
        TaskManager taskManager = Manager.getDefault();
        Epic epic = new Epic("ЭпикУдаляемый", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewEpic(epic);
        Subtask s1 = new Subtask("S1", "Описание", Status.NEW, taskManager.generateNewId(), epic.getId());
        Subtask s2 = new Subtask("S2", "Описание", Status.NEW, taskManager.generateNewId(), epic.getId());
        taskManager.addSubtaskInEpic(epic, s1);
        taskManager.addSubtaskInEpic(epic, s2);
        int epicId = epic.getId();
        int sub1Id = s1.getId();
        int sub2Id = s2.getId();
        taskManager.removeEpicById(epicId);
        Map<Integer, Epic> allEpics = taskManager.getListOfAllEpics();
        Map<Integer, Subtask> allSubs = taskManager.getListOfAllSubtasks();
        assertFalse(allEpics.containsKey(epicId), "Epic должен быть удалён из менеджера");
        assertFalse(allSubs.containsKey(sub1Id), "Первая Subtask должна быть удалена вместе с Epic");
        assertFalse(allSubs.containsKey(sub2Id), "Вторая Subtask должна быть удалена вместе с Epic");
    }

    //удаление всех Epics с удалением всех связанных Subtask
    @Test
    public void shouldRemoveAllEpics() {
        TaskManager taskManager = Manager.getDefault();
        Epic epic1 = new Epic("Эпик1", "Описание", Status.NEW, taskManager.generateNewId());
        Epic epic2 = new Epic("Эпик2", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewEpic(epic1);
        taskManager.addNewEpic(epic2);
        Subtask s1 = new Subtask("S1", "Описание", Status.NEW, taskManager.generateNewId(), epic1.getId());
        Subtask s2 = new Subtask("S2", "Описание", Status.NEW, taskManager.generateNewId(), epic2.getId());
        taskManager.addSubtaskInEpic(epic1, s1);
        taskManager.addSubtaskInEpic(epic2, s2);
        taskManager.removeAllEpics();
        Map<Integer, Epic> allEpics = taskManager.getListOfAllEpics();
        Map<Integer, Subtask> allSubs = taskManager.getListOfAllSubtasks();
        assertTrue(allEpics.isEmpty(), "Все Epics должны быть удалены");
        assertTrue(allSubs.isEmpty(), "Все Subtasks должны быть удалены вместе с Epics");
    }


}

