package pl.michal.olszewski.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.JpaTestBase;
import pl.michal.olszewski.dto.StoreDTO;
import pl.michal.olszewski.entity.Store;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StoreRepositoryTest extends JpaTestBase {
    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void shouldFindStoreById() {
        //given
        Long id = entityManager.persistAndFlush(new Store(StoreDTO.builder().city("city").country("pl").name("store").street("street").build())).getId();
        //when
        Optional<Store> store = storeRepository.findById(id);
        //then
        assertThat(store.isPresent()).isTrue();
        assertThat(store.get().getName()).isEqualTo("store");
    }

    @Test
    public void shouldFindStoreByName() {
        //given
        entityManager.persistAndFlush(new Store(StoreDTO.builder().city("city2").country("pl").name("store2").street("street2").build())).getId();
        //when
        Optional<Store> store = storeRepository.findByName("store2");
        //then
        assertThat(store.isPresent()).isTrue();
        assertThat(store.get().getName()).isEqualTo("store2");
    }

    @Test
    public void shouldUpdateStore() {
        //given
        Store store = entityManager.persistAndFlush(new Store(StoreDTO.builder().city("city").country("pl").name("store").street("street").build()));
        //when
        storeRepository.updateStore("store3", "str", "city", "DE", "zip", store.getId());
        entityManager.clear();
        //then
        Store updatedStore = storeRepository.findOne(store.getId());
        assertThat(updatedStore).isNotNull();
        assertThat(updatedStore.getName()).isEqualTo("store3");
        assertThat(updatedStore.getAddress().getStreet()).isEqualTo("str");
        assertThat(updatedStore.getAddress().getCountry()).isEqualTo("DE");
        assertThat(updatedStore.getAddress().getZipCode()).isEqualTo("zip");
    }
}