package mmasenheimerdbex.repositories;

import database.mmasenheimerdbex.database.DatabaseApplication;
import database.mmasenheimerdbex.database.domain.Author;
import database.mmasenheimerdbex.database.domain.Book;
import database.mmasenheimerdbex.database.repositories.AuthorRepository;
import database.mmasenheimerdbex.database.repositories.BookRepository;
import mmasenheimerdbex.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DatabaseApplication.class)
// Load the full application context for testing at the DatabaseApplication root

@ExtendWith(SpringExtension.class)
// This enables dependency injection and integrates JUnit testing2

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// Clean the context after running each individual test case

public class BookRepositoryIntegrationTests {


    private final BookRepository underTest;
    // Holds the BookDaoImpl bean during a test
    private final AuthorRepository authorRepository;

    @Autowired
    // Resolve and inject the required dependencies into the class
    public BookRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
    }
    // Spring injects the BookDaoImpl bean into the test class

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);

        Book book = TestDataUtil.createTestBookA(author);
        // Creates a Book object using the TestDataUtil method (book builder)

        underTest.save(book);
        // Call the  DAO method to insert the Book into the database

        Optional<Book> result = underTest.findById(book.getIsbn());
        // Queries the database for the book by ISBN

        assertThat(result).isPresent();

        assertThat(result.get()).isEqualTo(book);

        // Just checking to see if the created and inserted book is equal to the one
        // Extracted from the database
    }

    @Test
    public void testThatBMultipleBooksCanBeCreatedAndRecalled() {
        // Check to see whether multiple Book records can be created  and then retrieved correctly


        Author author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);
        // Creates a test author and inserts them into the database

        Book bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);

        Book bookB = TestDataUtil.createTestBookB(author);
        underTest.save(bookB);

        Book bookC = TestDataUtil.createTestBookC(author);
        underTest.save(bookC);

        // Creates Books A, B, C and assigns it to the saved author ID and then saves them

        Iterable<Book> result = underTest.findAll();
        List<Book> books = (List<Book>) result;
        // Retrieve all books from the database

        assertThat(books)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
        // Assert that exactly 3 books were retrieved, and he lost contains the exact 3 books inserted

        assertThat(books)
                .extracting(Book::getIsbn)
                .containsExactlyInAnyOrder(
                        "978-1-2345-6789-0",
                        "978-1-2345-6789-1",
                        "978-1-2345-6789-2"
                );

        assertThat(books)
                .extracting(Book::getTitle)
                .containsExactlyInAnyOrder(
                        "The Shadow in the Attic",
                        "Beyond the Horizon",
                        "The Last Ember"
                );
    }


    @Test
    public void testThatBookCanBeUpdated() {
        // This tests if a books information can be updated in the database

        Author author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);
        // Create an author and add it to the database

        Book bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);
        // Create a book and add it to the database

        bookA.setTitle("UPDATED");
        underTest.save(bookA);
        // Call the update method on the book

        Optional<Book> result = underTest.findById(bookA.getIsbn());
        // Retrieve the book

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("UPDATED");
        assertThat(result.get().getIsbn()).isEqualTo(bookA.getIsbn());
        assertThat(result.get().getAuthor().getName()).isEqualTo("Abigail Rose");
        // Make sure the book is present and the updated version checks out

    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);
        // Create author for the book

        Book bookA = TestDataUtil.createTestBookA(author);

        underTest.save(bookA);

        // Create the book and put it into the db
        underTest.deleteById(bookA.getIsbn());
        Optional<Book> result = underTest.findById(bookA.getIsbn());
        // Delete the book and try to query it out of the database

        assertThat(result).isEmpty();
        // Should be empty after deletion

    }

}
