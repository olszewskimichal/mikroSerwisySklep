package pl.michal.olszewski.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.IntegrationTest;
import pl.michal.olszewski.builders.StoreDTOListFactory;
import pl.michal.olszewski.builders.StoreListAssert;
import pl.michal.olszewski.dto.StoreDTO;
import pl.michal.olszewski.entity.Store;
import pl.michal.olszewski.repository.StoreRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreApiTest extends IntegrationTest {

    @Autowired
    private StoreRepository storeRepository;

    @Before
    public void setUp() {
        storeRepository.deleteAll();
    }

    @Test
    public void should_get_empty_list_of_stores() {
        givenStore()
                .buildNumberOfStoresDTOAndSave(0);

        List<StoreDTO> stores = thenGetStoresFromApi();

        assertThat(stores).isEmpty();
    }

    @Test
    public void should_get_all_stores() {
        givenStore()
                .buildNumberOfStoresDTOAndSave(3);

        List<StoreDTO> products = thenGetStoresFromApi();

        assertThat(products).hasSize(3);
    }

    @Test
    public void should_get_limit_three_stores() {
        givenStore()
                .buildNumberOfStoresDTOAndSave(6);

        List<StoreDTO> productDefinitionDTOS = thenGetNumberStoresFromApi(3);

        StoreListAssert.assertThat(productDefinitionDTOS)
                .isSuccessful()
                .hasNumberOfItems(3);
    }

    @Test
    public void should_get_one_store_byName() {
        List<StoreDTO> storeDTOS = givenStore()
                .buildNumberOfStoresDTOAndSave(1);

        StoreDTO product = thenGetOneStoreFromApiByName(storeDTOS.get(0).getName());

        assertThat(storeDTOS.get(0)).isEqualToComparingOnlyGivenFields(product, "name", "street", "city", "country", "zipCode");
    }

    @Test
    public void should_get_one_store() {
        List<Store> stores = givenStore()
                .buildNumberOfStoresAndSave(1);

        StoreDTO storeDTO = thenGetOneStoreFromApiById(stores.get(0).getId());
        assertThat(new StoreDTO(stores.get(0).getName(), stores.get(0).getAddress())).isEqualToComparingOnlyGivenFields(storeDTO, "name", "street", "city", "country", "zipCode");
    }

    @Test
    public void should_create_a_store() {
        //given
        storeRepository.deleteAll();
        //when
        thenCreateStoreByApi("test");

        //then
        assertThat(storeRepository.findAll().size()).isEqualTo(1);
        assertThat(storeRepository.findAll().get(0)).isNotNull();
    }

    @Test
    public void should_update_existing_store() {
        //given
        Store store = givenStore()
                .buildNumberOfStoresAndSave(1).get(0);
        StoreDTO storeDTO = new StoreDTO(store.getName(), store.getAddress());
        storeDTO.setName("nazwa nowa");

        //when
        thenUpdateStoreByApi(store.getId(), storeDTO);

        //then
        assertThat(storeRepository.findByName(storeDTO.getName()).get())
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "nazwa nowa");
    }

    @Test
    public void should_delete_existing_store() {
        //given
        Store store = givenStore()
                .buildNumberOfStoresAndSave(1).get(0);
        //when
        thenDeleteOneStoreFromApi(store.getId());

        //then
        assertThat(storeRepository.findOne(store.getId())).isNull();
    }

    private StoreDTOListFactory givenStore() {
        return new StoreDTOListFactory(storeRepository);
    }

    private List<StoreDTO> thenGetStoresFromApi() {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/stores", port), StoreDTO[].class).getBody());
    }

    private StoreDTO thenGetOneStoreFromApiById(Long id) {
        return template.getForEntity(String.format("http://localhost:%s/api/v1/stores/%s", port, id), StoreDTO.class).getBody();
    }

    private StoreDTO thenGetOneStoreFromApiByName(String name) {
        return template.getForEntity(String.format("http://localhost:%s/api/v1/stores/name/%s", port, name), StoreDTO.class).getBody();
    }

    private List<StoreDTO> thenGetNumberStoresFromApi(int number) {
        return Arrays.asList(template.getForEntity(String.format("http://localhost:%s/api/v1/stores?limit=%s", port, number), StoreDTO[].class).getBody());
    }

    private void thenCreateStoreByApi(String name) {
        template.postForEntity(String.format("http://localhost:%s/api/v1/stores", port), StoreDTO.builder().name(name).build(), StoreDTO.class);
    }

    private void thenUpdateStoreByApi(Long productId, StoreDTO storeDTO) {
        template.put(String.format("http://localhost:%s/api/v1/stores/%s", port, productId), storeDTO);
    }

    private void thenDeleteOneStoreFromApi(Long productId) {
        template.delete(String.format("http://localhost:%s/api/v1/stores/%s", port, productId));
    }
}
