package database.mmasenheimerdbex.database;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "database.mmasenheimerdbex.database",  // your main package
        "services",                            // explicitly add your service packages
        "services.impl"
})
@Log
// Log object for easy logging

public class BooksApiApplication {
    // Entry point for the application


    public static void main(String[] args) {
        SpringApplication.run(BooksApiApplication.class, args);
    }
    // Start the entire application, load the context and begin processing beans and components


}
