package manager;

public class Managers {
    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}
/* Универсальность
TaskManager manager = Managers.getDefault();
manager.addNewTask(new Task(...));*/
