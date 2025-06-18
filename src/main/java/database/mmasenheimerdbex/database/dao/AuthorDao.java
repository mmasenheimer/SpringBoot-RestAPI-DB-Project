package database.mmasenheimerdbex.database.dao;

import database.mmasenheimerdbex.database.domain.Author;

import java.util.Optional;
import java.util.List;

public interface AuthorDao {
    void create(Author author);
    // Persists a new Author in the database

    Optional<Author> findOne(long l);
    // Finds a single author by ID, returns an optional in case the author is not found

    List<Author> find();
    // Retrieves all authors from the database

    void update(long id, Author author);
    // Updates an author

    void delete(long id);
    // Deletes an author

}
