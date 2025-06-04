package manager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
/* Универсальность
TaskManager manager = Managers.getDefault();
manager.addNewTask(new Task(...));*/
