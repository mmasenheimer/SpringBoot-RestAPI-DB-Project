package mmasenheimerdbex.dao.impl;


import database.mmasenheimerdbex.database.dao.impl.BookDaoImpl;
import database.mmasenheimerdbex.database.domain.Book;
import mmasenheimerdbex.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
// Tells JUnit to use Mockito's extension for dependency injection and mocking

public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;
    // Creates a mocked JdbcTemplate

    @InjectMocks
    private BookDaoImpl underTest;
    // Injects the jdbcTemplate mock into a new BookDaoImpl instance using underTest

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {

        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);
        // Calls the method that inserts a book with the below sql

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("978-1-2345-6789-0"),
                eq("The Shadow in the Attic"),
                eq(1L)
        );
        // Verifies that the update method was called on jdbcTemplate with the correct SQL and parameters

    }

    @Test
    public void testThatFindOneBookGeneratesCorrectSql () {
        underTest.findOne("978-1-2345-6789-0");
        // Calling the find method with retrieves a book by ISBN

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id from books WHERE isbn = ? LIMIT 1"),
                // Verifies  the correct SQL query is used

                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                // Ignoring the exact BookRowMapper instance

                eq("978-1-2345-6789-0")
                // Confirms the ISBN value passed was ....
        );
    }

    @Test
    public void testThatFindGeneratesCorrectSql () {
        underTest.find();
        // Calls find() fetching all of the rows from the books table
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                // Checks the method correctly calls jdbcTemplate.query() with the expected SQL and
                // A BookRowMapper instance
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }


    @Test
    public void testThatUpDateGeneratesCorrectSql () {

        Book book = TestDataUtil.createTestBookA();
        underTest.update("978-1-2345-6789-0", book);
        // Creates a test book and calls the update method on the book

        verify(jdbcTemplate).update(
                "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                "978-1-2345-6789-0",
                "The Shadow in the Attic",
                1L,
                "978-1-2345-6789-0"
        );
        // Verifying the jdbcTemplate.update() was called with the correct sql and parameters

    }

    @Test
    public void testThatBookDeleteGeneratesCorrectSql () {
        // Testing that the correct sql is used to delete a book
        underTest.delete("978-1-2345-6789-0");
        verify(jdbcTemplate).update(
                "DELETE FROM books where isbn = ?",
                "978-1-2345-6789-0"

        );


    }

}
