package database.mmasenheimerdbex.database.controllers;

import database.mmasenheimerdbex.database.domain.dto.BookDto;
import database.mmasenheimerdbex.database.domain.entities.BookEntity;
import database.mmasenheimerdbex.database.mappers.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;
    // Contains the business logic and interacts with the repo

    public BookController(Mapper<BookEntity, BookDto> bookMapper,  BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
        // Constructor injection
    }

    @PutMapping(path ="/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(
            // Fully create or update a book
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto) {

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        // Take the book dto and convert it into an entity

        boolean bookExists = bookService.isExists(isbn);
        // Check if the book exists in the database via isbn

        BookEntity savedBook = bookService.createUpdateBook(isbn, bookEntity);

        BookDto savedBookDto = bookMapper.mapTo(savedBook);
        // Map from entity back to dto

        if(bookExists) {
            return new  ResponseEntity<>(savedBookDto, HttpStatus.OK);
            // Update as the book already exists in the db

        } else {
            return new  ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
            // Create as the book did not exist in the db

        }
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            // Partially updates a book
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        boolean bookExists = bookService.isExists(isbn);
        if(!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // Make sure the book exists first
        }

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);
        // Maps the DTO to entity and calls service for partial update

        return new ResponseEntity<>(
            bookMapper.mapTo(updatedBookEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/books")
    public Page<BookDto> listBooks(Pageable pageable) {
        // Returns a paginated list of books

        Page<BookEntity> books = bookService.findAll(pageable);
        return books.map(bookMapper::mapTo);
        // Map each book to a bookDto object
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        // Returns one book by its ISBN
        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook.map(bookEntity -> {

            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
            // If the book is found

                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                // If the book is not found
    }

    @DeleteMapping(path = "/books/{isbn}")
    // Deletes the book by ISBN
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // Return 204 indicating successful deletion

    }

}
