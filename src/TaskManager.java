import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    public HashMap<Integer, Task> tasks = new HashMap<>();
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    private static int counter = 0; // счетчик для идентификатора

    public int generateNewId() {
        return ++counter;
    }

    void printErrorId() {
        System.out.println("Такого идентификатора нет в списке задач");
    }

    //МЕТОДЫ ДЛЯ ЗАДАЧ:

    // получение списка всех задач
    public HashMap<Integer, Task> getListOfAllTasks() {
        return tasks;
    }

    // Удаление всех задач
    public void removeAllTasks() {
        tasks.clear();
    }

    // Получение по идентификатору.
    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else {
            printErrorId();
            return null;
        }
    }

    //  Создание. Сам объект должен передаваться в качестве параметра.
    public void addNewTask(Task task) {
        int id = task.getId();
        tasks.put(id, task);
    }

    // Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateTask(int id, Task task) {
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
        } else {
            printErrorId();
        }
    }

    // Удаление по идентификатору.
    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            printErrorId();
        }

    }

    // МЕТОДЫ ДЛЯ ПОДЗАДАЧ:

    // получение списка всех подзадач
    public HashMap<Integer, Subtask> getListOfAllSubtasks() {
        return subtasks;
    }

    //  Создание. Сам объект должен передаваться в качестве параметра.
    public void addNewSubtask(Subtask subtask) {
        int id = subtask.getId();
        subtasks.put(id, subtask);
    }

    // Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
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
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic);
        }
    }

    // Получение подзадачи по идентификатору
    public Subtask getSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else {
            printErrorId();
            return null;
        }
    }

    // Удаление подзадачи по идентификатору
    public void removeSubtaskById(int id) {
        if(!subtasks.containsKey((id))) {
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
    public void addNewEpic(Epic epic) {
        int id = epic.getId();
        epics.put(id, epic);
    }

    // получение списка всех эпиков
    public HashMap<Integer, Epic> getListOfAllEpics() {
        return epics;
    }

    // Добавление подзадачи в эпик
    public void addSubtaskInEpic(Epic epic, Subtask subtask) {
        subtask.setEpicId(epic.getId());
        addNewSubtask(subtask);
        epic.getSubtaskIds().add(subtask.getId());
        updateEpicStatus(epic);
    }


    // Получение списка всех подзадач определённого эпика.
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
    private void updateEpicStatus(Epic epic) {
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
    public Epic getEpicById(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        } else {
            printErrorId();
            return null;
        }
    }

    // Удаление всех эпиков и подзадач, которые с ними связаны
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
        epics.clear();
    }

    // Удаление эпика по идентификатору с удалением подзадач
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

}