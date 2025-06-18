package mmasenheimerdbex.dao.impl;

import database.mmasenheimerdbex.database.DatabaseApplication;
import database.mmasenheimerdbex.database.dao.AuthorDao;
import database.mmasenheimerdbex.database.dao.impl.BookDaoImpl;
import database.mmasenheimerdbex.database.domain.Author;
import database.mmasenheimerdbex.database.domain.Book;
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

public class BookDaoImplIntegrationTests {

    private AuthorDao authorDao;

    private BookDaoImpl underTest;
    // Holds the BookDaoImpl bean during a test

    @Autowired
    // Resolve and inject the required dependencies into the class
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDao authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }
    // Spring injects the BookDaoImpl bean into the test class

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);


        Book book = TestDataUtil.createTestBookA();
        // Creates a Book object using the TestDataUtil method (book builder)

        book.setAuthorId(author.getId());

        underTest.create(book);
        // Call the  DAO method to insert the Book into the database

        Optional<Book> result = underTest.findOne(book.getIsbn());
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
        authorDao.create(author);
        // Creates a test author and inserts them into the database

        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        Book bookB = TestDataUtil.createTestBookB();
        bookB.setAuthorId(author.getId());
        underTest.create(bookB);

        Book bookC = TestDataUtil.createTestBookC();
        bookC.setAuthorId(author.getId());
        underTest.create(bookC);

        // Creates Books A, B, C and assigns it to the saved author ID and then saves them

        List<Book> result = underTest.find();
        // Retrieve all books from the database

        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
        // Assert that exactly 3 books were retrieved, and he lost contains the exact 3 books inserted
    }

    @Test
    public void testThatBookCanBeUpdated() {
        // This tests if a books information can be updated in the database

        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        // Create an author and add it to the database

        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);
        // Create a book and add it to the database

        bookA.setTitle("UPDATED");
        underTest.update(bookA.getIsbn(), bookA);
        // Call the update method on the book

        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        // Retrieve the book

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
        // Make sure the book is present and the updated version checks out

    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        // Create author for the book

        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        // Create the book and put it into the db

        underTest.delete(bookA.getIsbn());
        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        // Delete the book and try to query it out of the database

        assertThat(result).isEmpty();
        // Should be empty after deletion

    }

}
