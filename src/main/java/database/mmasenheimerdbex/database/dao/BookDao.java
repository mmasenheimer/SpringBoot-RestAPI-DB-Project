package database.mmasenheimerdbex.database.dao;

import database.mmasenheimerdbex.database.domain.Book;

import java.util.Optional;
import java.util.List;

public interface BookDao {


    void create(Book book);
    // Persists a new book in the databse

    Optional<Book> findOne(String isbn);
    // Finds one book by isbn in the database, returns an optional in case the author isn't found

    List<Book> find();
    // Retrieves all books from the database

    void update(String isbn, Book book);
    // Updating a book in the database

    void delete(String isbn);
    // Deleting a book in the database

}
