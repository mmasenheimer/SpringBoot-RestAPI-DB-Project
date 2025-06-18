package mmasenheimerdbex.dao.impl;

import database.mmasenheimerdbex.database.dao.impl.AuthorDaoImpl;
import database.mmasenheimerdbex.database.domain.Author;
import mmasenheimerdbex.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
// Enables Mockito annotations like @Mock and @InjectMocks

public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;
    // Creating a mock JdbcTemplate used in AuthorDaoImpl

    @InjectMocks
    private AuthorDaoImpl underTest;
    // Creating an instance of AuthorDaoImpl and injects the jdbcTemplate mock into it

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthorA();

        // Creating a test Author using the create author method in the TestDataUtil class with
        // ID "1L", name "Abigail rose", and age "80"

        underTest.create(author);
        // Calling the real .create() method of AuthorDaoImpl using the mocked JdbcTemplate

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L), eq("Abigail Rose"), eq(80)
        );
        // This is just a verification that the jdbcTemplate.update() method was called with the correct
        // SQL query string and the correct author fields
    }


    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne(1L);
        // Calls the .findOne() method with the authorid of 1L

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)

                // This verifies that the jdbcTemplate.query() was called with the expected SQL query,
                // Any AuthorRowMapper instance, and the expected parameter 1L

                // eq() here meaning "check that jdbcTemplate.update() was called with....


        );
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql() {
        // Verifying that the AuthorDaoImpl.find() method generates and
        // executes the correct SQL query by using JdbcTemplate
        underTest.find();

        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );

        // Verify that jdbcTemplate.query() was called and the exact SQL  used was ... and
        // The rowMapper argument can be any instance of AuthorRowMapper

    }

    @Test public void testThatUpdateGeneratesTheCorrectSql() {
        // Verifies the update method in AuthorDaoImpl class generates the correct SQL with
        // The expected parameters

        Author author = TestDataUtil.createTestAuthorA();
        underTest.update(author.getId(), author);

        verify(jdbcTemplate).update(
                "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                1L, "Abigail Rose", 80, 1L
        );
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        underTest.delete(1L);
        // Testing the sql code was ran correctly to delete an author from the database
        verify(jdbcTemplate).update(
                "DELETE FROM authors WHERE id = ?",
                1L
        );

    }
}
