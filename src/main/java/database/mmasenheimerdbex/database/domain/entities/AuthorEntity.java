package database.mmasenheimerdbex.database.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// Getters and setters
@AllArgsConstructor
// Constructor with all fields
@NoArgsConstructor
// Constructor with no fields
@Builder
// Enable fluent object creation
@Entity
// Marks as a JPA entity so hibernate knows to persist it
@Table(name = "authors")
// Maps the entity to the authors table in the database
public class AuthorEntity {
    // This class defines a JPA entity for the authors table in the database

    @Id
    // Marks id as the primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    // Generates the id using a sequence strategy
    private Long id;

    private String name;

    private Integer age;

}