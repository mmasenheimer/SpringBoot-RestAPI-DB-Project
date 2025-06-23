package mmasenheimerdbex.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;

import database.mmasenheimerdbex.database.BooksApiApplication;
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

@SpringBootTest(classes = BooksApiApplication.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc


public class AuthorControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
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
}