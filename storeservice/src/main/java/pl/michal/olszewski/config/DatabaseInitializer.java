package pl.michal.olszewski.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.entity.Address;
import pl.michal.olszewski.entity.Store;
import pl.michal.olszewski.repository.StoreRepository;


@Profile("!test")
@Service
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final StoreRepository storeRepository;

    public DatabaseInitializer(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Bean
    public RestTemplate loadRestTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... strings) {
        storeRepository.save(Store.builder().name("Sklep 1").address(Address.builder().city("city").street("str").country("pl").zipCode("zip").build()).build());
    }
}