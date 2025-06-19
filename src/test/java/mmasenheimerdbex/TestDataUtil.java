package mmasenheimerdbex;

import database.mmasenheimerdbex.database.domain.Author;
import database.mmasenheimerdbex.database.domain.Book;

public final class TestDataUtil {
    // This whole class is full of methods for creating book or author objects

    private TestDataUtil() {

    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(null)
                .name("Abigail Rose")
                .age(80)
                .build();
    }
    public static Author createTestAuthorB() {
        return Author.builder()
                .id(null)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(null)
                .name("Jesse A Casey")
                .age(24)
                .build();
    }


    public static Book createTestBookA(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();

    }

    public static Book createTestBookB(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-1")
                .title("Beyond the Horizon")
                .author(author)
                .build();

    }

    public static Book createTestBookC(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-2")
                .title("The Last Ember")
                .author(author)
                .build();

    }
}
