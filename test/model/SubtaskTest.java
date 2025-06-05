package model;

import org.junit.jupiter.api.Test;
import util.Status;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    //проверьте, что наследники класса Task равны друг другу, если равен их id
    @Test
    void subtaskEqualsIfIdSame() {
        Subtask subtask1 = new Subtask("t1", "d1", Status.NEW, 1,1);
        Subtask subtask2 = new Subtask("t1", "d1", Status.NEW, 1,1);
        assertEquals(subtask1, subtask2, "Экземпляры не равны");

    }
}