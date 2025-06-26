package mmasenheimerdbex.repositories;

import database.mmasenheimerdbex.database.BooksApiApplication;

import database.mmasenheimerdbex.database.domain.entities.AuthorEntity;
import database.mmasenheimerdbex.database.repositories.AuthorRepository;
import jakarta.transaction.Transactional;
import mmasenheimerdbex.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BooksApiApplication.class)
// Load the full application context for testing, and use DatabseApplpication.class as the entry point

@ExtendWith(SpringExtension.class)
// Enable dependency injection and integrates JUnit 5

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// Resetting the context after each test, for a clean slate each time ran

public class AuthorEntityRepositoryIntegrationTests {

    private AuthorRepository underTest;
    // Declaring a field to hold the AuthorDaoImpl bean under test

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }
    // Spring injects the AuthorDaoImpl bean into the test class

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {

        AuthorEntity author = TestDataUtil.createTestAuthorEntityA();
        // Creates an Author object using the TestDataUtil method (Author builder)
        author.setId(null);

        underTest.save(author);

        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        // Checking to see if it's not an optional entity, it does exist

        assertThat(result.get()).isEqualTo(author);
        // Asserting that the retrieved author is equal to the one inserted

    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        // Testing the correct SQL querying on multiple authors. Using three different Author methods ABC in the Test
        // DataUtil class

        AuthorEntity authorA = TestDataUtil.createTestAuthorEntityA();
        authorA.setId(null);
        underTest.save(authorA);

        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);

        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);

        Iterable<AuthorEntity> result = underTest.findAll();
        // Find() will retrieve all of the authors in the database and put them in a list


        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorA, authorB, authorC);
        // Verifying that exactly 3 authors are returned, and the returned authors are the same instances
        // Or deeply equal as authorA, B, and C, as well as the order they were inserted

    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorEntityA.setId(null);
        underTest.save(authorEntityA);
        // Create a new Author object and save them in the database

        authorEntityA.setName("UPDATED");
        underTest.save(authorEntityA);
        // Calls the update method in author DaoImpl updating the author

        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        // Retrieving the author back from the database after the update

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntityA);
        // Check that the author is found in the storage and the author has been
        // Correctly updated

    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        // Testing if an author was successfully deleted from the database

        AuthorEntity authorA = TestDataUtil.createTestAuthorEntityA();
        authorA.setId(null);
        underTest.save(authorA);
        underTest.deleteById(authorA.getId());
        // Create a new author and delete them from the db

        Optional<AuthorEntity> result = underTest.findById(authorA.getId());
        assertThat(result).isEmpty();
        // Trying to find the author will return a null and pass the test if successfully deleted

    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan() {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        testAuthorA.setId(null);
        underTest.save(testAuthorA);
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        underTest.save(testAuthorB);
        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        underTest.save(testAuthorC);
        // Creating three authors to put into the database

        Iterable<AuthorEntity> result = underTest.ageLessThan(50);

        assertThat(result).containsExactly(testAuthorB, testAuthorC);
    }

    @Test
    public void testThatGetAuthorsWithAgeGreaterThan() {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        testAuthorA.setId(null);
        underTest.save(testAuthorA);
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        underTest.save(testAuthorB);
        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        underTest.save(testAuthorC);
        // Creating three authors to put into the database

        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);
        assertThat(result).containsExactly(testAuthorA);

    }




}
