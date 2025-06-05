package model;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import util.Status;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    //проверьте, что наследники класса Task равны друг другу, если равен их id
    @Test
    void epicEqualsIfIdSame() {
        Epic epic1 = new Epic("t1", "d1", Status.NEW, 1);
        Epic epic2 = new Epic("t1", "d1", Status.NEW, 1);
        assertEquals(epic1, epic2, "Экземпляры не равны");
    }


}

