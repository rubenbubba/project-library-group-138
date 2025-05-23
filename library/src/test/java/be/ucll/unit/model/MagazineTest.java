package be.ucll.unit.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagazineTest {
    @Test
    void givenValidMagazine_whenCreate_thenFieldsAreSet() {
        Magazine m = new Magazine("Mag", "Editor", "1234-5678", 2020, 5);
        assertEquals("Mag", m.getTitle());
        assertEquals("Editor", m.getEditor());
        assertEquals("1234-5678", m.getIssn());
        assertEquals(2020, m.getPublicationYear());
        assertEquals(5, m.getAvailableCopies());
    }
    // ... other unhappy-paths
}