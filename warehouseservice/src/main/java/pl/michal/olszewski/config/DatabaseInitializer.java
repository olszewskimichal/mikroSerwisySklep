package pl.michal.olszewski.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.entity.Address;
import pl.michal.olszewski.entity.Warehouse;
import pl.michal.olszewski.repository.WarehouseRepository;


@Profile("!test")
@Service
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final WarehouseRepository warehouseRepository;

    public DatabaseInitializer(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Bean
    public RestTemplate loadRestTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... strings) throws Exception {
        warehouseRepository.save(Warehouse.builder().name("magazyn centralny").address(Address.builder().city("city").street("str").country("pl").zipCode("zip").build()).build());
    }
}