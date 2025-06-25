package database.mmasenheimerdbex.database.controllers;

import database.mmasenheimerdbex.database.domain.dto.BookDto;
import database.mmasenheimerdbex.database.domain.entities.BookEntity;
import database.mmasenheimerdbex.database.mappers.Mapper;
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

    public BookController(Mapper<BookEntity, BookDto> bookMapper,  BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping(path ="/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(
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
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        boolean bookExists = bookService.isExists(isbn);
        if(!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);

        return new ResponseEntity<>(
            bookMapper.mapTo(updatedBookEntity),
            HttpStatus.OK);

    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {
        List<BookEntity> books = bookService.findAll();
        return books.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> foundBook = bookService.findOne(isbn);
        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);

                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
