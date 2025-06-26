package services.impl;

import database.mmasenheimerdbex.database.domain.entities.AuthorEntity;
import database.mmasenheimerdbex.database.repositories.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import services.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        // Constructor injection

    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
        // Pass through for service layer
    }

    @Override
    public List<AuthorEntity> findAll() {
        // Making a list of AuthorEntities using spliterator
        return StreamSupport.stream(authorRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);

        return authorRepository.findById(id).map(existingAuthor -> {

            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);

            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            // If the author entity has an age, and it's not null, se that on the existing author
            // As found from the database

            return authorRepository.save(existingAuthor);

        }).orElseThrow(() -> new RuntimeException("Author does not exist"));

    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

}
