package be.ucll.unit;

import be.ucll.repository.PublicationRepository;
import be.ucll.service.PublicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PublicationServiceTest {

    private PublicationService svc() {
        return new PublicationService(new PublicationRepository());
    }

    @Test
    void filterByTitle_substring_caseInsensitive() {
        var list = svc().getPublications("harry", null);
        Assertions.assertEquals(1, list.size());
        Assertions.assertTrue(list.getFirst().getTitle().contains("Harry"));
    }

    @Test
    void filterByType_book_returnsOnlyBooks() {
        var list = svc().getPublications(null, "book");
        Assertions.assertTrue(list.stream().allMatch(p -> p instanceof be.ucll.model.Book));
    }

    @Test
    void filterByType_invalid_throws() {
        Assertions.assertThrows(RuntimeException.class,
                () -> svc().getPublications(null, "comic"));
    }

    @Test
    void filterByTitleAndType_combinationWorks() {
        var list = svc().getPublications("time", "magazine");
        Assertions.assertEquals(1, list.size());
        Assertions.assertTrue(list.getFirst() instanceof be.ucll.model.Magazine);
    }
}
