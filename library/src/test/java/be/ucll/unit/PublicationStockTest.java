package be.ucll.unit;

import be.ucll.repository.PublicationRepository;
import be.ucll.service.PublicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PublicationStockTest {

    private PublicationService svc() {
        return new PublicationService(new PublicationRepository());
    }

    @Test
    void getPublicationsWithCopies_onlyReturnsThoseWithEnoughStock() {
        var list = svc().getPublicationsWithCopies(5);
        Assertions.assertTrue(list.stream().allMatch(p -> p.getNumberOfCopies() >= 5));
    }

    @Test
    void getPublicationsWithCopies_invalidMin_throws() {
        Assertions.assertThrows(RuntimeException.class,
                () -> svc().getPublicationsWithCopies(0));
    }
}
