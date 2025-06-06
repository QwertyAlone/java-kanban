package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {
    // убедитесь, что утилитарный класс всегда возвращает проинициализированные и
    // готовые к работе экземпляры менеджеров
    @Test
    public void testManagerReturnNonNull() {
        TaskManager tm = Manager.getDefault();
        HistoryManager hm = Manager.getDefaultHistory();
        assertNotNull(tm, "TaskManager не должен быть null");
        assertNotNull(hm, "HistoryManager не должен быть null");
    }

}