package blitz.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    @Test
    public void parseDate_isoFormat() {
        Deadline d = new Deadline("return book", "2019-12-02");
        assertNotNull(d.getDate());
        assertEquals(2019, d.getDate().getYear());
        assertEquals(12, d.getDate().getMonthValue());
        assertEquals(2, d.getDate().getDayOfMonth());
    }

    @Test
    public void parseDate_dMyyyyHHmm() {
        Deadline d = new Deadline("return book", "2/12/2019 1800");
        assertNotNull(d.getDate());
        assertEquals(18, d.getDate().getHour());
        assertEquals(0, d.getDate().getMinute());
    }
}
