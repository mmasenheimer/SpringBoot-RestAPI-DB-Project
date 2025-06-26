package database.mmasenheimerdbex.database.controllers;

import database.mmasenheimerdbex.database.domain.dto.AuthorDto;
import database.mmasenheimerdbex.database.domain.entities.AuthorEntity;
import database.mmasenheimerdbex.database.mappers.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
// Start REST development; this class handles REST requests
public class AuthorController {

    private AuthorService authorService;
    // Handles the business logic and database access
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        // Dependency constructor injection
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        // Telling spring to at the http request post for Author object represented as JSON
        AuthorEntity authorEntity = authorMapper.mapFrom(author);

        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
        // Takes an authorDto JSON object and maps it to AuthorEntity, saves it via authorService
        // And maps it back to DTO retuning http 201 CREATED

    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors() {
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream()
                // Starts a stream pipeline to process the list of AuthorEntity objects
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());
        // Returns a list of all authors, retrieves all authorEntity records from the service
        // Maps each on to AuthorDto

    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id) {
        // Returns one author by ID
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        return foundAuthor.map(authorEntity -> {
                    AuthorDto authorDto = authorMapper.mapTo(authorEntity);
                    return new ResponseEntity<>(authorDto, HttpStatus.OK);
                    // If the author is found, return it as JSON with 200 OK

                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                // Otherwise return 404 NOT_FOUND

    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            // Replaces an existing author completely

            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto) {

        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // If the author doesn't exist, returns a 404 NOT FOUND
        }

        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        // Otherwise, update the author with the new info and return 200 OK
        return new ResponseEntity<>(
                authorMapper.mapTo(savedAuthorEntity), HttpStatus.OK);

    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
            //Partially updates an existing author
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    ) {
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // Make sure the author exists first
        }

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorEntity);
        // Delegating to the partialUpdate method in the service and retuns the updated
        // Data with 200 OK

        return new ResponseEntity<>(
                authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id) {
        // Deleting an author by ID
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // Return 204 which is a success but no response body

    }

}
