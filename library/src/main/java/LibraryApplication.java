import be.ucll.model.Book;
import be.ucll.model.Magazine;
import be.ucll.model.Publication;

import java.util.ArrayList;
import java.util.List;

public class LibraryApplication {
    public static void main(String[] args) {
        List<Publication> library = new ArrayList<>();

        Book book1 = new Book("J.K. Rowling", "9780545010221", "Harry Potter", 2007, 4);
        Magazine mag1 = new Magazine("Time", "Anna Wintour", "1234-5678", 2020, 6);

        library.add(book1);
        library.add(mag1);

        for (Publication pub : library) {
            System.out.println(pub);
        }
    }
}
