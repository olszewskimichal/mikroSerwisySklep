package pl.michal.olszewski.store;

import pl.michal.olszewski.dto.StoreProductDTO;

public interface StoreService {
    void removeProductsFromStore(StoreProductDTO storeProductDTO);
}
