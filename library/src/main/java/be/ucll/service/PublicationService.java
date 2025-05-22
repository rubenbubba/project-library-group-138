package be.ucll.service;

import be.ucll.model.Book;
import be.ucll.model.Magazine;
import be.ucll.model.Publication;
import be.ucll.repository.PublicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService {

    private final PublicationRepository repo;

    public PublicationService(PublicationRepository repo) {
        this.repo = repo;
    }

    public Publication add(Publication p) {
        return repo.save(p);
    }

    /**
     * title : optional substring filter (case-insensitive, null → “”)
     * type  : null → any, “BOOK” or “MAGAZINE” (case-insensitive)
     */
    public List<Publication> getPublications(String title, String type) {
        if (title == null) title = "";

        List<Publication> list = repo.findByTitleContainingIgnoreCase(title);

        if (type == null) return list;

        /* ---------- FIX ---------- */
        final String filterType = type.toUpperCase();   // ★ make effectively-final
        return list.stream()
                .filter(p -> (filterType.equals("BOOK")     && p instanceof Book) ||
                        (filterType.equals("MAGAZINE") && p instanceof Magazine))
                .collect(Collectors.toList());
    }

    public List<Publication> getPublicationsWithCopies(int min) {
        return repo.findByNumberOfCopiesGreaterThanEqual(min);
    }
}
