package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    // убедитесь, что утилитарный класс всегда возвращает проинициализированные и
    // готовые к работе экземпляры менеджеров
    @Test
    public void testManagersReturnNonNull() {
        TaskManager tm = Managers.getDefault();
        HistoryManager hm = Managers.getDefaultHistory();
        assertNotNull(tm, "TaskManager не должен быть null");
        assertNotNull(hm, "HistoryManager не должен быть null");
    }

}