package services.impl;

import database.mmasenheimerdbex.database.domain.entities.BookEntity;
import database.mmasenheimerdbex.database.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        // Using injection to get a BookRepository instance
    }

    @Override
    public BookEntity createUpdateBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        // Set the isbn on a given book and save
        return bookRepository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        // Gets all the books using bookRepository.findAll()
        return StreamSupport
                .stream(
                        bookRepository.findAll().spliterator(),
                        false)
                .collect(Collectors.toList());
                // Converts the iterable result to List<BookEntity>
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        // Returns a paginated list of books
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        // Looks up a book by ISBN
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExists(String isbn) {
        // Check if a book with the given ISBN exists in the database
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        // Partially updates the book

        bookEntity.setIsbn(isbn);

        return bookRepository.findById(isbn).map(existingBook -> {
            // Find the existing book from the database
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            return bookRepository.save(existingBook);
            // If exists, updates the title only if the new one is provided, then saves

        }).orElseThrow(() -> new RuntimeException("Book does not exist"));
        // Otherwise throws a RuntimeException
    }

    @Override
    public void delete(String isbn) {
        // Deletes book by specified isbn
        bookRepository.deleteById(isbn);
    }

}
