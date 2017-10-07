package pl.michal.olszewski.service;

import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.dto.ProductsStatusChangeDTO;
import pl.michal.olszewski.dto.WarehouseDTO;
import pl.michal.olszewski.dto.WarehouseProductDTO;
import pl.michal.olszewski.entity.Warehouse;
import pl.michal.olszewski.product.ProductDTO;
import pl.michal.olszewski.product.ProductService;
import pl.michal.olszewski.product.ProductStatus;
import pl.michal.olszewski.repository.WarehouseRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private static final int PAGE_LIMIT = 20;
    private final WarehouseRepository warehouseRepository;
    private final ProductService productService;

    public WarehouseService(WarehouseRepository warehouseRepository, ProductService productService) {
        this.warehouseRepository = warehouseRepository;
        this.productService = productService;
    }

    public WarehouseDTO getWarehouseById(final Long warehouseId) {
        return warehouseRepository.findById(warehouseId).map(v -> new WarehouseDTO(v.getName(), v.getAddress()))
                .orElse(null);
    }

    public WarehouseDTO getWarehouseByName(@NonNull final String warehouseName) {
        return warehouseRepository.findByName(warehouseName).map(v -> new WarehouseDTO(v.getName(), v.getAddress()))
                .orElse(null);
    }

    public List<WarehouseDTO> getWarehouses(final Integer limit, final Integer page) {
        PageRequest pageRequest = new PageRequest(getPage(page), getLimit(limit));
        return warehouseRepository.findAll(pageRequest).getContent().stream().map(v -> new WarehouseDTO(v.getName(), v.getAddress())).collect(Collectors.toList());
    }

    public void createWarehouse(final WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse(warehouseDTO);
        warehouseRepository.save(warehouse);
    }

    public void updateWarehouse(final WarehouseDTO warehouseDTO, final Long id) {
        warehouseRepository.updateWarehouse(warehouseDTO.getName(), warehouseDTO.getStreet(), warehouseDTO.getCity(), warehouseDTO.getCountry(), warehouseDTO.getZipCode(), id);
    }

    @Transactional
    public Boolean moveProductsToWarehouse(WarehouseProductDTO warehouseProductDTO) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseProductDTO.getWarehouseId());
        warehouseOptional.ifPresent(warehouse -> {
            String productsId = warehouseProductDTO.getProductsIds().stream().map(Object::toString).collect(Collectors.joining(","));
            List<Long> productIds = productService.getAvailibleProductsForWarehouseFromApi(productsId).stream().map(ProductDTO::getProductId).collect(Collectors.toList());
            productService.changeProductsStatus(ProductsStatusChangeDTO.builder().productsId(productIds).productStatus(ProductStatus.IN_WAREHOUSE.getValue()).build());
            warehouse.getProductIds().addAll(productIds);
        });
        return true;
    }

    public void deleteWarehouse(final Long id) {
        warehouseRepository.delete(id);
    }

    private int getLimit(final Integer size) {
        return (Objects.isNull(size) ? PAGE_LIMIT : size);
    }

    private int getPage(final Integer page) {
        return (Objects.isNull(page) ? 0 : page);
    }
}
