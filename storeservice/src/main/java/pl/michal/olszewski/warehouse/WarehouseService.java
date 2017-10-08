package pl.michal.olszewski.warehouse;

import pl.michal.olszewski.dto.StoreProductDTO;
import pl.michal.olszewski.dto.WarehouseProductDTO;

public interface WarehouseService {
    void removeProductsFromWarehouse(WarehouseProductDTO warehouseProductDTO);
}
