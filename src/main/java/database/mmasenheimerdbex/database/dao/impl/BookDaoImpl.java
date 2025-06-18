package database.mmasenheimerdbex.database.dao.impl;

import database.mmasenheimerdbex.database.dao.BookDao;
import database.mmasenheimerdbex.database.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {
    // Marks this class as a bean so it can be automatically injected

    private final JdbcTemplate jdbcTemplate;
    public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    // JdbcTemplate is injected by spring and is used to run SQL queries


    @Override
    public void create(Book book) {
        // This method inserts a new Book into the books table using its ISBN, title, and author ID
        jdbcTemplate.update(
                "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId()
        );

    }

    @Override
    public Optional<Book> findOne(String isbn) {
        List<Book> results = jdbcTemplate.query(
                // This searches for a single book by the ISBN
                "SELECT isbn, title, author_id from books WHERE isbn = ? LIMIT 1",
                new BookRowMapper(),
                // Uses BookRowMapper to convert rows into Book objects
                isbn
        );
        return results.stream().findFirst();
        // Returns the result as an Optional<Book>
    }

    public static class BookRowMapper implements RowMapper<Book> {
        // Nested class for converting each row in the result into a Book object

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Using the builder pattern to map the fields over to the object, then returns that object
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .build();

        }
    }

    @Override
    public List<Book> find() {
        return jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books",
                // Fetches all rows from the books table

                new BookRowMapper()
                // Passes it to BookRowMapper which converts it to a Book object
        );
        // Returns a List<Book> containing all the books found in the database
    }

    @Override
    public void update(String isbn, Book book) {
        // This is the method that actually updates the book in the database
        jdbcTemplate.update(
                "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                book.getIsbn(), book.getTitle(), book.getAuthorId(), isbn
        );

    }

    @Override
    public void delete(String isbn) {
        // Calling the method to delete the book from the database by isbn
        jdbcTemplate.update(
                "DELETE FROM books where isbn = ?",
                isbn
        );

    }

}
