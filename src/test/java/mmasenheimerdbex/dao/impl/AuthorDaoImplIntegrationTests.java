package mmasenheimerdbex.dao.impl;

import database.mmasenheimerdbex.database.DatabaseApplication;
import database.mmasenheimerdbex.database.dao.impl.AuthorDaoImpl;
import database.mmasenheimerdbex.database.domain.Author;
import mmasenheimerdbex.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DatabaseApplication.class)
// Load the full application context for testing, and use DatabseApplpication.class as the entry point

@ExtendWith(SpringExtension.class)
// Enable dependency injection and integrates JUnit 5

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// Resetting the context after each test, for a clean slate each time ran

public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;
    // Declaring a field to hold the AuthorDaoImpl bean under test

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }
    // Spring injects the AuthorDaoImpl bean into the test class

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {

        Author author = TestDataUtil.createTestAuthorA();
        // Creates an Author object using the TestDataUtil method (Author builder)

        underTest.create(author);

        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        // Checking to see if it's not an optional entity, it does exist

        assertThat(result.get()).isEqualTo(author);
        // Asserting that the retrieved author is equal to the one inserted

    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        // Testing the correct SQL querying on multiple authors. Using three different Author methods ABC in the Test
        // DataUtil class

        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.create(authorA);

        Author authorB = TestDataUtil.createTestAuthorB();
        underTest.create(authorB);

        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.create(authorC);

        List<Author> result = underTest.find();
        // Find() will retrieve all of the authors in the database and put them in a list


        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorA, authorB, authorC);
        // Verifying that exactly 3 authors are returned, and the returned authors are the same instances
        // Or deeply equal as authorA, B, and C, as well as the order they were inserted

    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.create(authorA);
        // Create a new Author object and save them in the database

        authorA.setName("UPDATED");
        underTest.update(authorA.getId(), authorA);
        // Calls the update method in author DaoImpl updating the author

        Optional<Author> result = underTest.findOne(authorA.getId());
        // Retrieving the author back from the database after the update

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
        // Check that the author is found in the storage and the author has been
        // Correctly updated

    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        // Testing if an author was successfully deleted from the database

        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.create(authorA);
        underTest.delete(authorA.getId());
        // Create a new author and delete them from the db

        Optional<Author> result = underTest.findOne(authorA.getId());
        assertThat(result).isEmpty();
        // Trying to find the author will return a null and pass the test if successfully deleted

    }




}
