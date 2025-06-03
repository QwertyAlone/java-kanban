import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Epic;
import model.Subtask;
import model.Task;
import util.Status;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new InMemoryTaskManager();

        // ТАСКИ
        System.out.println("Добавление новых задач:");
        Task task1 = new Task("Задача1", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewTask(task1);
        Task task2 = new Task("Задача2", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewTask(task2);
        System.out.println(taskManager.getListOfAllTasks());

        System.out.print("Получение по идентификатору: ");
        int id = scanner.nextInt();
        System.out.println(taskManager.getTaskById(id));

        System.out.print("Удаление по идентификатору: ");
        id = scanner.nextInt();
        taskManager.removeTaskById(id);
        System.out.println("Удалено. Обновленный список: ");
        System.out.println(taskManager.getListOfAllTasks());

        System.out.print("Задачу под каким идентификатором обновить? -> ");
        id = scanner.nextInt();
        taskManager.updateTask(id, new Task("НазваниеОбновлено", "Описание", Status.IN_PROGRESS, id));
        System.out.println("Обновлено");
        System.out.println(taskManager.getListOfAllTasks());

        System.out.println("Удаление списка задач ");
        taskManager.removeAllTasks();
        System.out.println("Удалено");
        System.out.println(taskManager.getListOfAllTasks());

        // ЭПИКИ И ПОДЗАДАЧИ
        Epic epic1 = new Epic("Эпик1", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewEpic(epic1);

        System.out.println("Добавление подзадач в эпик:");
        Subtask subtask1 = new Subtask("Подзадача1", "Описание", Status.NEW, taskManager.generateNewId(), epic1.getId());
        taskManager.addSubtaskInEpic(epic1, subtask1);
        Subtask subtask2 = new Subtask("Подзадача2", "Описание", Status.NEW, taskManager.generateNewId(), epic1.getId());
        taskManager.addSubtaskInEpic(epic1, subtask2);

        Epic epic2 = new Epic("Эпик2", "Описание", Status.NEW, taskManager.generateNewId());
        taskManager.addNewEpic(epic2);
        Subtask subtask3 = new Subtask("Подзадача3", "Описание", Status.NEW, taskManager.generateNewId(), epic2.getId());
        taskManager.addSubtaskInEpic(epic2, subtask3);

        System.out.println("Все подзадачи:");
        System.out.println(taskManager.getListOfAllSubtasks());

        System.out.println("Эпики:");
        System.out.println(taskManager.getListOfAllEpics());

        System.out.println("Получение подзадач определённого эпика:");
        System.out.println(taskManager.getSubtasksOfEpic(epic1));

        // Обновление статуса подзадачи и отражение в эпике
        System.out.println("Обновление статуса подзадачи:");
        taskManager.updateSubtask(subtask1.getId(), new Subtask("Подзадача1", "Описание",
                Status.IN_PROGRESS, subtask1.getId(), epic1.getId()));
        System.out.println(taskManager.getListOfAllEpics());

        // Все подзадачи DONE -> эпик DONE
        System.out.println("Обновление подзадач в DONE:");
        taskManager.updateSubtask(subtask1.getId(), new Subtask("Подзадача1", "Описание",
                Status.DONE, subtask1.getId(), epic1.getId()));
        taskManager.updateSubtask(subtask2.getId(), new Subtask("Подзадача2", "Описание",
                Status.DONE, subtask2.getId(), epic1.getId()));
        System.out.println(taskManager.getListOfAllEpics());

        // Проверка удаления подзадачи по ID и очистки в эпике
        System.out.println("Удаление подзадачи по ID:");
        taskManager.removeSubtaskById(subtask3.getId());
        System.out.println(taskManager.getListOfAllSubtasks());
        System.out.println(taskManager.getListOfAllEpics());

        // Удаление всех подзадач
        System.out.println("Удаление всех подзадач:");
        taskManager.removeAllSubtasks();
        System.out.println(taskManager.getListOfAllSubtasks());
        System.out.println(taskManager.getListOfAllEpics());

        // Удаление эпика по ID
        System.out.println("Удаление эпика по ID:");
        taskManager.removeEpicById(epic1.getId());
        System.out.println(taskManager.getListOfAllEpics());

        // Удаление всех эпиков
        System.out.println("Удаление всех эпиков:");
        taskManager.removeAllEpics();
        System.out.println(taskManager.getListOfAllEpics());
        System.out.println(taskManager.getListOfAllSubtasks());

    }

}

