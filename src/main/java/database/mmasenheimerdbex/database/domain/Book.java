package database.mmasenheimerdbex.database.domain;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Book {
    private String isbn;
    private String title;
    private Long authorId;
}
