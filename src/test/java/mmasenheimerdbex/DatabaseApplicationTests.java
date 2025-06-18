package mmasenheimerdbex;

import database.mmasenheimerdbex.database.DatabaseApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DatabaseApplication.class)
// Start the full application context for testing
class DatabaseApplicationTests {

    @Test
    void contextLoads() {
    }
    // Sanity check, if missing beans or bad config this test will fail

}
