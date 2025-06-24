package mmasenheimerdbex.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;

import database.mmasenheimerdbex.database.BooksApiApplication;
import database.mmasenheimerdbex.database.domain.dto.AuthorDto;
import database.mmasenheimerdbex.database.domain.entities.AuthorEntity;
import mmasenheimerdbex.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import services.AuthorService;

@SpringBootTest(classes = BooksApiApplication.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc

public class AuthorControllerIntegrationTests {

    private AuthorService authorService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
        // MockMvc for spring testing utility for http requests against controllers
        // objectMapper for converting Java objects to JSON
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        // Create test author

        testAuthorA.setId(null);
        // Server treats it as a new author creation

        String authorJson = objectMapper.writeValueAsString(testAuthorA);
        // Convert to JSON string

        mockMvc.perform(
                // This simulates sending an HTTP post request to /authors endpoint
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ). andExpect(
                MockMvcResultMatchers.status().isCreated()
                // Assert 201 creation status
        );

    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ). andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
                // Testing the ID
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
                // Testing the name
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
                // Testing the ID
        );

    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
                // Testing the ID
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
                // Testing the name
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(80)
                // Testing the ID
        );

    }

    @Test
    public void testThaGetAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThaGetAuthorReturnsAuthorWhenAuthorExist() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ). andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
                // Testing the ID
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
                // Testing the name
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
                // Testing the ID
        );

    }

    @Test
    public void testThaGetAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatFullUpdateAuthorReturns404WhenNoAuthorExists() throws Exception {
        AuthorDto testAuthorDtoA = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)

        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatFullUpdateAuthorReturns200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        // Convert saved entity to DTO to preserve version
        AuthorDto updateDto = new AuthorDto();
        updateDto.setId(savedAuthor.getId());
        updateDto.setVersion(savedAuthor.getVersion());
        updateDto.setName("Updated Name"); // Your update data
        updateDto.setAge(85); // Your update data

        String authorDtoJson = objectMapper.writeValueAsString(updateDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}