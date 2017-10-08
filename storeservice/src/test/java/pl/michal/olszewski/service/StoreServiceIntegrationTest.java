package pl.michal.olszewski.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.IntegrationTest;
import pl.michal.olszewski.dto.StoreProductDTO;
import pl.michal.olszewski.entity.Address;
import pl.michal.olszewski.entity.Store;
import pl.michal.olszewski.repository.StoreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreServiceIntegrationTest extends IntegrationTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreService storeService;

    @Test
    public void shouldMoveProductsToStore() {
        //given
        storeRepository.deleteAll();
        Store store = storeRepository.save(Store.builder().name("test").address(Address.builder().city("city").country("PL").street("str").zipCode("zip").build()).build());
        StoreProductDTO storeProductDTO = StoreProductDTO.builder().storeId(store.getId()).productsIds(Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 6L)).build();
        //when
        storeService.moveProductsToStore(storeProductDTO);
        //then
        Store storeRepositoryOne = storeRepository.findById(store.getId()).get();
        assertThat(storeRepositoryOne.getProductIds().size()).isEqualTo(2);
    }

    @Test
    public void shouldNotMoveProductsToStoreWillByProblemWithService() {
        //given
        storeRepository.deleteAll();
        Store store = storeRepository.save(Store.builder().name("test").address(Address.builder().city("city").country("PL").street("str").zipCode("zip").build()).build());
        StoreProductDTO storeProductDTO = StoreProductDTO.builder().storeId(store.getId()).productsIds(Arrays.asList(0L, 0L, 2L, 1L)).build();
        //when
        try {
            storeService.moveProductsToStore(storeProductDTO);
        } catch (NullPointerException e) {

        }
        //then
        Store storeRepositoryOne = storeRepository.findById(store.getId()).get();
        assertThat(storeRepositoryOne.getProductIds().size()).isEqualTo(0);
    }

    @Test
    public void shouldRemoveProductsFromStore() {
        //given
        storeRepository.deleteAll();
        Store store = Store.builder().name("test").productIds(new HashSet<>(Arrays.asList(1L, 2L, 3L))).address(Address.builder().city("city").country("PL").street("str").zipCode("zip").build()).build();
        storeRepository.save(store);
        StoreProductDTO storeProductDTO = StoreProductDTO.builder().storeId(store.getId()).productsIds(Collections.singletonList(3L)).build();
        //when
        storeService.removeProductsFromStore(storeProductDTO);
        //then
        Store storeRepositoryOne = storeRepository.findById(store.getId()).get();
        assertThat(storeRepositoryOne.getProductIds().size()).isEqualTo(2);
    }

}