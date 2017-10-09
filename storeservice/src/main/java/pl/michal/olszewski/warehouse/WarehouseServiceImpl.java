package pl.michal.olszewski.warehouse;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.dto.WarehouseProductDTO;
import pl.michal.olszewski.product.ProductDTO;

@Service
@Profile("!test")
public class WarehouseServiceImpl implements WarehouseService {

    private final RestTemplate restTemplate;

    public WarehouseServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void removeProductsFromWarehouse(WarehouseProductDTO warehouseProductDTO) {
        restTemplate.postForEntity("http://localhost:8081/api/v1/warehouses/removeProductsFromWarehouse", warehouseProductDTO, ProductDTO[].class);
    }
}
