package services;

import database.mmasenheimerdbex.database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    // Defines the contract for managing AuthorEntity objects in the app's service layer
    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorAuthorEntity);

    void delete(Long id);

}
