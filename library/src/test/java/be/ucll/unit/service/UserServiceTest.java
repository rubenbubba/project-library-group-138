package be.ucll.unit.service;

import be.ucll.model.Book;
import be.ucll.repository.PublicationRepository;
import be.ucll.service.PublicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublicationServiceTest {

    private PublicationRepository repo;
    private PublicationService    service;

    @BeforeEach
    void setup() {
        repo     = mock(PublicationRepository.class);
        service  = new PublicationService(repo);
    }

    @Test
    void getPublications_titleFilterDelegatesToRepo() {
        when(repo.findByTitleContainingIgnoreCase("clean"))
                .thenReturn(List.of(new Book("Clean Code",2008,4,
                        "Robert C. Martin","978...")));
        assertThat(service.getPublications("clean", null)).hasSize(1);
        verify(repo).findByTitleContainingIgnoreCase("clean");
    }

    @Test
    void getPublications_typeFilterBook() {
        service.getPublications(null,"book");
        verify(repo).findByAuthorNotNull();
    }
}
