package database.mmasenheimerdbex.database.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor

@NoArgsConstructor

@Builder

public class BookDto {
    // The BookDto helps decouple the API layer from the persistence layer

    private String isbn;

    private String title;

    private AuthorDto author;

}

