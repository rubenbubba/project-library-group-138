package be.ucll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Exclude R2DBC auto‐configuration to avoid the missing io.r2dbc.spi.ValidationDepth
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

@SpringBootApplication(
        exclude = {
                R2dbcAutoConfiguration.class
        }
)
public class LibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }
}
