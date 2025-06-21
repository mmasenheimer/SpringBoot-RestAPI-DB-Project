package database.mmasenheimerdbex.database.repositories;

import database.mmasenheimerdbex.database.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
// Same as @Component, but describes a repository
// Now the Author repository is a bean that can be injected anywhere that is needed

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    Iterable<AuthorEntity> ageLessThan(int age);

    @Query("SELECT a from AuthorEntity a where a.age > ?1")
    // If Spring data JPA cannot pick up the basic SQL method you're going for, add a @Query annotation with HQL
    // to help spring Jpa understand the functionality you're going for
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);
}
