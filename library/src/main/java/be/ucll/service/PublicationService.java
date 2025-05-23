package be.ucll.service;

import be.ucll.model.Book;
import be.ucll.model.Magazine;
import be.ucll.model.Publication;
import be.ucll.repository.BookRepository;
import be.ucll.repository.MagazineRepository;
import be.ucll.repository.PublicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * Business operations around publications (books + magazines).
 */
@Service
public class PublicationService {

    private final PublicationRepository publicationRepo;
    private final BookRepository        bookRepo;
    private final MagazineRepository    magazineRepo;

    public PublicationService(PublicationRepository publicationRepo,
                              BookRepository        bookRepo,
                              MagazineRepository    magazineRepo) {
        this.publicationRepo = publicationRepo;
        this.bookRepo        = bookRepo;
        this.magazineRepo    = magazineRepo;
    }

    /* ------------------------------------------------------------------
       CRUD helpers
       ------------------------------------------------------------------ */

    public Publication add(Publication publication) {
        return publicationRepo.save(publication);
    }

    /* ------------------------------------------------------------------
       High-level queries used by the REST controller
       ------------------------------------------------------------------ */

    /**
     * All publications that match an optional *title* filter and/or *type*
     * filter.
     *
     * @param title  optional – substring (case-insensitive) that must be
     *               present in the title
     * @param type   optional – “book” or “magazine”, case-insensitive
     */
    public List<Publication> getPublications(String title, String type) {

        Stream<Publication> stream = publicationRepo.findAll().stream();

        if (title != null && !title.isBlank()) {
            String t = title.toLowerCase(Locale.ROOT);
            stream = stream.filter(p -> p.getTitle()
                    .toLowerCase(Locale.ROOT)
                    .contains(t));
        }

        if (type != null && !type.isBlank()) {
            String t = type.toLowerCase(Locale.ROOT);
            if (t.equals("book")) {
                stream = stream.filter(Book.class::isInstance);
            } else if (t.equals("magazine")) {
                stream = stream.filter(Magazine.class::isInstance);
            }
        }

        return stream.toList();
    }

    /**
     * All publications that still have at least <code>minCopies</code> copies
     * available.
     */
    public List<Publication> getPublicationsWithCopies(int minCopies) {
        return publicationRepo.findByNumberOfCopiesGreaterThan(minCopies);
    }

    /* ------------------------------------------------------------------
       Convenience split per subclass
       ------------------------------------------------------------------ */

    public List<Book> getAllBooks()                 { return bookRepo.findAll(); }

    public List<Magazine> getAllMagazines()         { return magazineRepo.findAll(); }

    public List<Publication> getAll()               { return publicationRepo.findAll(); }

    public List<Publication> getAvailable()         { return getPublicationsWithCopies(0); }

    public List<Book> getAvailableBooks()           { return bookRepo.findByNumberOfCopiesGreaterThan(0); }

    public List<Magazine> getAvailableMagazines()   { return magazineRepo.findByNumberOfCopiesGreaterThan(0); }
}
