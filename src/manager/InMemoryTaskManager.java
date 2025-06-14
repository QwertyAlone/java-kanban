package manager;

import model.Epic;
import model.Subtask;
import model.Task;
import util.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    // сделал поля приватными
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Manager.getDefaultHistory();
    private static int counter = 0; // счетчик для идентификатора

    @Override
    public int generateNewId() {
        return ++counter;
    }

    void printErrorId() {
        System.out.println("Такого идентификатора нет в списке задач");
    }

    //МЕТОДЫ ДЛЯ ЗАДАЧ:

    // получение списка всех задач
    @Override
    public HashMap<Integer, Task> getListOfAllTasks() {
        return tasks;
    }

    // Удаление всех задач
    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    // Получение по идентификатору.
    @Override
    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        } else {
            printErrorId();
            return null;
        }
    }

    //  Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public void addNewTask(Task task) {
        int id = task.getId();
        tasks.put(id, task);
    }

    // Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Override
    public void updateTask(int id, Task task) {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        } else {
            printErrorId();
        }
    }

    // Удаление по идентификатору.
    @Override
    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            printErrorId();
        }

    }

    // МЕТОДЫ ДЛЯ ПОДЗАДАЧ:

    // получение списка всех подзадач
    @Override
    public HashMap<Integer, Subtask> getListOfAllSubtasks() {
        return subtasks;
    }

    //  Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public void addNewSubtask(Subtask subtask) {
        int id = subtask.getId();
        subtasks.put(id, subtask);
    }

    // Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Override
    public void updateSubtask(int id, Subtask subtask) {
        if (!subtasks.containsKey(id)) {
            printErrorId();
            return;
        }

        subtasks.put(id, subtask);

        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            updateEpicStatus(epic);
        }
    }

    // Удаление всех подзадач с очисткой эпиков
    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic);
        }
    }

    // Получение подзадачи по идентификатору
    @Override
    public Subtask getSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            historyManager.add(subtasks.get(id));
            return subtasks.get(id);
        } else {
            printErrorId();
            return null;
        }
    }

    // Удаление подзадачи по идентификатору
    @Override
    public void removeSubtaskById(int id) {
        if (!subtasks.containsKey((id))) {
            printErrorId();
            return;
        }
        Subtask subtask = subtasks.remove(id);
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.getSubtaskIds().remove((Integer) id);
            updateEpicStatus(epic);
        }
    }

    // МЕТОДЫ ДЛЯ ЭПИКОВ:

    //  Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public void addNewEpic(Epic epic) {
        int id = epic.getId();
        epics.put(id, epic);
    }

    // получение списка всех эпиков
    @Override
    public HashMap<Integer, Epic> getListOfAllEpics() {
        return epics;
    }

    // Добавление подзадачи в эпик
    @Override
    public void addSubtaskInEpic(Epic epic, Subtask subtask) {
        //добавлена проверка, до этого некорректно работало
        if (subtask.getId() == epic.getId()) {
            return;
        }
        subtask.setEpicId(epic.getId());
        addNewSubtask(subtask);
        epic.getSubtaskIds().add(subtask.getId());
        updateEpicStatus(epic);
    }


    // Получение списка всех подзадач определённого эпика.
    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(Epic epic) {
        if (epics.containsValue(epic)) {
            ArrayList<Subtask> epicSubtasks = new ArrayList<>();
            for (int id : epic.getSubtaskIds()) {
                epicSubtasks.add(subtasks.get(id));
            }
            return epicSubtasks;
        } else {
            return null;
        }
    }

    // Авто-обновление статуса эпика
    @Override
    public void updateEpicStatus(Epic epic) {
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (int id : subtaskIds) {
            Status status = subtasks.get(id).getStatus();
            if (status != Status.NEW) {
                allNew = false;
            }
            if (status != Status.DONE) {
                allDone = false;
            }
        }

        if (allDone) {
            epic.setStatus(Status.DONE);
        } else if (allNew) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    // Получение по идентификатору эпик
    @Override
    public Epic getEpicById(int id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
            return epics.get(id);
        } else {
            printErrorId();
            return null;
        }
    }

    // Удаление всех эпиков и подзадач, которые с ними связаны
    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
        epics.clear();
    }

    // Удаление эпика по идентификатору с удалением подзадач
    @Override
    public void removeEpicById(int id) {
        if (!epics.containsKey(id)) {
            printErrorId();
            return;
        }

        Epic epic = epics.remove(id);
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
    }

    //Метод для просмотра истории
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}