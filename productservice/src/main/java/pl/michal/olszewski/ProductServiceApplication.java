package pl.michal.olszewski;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import pl.michal.olszewski.config.DatabaseInitializer;

@SpringBootApplication
@Slf4j
public class ProductServiceApplication implements CommandLineRunner {

    //private final DatabaseInitializer initializer;

    public ProductServiceApplication(/*DatabaseInitializer initializer*/) {
        /*this.initializer = initializer;*/
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class);
    }

    @Override
    public void run(String... strings) {
        /*initializer.populate();*/
    }
}
