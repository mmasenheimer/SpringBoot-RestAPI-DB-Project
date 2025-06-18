package database.mmasenheimerdbex.database;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
// Log object for easy logging

public class DatabaseApplication {
    // Entry point for the application


    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }
    // Start the entire application, load the context and begin processing beans and components


}
