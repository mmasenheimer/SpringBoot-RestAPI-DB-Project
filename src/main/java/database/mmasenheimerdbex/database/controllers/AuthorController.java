package database.mmasenheimerdbex.database.controllers;


import database.mmasenheimerdbex.database.domain.Author;
import database.mmasenheimerdbex.database.domain.dto.AuthorDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import services.AuthorService;

@RestController
// Start REST development
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(path = "/authors")

    public AuthorDto createAuthor(@RequestBody AuthorDto author) {
        // Telling spring to at the http request post for Author object represented as JSON

        return authorService.createAuthor(author);

    }
}
