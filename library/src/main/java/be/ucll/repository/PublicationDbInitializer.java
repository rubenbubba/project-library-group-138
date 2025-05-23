package be.ucll.repository;

import be.ucll.model.Book;
import be.ucll.model.Magazine;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
class PublicationDbInitializer {

    private final PublicationRepository repo;

    PublicationDbInitializer(PublicationRepository repo) { this.repo = repo; }

    @PostConstruct
    void seed() {
        if (repo.count() > 0) return;      // already filled

        repo.save(new Book(
                "Harry Potter and the Philosopher's Stone", 1997, 5,
                "J. K. Rowling", "978-0-7475-3269-9"));

        repo.save(new Magazine(
                "National Geographic – May 2025", 2025, 12,
                "National Geographic Society", "0027-9358"));
    }
}
