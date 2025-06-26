package database.mmasenheimerdbex.database.domain.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
// Generates getters and setters
@AllArgsConstructor
// Constructor with all arguments
@NoArgsConstructor
// Constructor with no arguments
@Builder
// Enable fluent object creation
@Entity
// Marks as a JPA entity so hibernate knows to persist it
@Table(name = "books")
// Maps the entity to the authors table in the database
public class BookEntity {

    @Id
    // Isbn as the primary key of the table
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    // Many books can be associated with one author, and all operations
    // Will be cascaded to the AuthorEntity
    @JoinColumn(name = "author_id")
    // The foreign key column in the books table is author_id
    // Links to the id field of the AuthorEntity
    private AuthorEntity author;
}
