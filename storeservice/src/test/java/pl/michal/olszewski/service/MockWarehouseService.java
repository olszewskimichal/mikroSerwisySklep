package pl.michal.olszewski.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.dto.WarehouseProductDTO;
import pl.michal.olszewski.warehouse.WarehouseService;

@Service
@Profile("test")
public class MockWarehouseService implements WarehouseService {
    @Override
    public void removeProductsFromWarehouse(WarehouseProductDTO warehouseProductDTO) {

    }
}
