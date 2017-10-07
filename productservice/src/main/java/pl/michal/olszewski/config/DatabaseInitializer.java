package pl.michal.olszewski.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.entity.Product;
import pl.michal.olszewski.repository.ProductRepository;
import pl.michal.olszewski.enums.ProductStatus;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.repository.ProductDefinitionRepository;
import pl.michal.olszewski.enums.ProductType;

import java.math.BigDecimal;

@Profile("development")
@Service
@Slf4j
@Transactional
@EnableJpaAuditing
public class DatabaseInitializer implements CommandLineRunner {
    private final ProductDefinitionRepository productDefinitionRepository;
    private final ProductRepository productRepository;

    public DatabaseInitializer(ProductDefinitionRepository productDefinitionRepository, ProductRepository productRepository) {
        this.productDefinitionRepository = productDefinitionRepository;
        this.productRepository = productRepository;
    }

    private void populate() {
        ProductDefinition productDefinition1 = new ProductDefinition("T-Shirt 1", "Koszulka", null, ProductType.TSHIRT, BigDecimal.ONE);
        ProductDefinition productDefinition2 = new ProductDefinition("Buty 1", "Buty", null, ProductType.SHOES, BigDecimal.TEN);
        ProductDefinition productDefinition3 = new ProductDefinition("Spodnie 1", "Spodnie", null, ProductType.PANTS, BigDecimal.ZERO);
        productDefinitionRepository.save(productDefinition1);
        productDefinitionRepository.save(productDefinition2);
        productDefinitionRepository.save(productDefinition3);

        Product product1 = new Product(productDefinition1, ProductStatus.DAMAGED);
        Product product2 = new Product(productDefinition1, ProductStatus.SOLD);
        Product product3 = new Product(productDefinition1, ProductStatus.IN_STORE);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

    public void checkDBQuerys() {
        Product product = productRepository.findOne(1L);
        log.info(product.toString());
        log.info(product.getProductDefinition().toString());
    }

    public void checkFetch() {
        Product fetchProductDetails = productRepository.findByIdFetchProductDetails(1L);
        log.info(fetchProductDetails.toString());
        log.info(fetchProductDetails.getProductDefinition().toString());
    }

    @Override
    public void run(String... strings) throws Exception {
        populate();
    }
}
