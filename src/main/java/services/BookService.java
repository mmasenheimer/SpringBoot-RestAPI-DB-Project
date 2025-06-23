package services;

import database.mmasenheimerdbex.database.domain.entities.BookEntity;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity book);

}
