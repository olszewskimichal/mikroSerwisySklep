package pl.michal.olszewski.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.IntegrationTest;
import pl.michal.olszewski.dto.WarehouseProductDTO;
import pl.michal.olszewski.entity.Address;
import pl.michal.olszewski.entity.Warehouse;
import pl.michal.olszewski.product.ProductDTO;
import pl.michal.olszewski.repository.WarehouseRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WarehouseService warehouseService;

    @Test
    public void shouldMoveProductsToWarehouse() {
        //given
        warehouseRepository.deleteAll();
        Warehouse warehouse = warehouseRepository.save(Warehouse.builder().name("test").address(Address.builder().city("city").country("PL").street("str").zipCode("zip").build()).build());
        WarehouseProductDTO warehouseProductDTO = WarehouseProductDTO.builder().warehouseId(warehouse.getId()).productsIds(Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 6L)).build();
        //when
        warehouseService.moveProductsToWarehouse(warehouseProductDTO);
        //then
        Warehouse warehouseRepositoryOne = warehouseRepository.findById(warehouse.getId()).get();
        assertThat(warehouseRepositoryOne.getProductIds().size()).isEqualTo(2);
    }

    @Test
    public void shouldNotMoveProductsToWarehouseWillByProblemWithService() {
        //given
        warehouseRepository.deleteAll();
        Warehouse warehouse = warehouseRepository.save(Warehouse.builder().name("test").address(Address.builder().city("city").country("PL").street("str").zipCode("zip").build()).build());
        WarehouseProductDTO warehouseProductDTO = WarehouseProductDTO.builder().warehouseId(warehouse.getId()).productsIds(Arrays.asList(0L, 0L, 2L, 3L)).build();
        //when
        try {
            warehouseService.moveProductsToWarehouse(warehouseProductDTO);
        } catch (NullPointerException e) {

        }
        //then
        Warehouse warehouseRepositoryOne = warehouseRepository.findById(warehouse.getId()).get();
        assertThat(warehouseRepositoryOne.getProductIds().size()).isEqualTo(0);
    }

    @Test
    public void shouldRemoveProductsFromWarehouse() {
        //given
        warehouseRepository.deleteAll();
        Warehouse warehouse = Warehouse.builder().name("test").productIds(new HashSet<>(Arrays.asList(1L, 2L, 3L))).address(Address.builder().city("city").country("PL").street("str").zipCode("zip").build()).build();
        warehouseRepository.save(warehouse);
        WarehouseProductDTO warehouseProductDTO = WarehouseProductDTO.builder().warehouseId(warehouse.getId()).productsIds(Arrays.asList(1L)).build();
        //when
        warehouseService.removeProductsFromWarehouse(warehouseProductDTO);
        //then
        Warehouse warehouseRepositoryOne = warehouseRepository.findById(warehouse.getId()).get();
        assertThat(warehouseRepositoryOne.getProductIds().size()).isEqualTo(2);
    }

}