package be.ucll.repository;

import be.ucll.model.Book;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PublicationDbInitializer {

    private final PublicationRepository repo;

    public PublicationDbInitializer(PublicationRepository repo) { this.repo = repo; }

    @PostConstruct
    public void seed() {
        if (repo.count() > 0) return;

        repo.save(new Book("Clean Code", 2008, 5, "Robert C. Martin", "9780132350884"));
        repo.save(new Book("Design Patterns", 1994, 3, "Gamma et al.",   "9780201633610"));
    }
}
