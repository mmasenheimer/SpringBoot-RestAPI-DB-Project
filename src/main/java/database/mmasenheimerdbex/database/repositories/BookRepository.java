package database.mmasenheimerdbex.database.repositories;


import database.mmasenheimerdbex.database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
// Acts like @Component

public interface BookRepository extends CrudRepository<BookEntity, String> {
}
