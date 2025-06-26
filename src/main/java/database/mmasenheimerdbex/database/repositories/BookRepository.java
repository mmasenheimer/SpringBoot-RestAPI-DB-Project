package database.mmasenheimerdbex.database.repositories;


import database.mmasenheimerdbex.database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
// Acts like @Component

public interface BookRepository extends CrudRepository<BookEntity, String>,
        PagingAndSortingRepository<BookEntity, String> {
    // Defines how the app interacts with the db layer for bookEntity objects
}
