package database.mmasenheimerdbex.database.dao.impl;

import database.mmasenheimerdbex.database.dao.AuthorDao;
import database.mmasenheimerdbex.database.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;
    // Make a JdbcTemplate

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    // Inject the jdbcTemplate via constructor method, which will handle the sql operations

    @Override
    public void create(Author author) {
        jdbcTemplate.update(
                "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)",
                author.getId(), author.getName(), author.getAge()
        );
        // Inserts a new author into the authors table using their name, id and age

    }

    @Override
    public Optional<Author> findOne(long authorId) {
        List<Author> results = jdbcTemplate.query(
                // Retrieves a single author by ID

                "SELECT id, name, age FROM authors WHERE id = ? LIMIT 1",
                new AuthorRowMapper(), authorId);
                // Result is mapped using the AuthorRowMapper to map the output to a java object

        return results.stream().findFirst();
        // Returns an Optional<Author> either the author found or an empty value

    }

    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Converting each row in the result set into an Author object
            return Author.builder()
                    .id(rs.getLong( "id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();

        }

    }

    @Override
    public List<Author> find() {
        return jdbcTemplate.query(
                // Retrieves all authors from the authors table
                "SELECT id, name, age FROM authors",
                new AuthorRowMapper()
        );
    }

    @Override
    public void update(long id, Author author) {
        jdbcTemplate.update(
                "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                author.getId(), author.getName(), author.getAge(), id
                // Executing the sql with the provided author fields
        );

    }

    @Override
    public void delete(long id) {
        // Deletion of an author by id from the database
        jdbcTemplate.update(
                "DELETE FROM authors WHERE id = ?",
                id

        );

    }
}
