package mmasenheimerdbex;

import database.mmasenheimerdbex.database.BooksApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BooksApiApplication.class)
// Start the full application context for testing
class BooksApiApplicationTests {

    @Test
    void contextLoads() {
    }
    // Sanity check, if missing beans or bad config this test will fail

}
