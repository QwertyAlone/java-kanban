package model;

import org.junit.jupiter.api.Test;
import util.Status;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    //проверьте, что экземпляры класса Task равны друг другу, если равен их id
    @Test
    void taskEqualsIfIdSame() {
        Task task1 = new Task("t1", "d1", Status.NEW, 1);
        Task task2 = new Task("t1", "d1", Status.NEW, 1);
        assertEquals(task1, task2, "Экземпляры не равны");

    }

}