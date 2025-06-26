package database.mmasenheimerdbex.database.mappers.impl;

import database.mmasenheimerdbex.database.domain.dto.BookDto;
import database.mmasenheimerdbex.database.domain.entities.BookEntity;
import database.mmasenheimerdbex.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {
    // This class is responsible for converting bookEntity to BookDto
    // And BookDto to BookEntity

    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        // Constructor injection to receive the modelMapper bean
    }

    @Override
    public BookDto mapTo(BookEntity book) {
        // Converts a bookEntity from the database into a BookDto
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        // Converts a BookDto (from API request) into a bookEntity to save in DB
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
