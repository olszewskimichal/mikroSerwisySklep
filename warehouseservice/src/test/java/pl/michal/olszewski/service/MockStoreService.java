package pl.michal.olszewski.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.dto.StoreProductDTO;
import pl.michal.olszewski.store.StoreService;

@Service
@Profile("test")
public class MockStoreService implements StoreService {

    @Override
    public void removeProductsFromStore(StoreProductDTO storeProductDTO) {

    }
}
