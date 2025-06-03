package manager;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {
    int generateNewId();

    // получение списка всех задач
    HashMap<Integer, Task> getListOfAllTasks();

    // Удаление всех задач
    void removeAllTasks();

    // Получение по идентификатору.
    Task getTaskById(int id);

    //  Создание. Сам объект должен передаваться в качестве параметра.
    void addNewTask(Task task);

    // Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateTask(int id, Task task);

    // Удаление по идентификатору.
    void removeTaskById(int id);

    // получение списка всех подзадач
    HashMap<Integer, Subtask> getListOfAllSubtasks();

    //  Создание. Сам объект должен передаваться в качестве параметра.
    void addNewSubtask(Subtask subtask);

    // Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateSubtask(int id, Subtask subtask);

    // Удаление всех подзадач с очисткой эпиков
    void removeAllSubtasks();

    // Получение подзадачи по идентификатору
    Subtask getSubtaskById(int id);

    // Удаление подзадачи по идентификатору
    void removeSubtaskById(int id);

    //  Создание. Сам объект должен передаваться в качестве параметра.
    void addNewEpic(Epic epic);

    // получение списка всех эпиков
    HashMap<Integer, Epic> getListOfAllEpics();

    // Добавление подзадачи в эпик
    void addSubtaskInEpic(Epic epic, Subtask subtask);

    // Получение списка всех подзадач определённого эпика.
    ArrayList<Subtask> getSubtasksOfEpic(Epic epic);

    // Авто-обновление статуса эпика
    void updateEpicStatus(Epic epic);

    // Получение по идентификатору эпик
    Epic getEpicById(int id);

    // Удаление всех эпиков и подзадач, которые с ними связаны
    void removeAllEpics();

    // Удаление эпика по идентификатору с удалением подзадач
    void removeEpicById(int id);


}
