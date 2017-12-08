package pl.michal.olszewski.store;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.dto.StoreProductDTO;
import pl.michal.olszewski.product.ProductDTO;

@Service
@Profile("!test")
public class StoreServiceImpl implements StoreService {

    private final RestTemplate restTemplate;

    public StoreServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void removeProductsFromStore(StoreProductDTO storeProductDTO) {
        restTemplate.postForEntity("http://localhost:8082/api/v1/stores/removeProductsFromStore", storeProductDTO, ProductDTO[].class);
    }
}
