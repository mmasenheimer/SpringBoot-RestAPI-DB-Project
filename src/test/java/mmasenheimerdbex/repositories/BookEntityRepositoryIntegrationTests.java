package mmasenheimerdbex.repositories;

import database.mmasenheimerdbex.database.BooksApiApplication;
import database.mmasenheimerdbex.database.domain.entities.AuthorEntity;
import database.mmasenheimerdbex.database.domain.entities.BookEntity;
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

@SpringBootTest(classes = BooksApiApplication.class)
// Load the full application context for testing at the DatabaseApplication root

@ExtendWith(SpringExtension.class)
// This enables dependency injection and integrates JUnit testing2

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// Clean the context after running each individual test case

public class BookEntityRepositoryIntegrationTests {


    private final BookRepository underTest;
    // Holds the BookDaoImpl bean during a test
    private final AuthorRepository authorRepository;

    @Autowired
    // Resolve and inject the required dependencies into the class
    public BookEntityRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
    }
    // Spring injects the BookDaoImpl bean into the test class

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);

        BookEntity bookEntity = TestDataUtil.createTestBookA(author);
        // Creates a Book object using the TestDataUtil method (book builder)

        underTest.save(bookEntity);
        // Call the  DAO method to insert the Book into the database

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        // Queries the database for the book by ISBN

        assertThat(result).isPresent();

        assertThat(result.get()).isEqualTo(bookEntity);

        // Just checking to see if the created and inserted book is equal to the one
        // Extracted from the database
    }

    @Test
    public void testThatBMultipleBooksCanBeCreatedAndRecalled() {
        // Check to see whether multiple Book records can be created  and then retrieved correctly


        AuthorEntity author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);

        // Creates a test author and inserts them into the database

        BookEntity bookEntityA = TestDataUtil.createTestBookA(author);
        underTest.save(bookEntityA);

        BookEntity bookEntityB = TestDataUtil.createTestBookB(author);
        underTest.save(bookEntityB);

        BookEntity bookEntityC = TestDataUtil.createTestBookC(author);
        underTest.save(bookEntityC);

        // Creates Books A, B, C and assigns it to the saved author ID and then saves them

        Iterable<BookEntity> result = underTest.findAll();
        List<BookEntity> bookEntities = (List<BookEntity>) result;
        // Retrieve all books from the database


        assertThat(bookEntities)
                .hasSize(3)
                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
        // Assert that exactly 3 books were retrieved, and he lost contains the exact 3 books inserted

        assertThat(bookEntities)
                .extracting(BookEntity::getIsbn)
                .containsExactlyInAnyOrder(
                        "978-1-2345-6789-0",
                        "978-1-2345-6789-1",
                        "978-1-2345-6789-2"
                );

        assertThat(bookEntities)
                .extracting(BookEntity::getTitle)
                .containsExactlyInAnyOrder(
                        "The Shadow in the Attic",
                        "Beyond the Horizon",
                        "The Last Ember"
                );
    }


    @Test
    public void testThatBookCanBeUpdated() {
        // This tests if a books information can be updated in the database

        AuthorEntity author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);
        // Create an author and add it to the database

        BookEntity bookEntityA = TestDataUtil.createTestBookA(author);
        underTest.save(bookEntityA);
        // Create a book and add it to the database

        bookEntityA.setTitle("UPDATED");
        underTest.save(bookEntityA);
        // Call the update method on the book

        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        // Retrieve the book

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityA);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("UPDATED");
        assertThat(result.get().getIsbn()).isEqualTo(bookEntityA.getIsbn());
        assertThat(result.get().getAuthor().getName()).isEqualTo("Abigail Rose");
        // Make sure the book is present and the updated version checks out

    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);
        // Create author for the book

        BookEntity bookEntityA = TestDataUtil.createTestBookA(author);

        underTest.save(bookEntityA);

        // Create the book and put it into the db
        underTest.deleteById(bookEntityA.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        // Delete the book and try to query it out of the database

        assertThat(result).isEmpty();
        // Should be empty after deletion

    }

}
