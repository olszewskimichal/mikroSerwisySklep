package pl.michal.olszewski.service;

import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.dto.ProductsStatusChangeDTO;
import pl.michal.olszewski.dto.StoreDTO;
import pl.michal.olszewski.dto.StoreProductDTO;
import pl.michal.olszewski.dto.WarehouseProductDTO;
import pl.michal.olszewski.entity.Store;
import pl.michal.olszewski.product.ProductDTO;
import pl.michal.olszewski.product.ProductService;
import pl.michal.olszewski.product.ProductStatus;
import pl.michal.olszewski.repository.StoreRepository;
import pl.michal.olszewski.warehouse.WarehouseService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {
    private static final int PAGE_LIMIT = 20;
    private final StoreRepository storeRepository;
    private final ProductService productService;
    private final WarehouseService warehouseService;


    public StoreService(StoreRepository storeRepository, ProductService productService, WarehouseService warehouseService) {
        this.storeRepository = storeRepository;
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    public StoreDTO getStoreById(final Long storeId) {
        return storeRepository.findById(storeId).map(v -> new StoreDTO(v.getName(), v.getAddress()))
                .orElse(null);
    }

    public StoreDTO getStoreByName(@NonNull final String storeName) {
        return storeRepository.findByName(storeName).map(v -> new StoreDTO(v.getName(), v.getAddress()))
                .orElse(null);
    }

    public List<StoreDTO> getStores(final Integer limit, final Integer page) {
        PageRequest pageRequest = new PageRequest(getPage(page), getLimit(limit));
        return storeRepository.findAll(pageRequest).getContent().stream().map(v -> new StoreDTO(v.getName(), v.getAddress())).collect(Collectors.toList());
    }

    public void createStore(final StoreDTO storeDTO) {
        Store store = new Store(storeDTO);
        storeRepository.save(store);
    }

    public void updateStore(final StoreDTO storeDTO, final Long id) {
        storeRepository.updateStore(storeDTO.getName(), storeDTO.getStreet(), storeDTO.getCity(), storeDTO.getCountry(), storeDTO.getZipCode(), id);
    }

    public void deleteStore(final Long id) {
        storeRepository.delete(id);
    }

    private int getLimit(final Integer size) {
        return (Objects.isNull(size) ? PAGE_LIMIT : size);
    }

    private int getPage(final Integer page) {
        return (Objects.isNull(page) ? 0 : page);
    }

    @Transactional
    public Boolean removeProductsFromStore(StoreProductDTO storeProductDTO) {
        Optional<Store> storeOptional = storeRepository.findById(storeProductDTO.getStoreId());
        storeOptional.ifPresent(store -> {
            String productsId = storeProductDTO.getProductsIds().stream().map(Object::toString).collect(Collectors.joining(","));
            List<ProductDTO> products = productService.getAvailableProductsForStoreFromApi(productsId);
            List<Long> productIds = productService.filterProductsForStatus(products, Collections.singletonList(ProductStatus.IN_STORE)).stream().map(ProductDTO::getProductId).collect(Collectors.toList());
            store.getProductIds().removeAll(productIds);
        });
        return true;
    }

    @Transactional
    public Boolean moveProductsToStore(StoreProductDTO storeProductDTO) {
        Optional<Store> storeOptional = storeRepository.findById(storeProductDTO.getStoreId());
        storeOptional.ifPresent(store -> {
            String productsId = storeProductDTO.getProductsIds().stream().map(Object::toString).collect(Collectors.joining(","));
            List<ProductDTO> products = productService.getAvailableProductsForStoreFromApi(productsId);
            List<Long> productIds = productService.filterProductsForStatus(products, ProductStatus.availableForMoveToStoreStatuses).stream().map(ProductDTO::getProductId).collect(Collectors.toList());
            List<Long> productIdsForRemoveFromWarehouse = productService.filterProductsForStatus(products, Collections.singletonList(ProductStatus.IN_WAREHOUSE)).stream().map(ProductDTO::getProductId).collect(Collectors.toList());
            warehouseService.removeProductsFromWarehouse(WarehouseProductDTO.builder().productsIds(productIdsForRemoveFromWarehouse).build());
            productService.changeProductsStatus(ProductsStatusChangeDTO.builder().productsId(productIds).productStatus(ProductStatus.IN_STORE.getValue()).build());
            store.getProductIds().addAll(productIds);
        });
        return true;
    }
}
