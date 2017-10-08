package pl.michal.olszewski.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.IntegrationTest;
import pl.michal.olszewski.builders.WarehouseDTOListFactory;
import pl.michal.olszewski.builders.WarehouseListAssert;
import pl.michal.olszewski.dto.WarehouseDTO;
import pl.michal.olszewski.dto.WarehouseProductDTO;
import pl.michal.olszewski.entity.Warehouse;
import pl.michal.olszewski.repository.WarehouseRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseApiTest extends IntegrationTest {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Before
    public void setUp() {
        warehouseRepository.deleteAll();
    }

    @Test
    public void should_get_empty_list_of_warehouses() {
        givenWarehouse()
                .buildNumberOfWarehousesDTOAndSave(0);

        List<WarehouseDTO> warehouses = thenGetWarehousesFromApi();

        assertThat(warehouses).isEmpty();
    }

    @Test
    public void should_get_all_warehouses() {
        givenWarehouse()
                .buildNumberOfWarehousesDTOAndSave(3);

        List<WarehouseDTO> products = thenGetWarehousesFromApi();

        assertThat(products).hasSize(3);
    }

    @Test
    public void should_get_limit_three_warehouses() {
        givenWarehouse()
                .buildNumberOfWarehousesDTOAndSave(6);

        List<WarehouseDTO> productDefinitionDTOS = thenGetNumberWarehousesFromApi(3);

        WarehouseListAssert.assertThat(productDefinitionDTOS)
                .isSuccessful()
                .hasNumberOfItems(3);
    }

    @Test
    public void should_get_one_warehouse_byName() {
        List<WarehouseDTO> warehouseDTOS = givenWarehouse()
                .buildNumberOfWarehousesDTOAndSave(1);

        WarehouseDTO product = thenGetOneWarehouseFromApiByName(warehouseDTOS.get(0).getName());

        assertThat(warehouseDTOS.get(0)).isEqualToComparingOnlyGivenFields(product, "name", "street", "city", "country", "zipCode");
    }

    @Test
    public void should_get_one_warehouse() {
        List<Warehouse> warehouses = givenWarehouse()
                .buildNumberOfWarehousesAndSave(1);

        WarehouseDTO warehouseDTO = thenGetOneWarehouseFromApiById(warehouses.get(0).getId());
        assertThat(new WarehouseDTO(warehouses.get(0).getName(), warehouses.get(0).getAddress())).isEqualToComparingOnlyGivenFields(warehouseDTO, "name", "street", "city", "country", "zipCode");
    }

    @Test
    public void should_create_a_warehouse() {
        //given
        warehouseRepository.deleteAll();
        //when
        thenCreateWarehouseByApi("test");

        //then
        assertThat(warehouseRepository.findAll().size()).isEqualTo(1);
        assertThat(warehouseRepository.findAll().get(0)).isNotNull();
    }

    @Test
    public void should_update_existing_warehouse() {
        //given
        Warehouse warehouse = givenWarehouse()
                .buildNumberOfWarehousesAndSave(1).get(0);
        WarehouseDTO warehouseDTO = new WarehouseDTO(warehouse.getName(), warehouse.getAddress());
        warehouseDTO.setName("nazwa nowa");

        //when
        thenUpdateWarehouseByApi(warehouse.getId(), warehouseDTO);

        //then
        assertThat(warehouseRepository.findByName(warehouseDTO.getName()).get())
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "nazwa nowa");
    }

    @Test
    public void should_delete_existing_warehouse() {
        //given
        Warehouse warehouse = givenWarehouse()
                .buildNumberOfWarehousesAndSave(1).get(0);
        //when
        thenDeleteOneWarehouseFromApi(warehouse.getId());

        //then
        assertThat(warehouseRepository.findOne(warehouse.getId())).isNull();
    }

    @Test
    public void should_move_products_to_warehouse() {
        //given
        Warehouse warehouse = givenWarehouse()
                .buildNumberOfWarehousesAndSave(1).get(0);
        //when
        thenMoveProductsToWarehouseByApi(WarehouseProductDTO.builder().warehouseId(warehouse.getId()).productsIds(Arrays.asList(0L, 1L, 2L, 3L)).build());

        //then
        Warehouse warehouseUpdated = warehouseRepository.findById(warehouse.getId()).get();
        assertThat(warehouseUpdated).isNotNull();
        assertThat(warehouseUpdated.getProductIds()).isNotEmpty();
        assertThat(warehouseUpdated.getProductIds().size()).isEqualTo(2);
    }

    @Test
    public void should_remove_products_from_warehouse() {
        //given
        Warehouse warehouse = givenWarehouse()
                .buildNumberOfWarehousesAndSave(1).get(0);
        warehouse.getProductIds().addAll(Collections.singletonList(1L));
        warehouseRepository.save(warehouse);
        //when
        thenRemoveProductsFromWarehouseByApi(WarehouseProductDTO.builder().warehouseId(warehouse.getId()).productsIds(Collections.singletonList(1L)).build());

        //then
        Warehouse warehouseUpdated = warehouseRepository.findById(warehouse.getId()).get();
        assertThat(warehouseUpdated).isNotNull();
        assertThat(warehouseUpdated.getProductIds()).isEmpty();
        assertThat(warehouseUpdated.getProductIds().size()).isEqualTo(0);
    }


    private WarehouseDTOListFactory givenWarehouse() {
        return new WarehouseDTOListFactory(warehouseRepository);
    }

    private List<WarehouseDTO> thenGetWarehousesFromApi() {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/warehouses", port), WarehouseDTO[].class).getBody());
    }

    private WarehouseDTO thenGetOneWarehouseFromApiById(Long id) {
        return template.getForEntity(String.format("http://localhost:%s/api/v1/warehouses/%s", port, id), WarehouseDTO.class).getBody();
    }

    private WarehouseDTO thenGetOneWarehouseFromApiByName(String name) {
        return template.getForEntity(String.format("http://localhost:%s/api/v1/warehouses/name/%s", port, name), WarehouseDTO.class).getBody();
    }

    private List<WarehouseDTO> thenGetNumberWarehousesFromApi(int number) {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/warehouses?limit=%s", port, number), WarehouseDTO[].class).getBody());
    }

    private void thenCreateWarehouseByApi(String name) {
        template.postForEntity(String.format("http://localhost:%s/api/v1/warehouses", port), WarehouseDTO.builder().name(name).build(), WarehouseDTO.class);
    }

    private void thenUpdateWarehouseByApi(Long productId, WarehouseDTO warehouseDTO) {
        template.put(String.format("http://localhost:%s/api/v1/warehouses/%s", port, productId), warehouseDTO);
    }

    private void thenDeleteOneWarehouseFromApi(Long productId) {
        template.delete(String.format("http://localhost:%s/api/v1/warehouses/%s", port, productId));
    }

    private ResponseEntity<String> thenMoveProductsToWarehouseByApi(WarehouseProductDTO warehouseProductDTO) {
        return template.postForEntity(String.format("http://localhost:%s/api/v1/warehouses/moveProductsToWarehouse", port), warehouseProductDTO, String.class);
    }

    private ResponseEntity<String> thenRemoveProductsFromWarehouseByApi(WarehouseProductDTO warehouseProductDTO) {
        return template.postForEntity(String.format("http://localhost:%s/api/v1/warehouses/removeProductsFromWarehouse", port), warehouseProductDTO, String.class);
    }
}
