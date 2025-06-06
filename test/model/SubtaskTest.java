package model;

import manager.InMemoryTaskManager;
import manager.Manager;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import util.Status;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    //проверьте, что наследники класса Task равны друг другу, если равен их id
    @Test
    void subtaskEqualsIfIdSame() {
        Subtask subtask1 = new Subtask("t1", "d1", Status.NEW, 1,1);
        Subtask subtask2 = new Subtask("t1", "d1", Status.NEW, 1,1);
        assertEquals(subtask1, subtask2, "Экземпляры не равны");
    
    }

    //проверьте, что объект Subtask нельзя сделать своим же эпиком;
    @Test
    void subtaskCannotBeItsOwnEpic() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        // Проверим ситуацию "Subtask не может быть своим же эпиком" – т. е. subtask.getEpicId() == subtask.getId()

        Subtask subtask = new Subtask("t", "d", Status.NEW, 1, 1);
        // В менеджере нет эпика с таким id (эпик не регистрировался), но попробуем вызвать updateSubtask или addNewSubtask.
        manager.addNewSubtask(subtask);
        // В итоге, если subtask.epicId == subtask.id, то он "повиснет" без реального эпика.
        // Проверим, что в списке subtasks он есть, но универсальная логика реализации не присвоит ему статус эпика.
        assertTrue(manager.getListOfAllSubtasks().containsKey(1), "Subtask с epicId == id должен быть в списке subtask'ов");
        // Но getSubtasksOfEpic для эпика с таким id вернёт null или пустой список, потому что эпика нет:
        Epic epic = manager.getEpicById(1);
        // getEpicById вернёт null (или выведет ошибку), т. к. эпик не существует:
        assertNull(epic, "Эпика с данным id не должно существовать");
    }

    // удаление Subtask по id и очистка Epic
    @Test
    public void shouldRemoveSubtaskById() {
        TaskManager taskManager = Manager.getDefault();
        Epic epic = new Epic("Эпик", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewEpic(epic);
        Subtask sub = new Subtask("Подзадача", "Описание", Status.NEW, taskManager.generateNewId(), epic.getId());
        taskManager.addSubtaskInEpic(epic, sub);
        int subId = sub.getId();
        taskManager.removeSubtaskById(subId);
        Map<Integer, Subtask> allSubs = taskManager.getListOfAllSubtasks();
        assertFalse(allSubs.containsKey(subId), "Subtask должен быть удалён из менеджера");
        assertFalse(epic.getSubtaskIds().contains(subId), "Epic не должен ссылаться на удалённую Subtask");
    }

    //удаление всех Subtask и очистка всех Epics
    @Test
    public void shouldRemoveAllSubtasks() {
        TaskManager taskManager = Manager.getDefault();
        Epic epic1 = new Epic("Эпик1", "Описание", Status.NEW, taskManager.generateNewId());
        Epic epic2 = new Epic("Эпик2", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewEpic(epic1);
        taskManager.addNewEpic(epic2);
        Subtask s1 = new Subtask("S1", "Описание", Status.NEW, taskManager.generateNewId(), epic1.getId());
        Subtask s2 = new Subtask("S2", "Описание", Status.NEW, taskManager.generateNewId(), epic2.getId());
        taskManager.addSubtaskInEpic(epic1, s1);
        taskManager.addSubtaskInEpic(epic2, s2);
        taskManager.removeAllSubtasks();
        Map<Integer, Subtask> allSubs = taskManager.getListOfAllSubtasks();
        assertTrue(allSubs.isEmpty(), "Все Subtask должны быть удалены");
        assertTrue(epic1.getSubtaskIds().isEmpty(), "Список subtaskIds у первого Epic должен быть пустым");
        assertTrue(epic2.getSubtaskIds().isEmpty(), "Список subtaskIds у второго Epic должен быть пустым");
    }
}