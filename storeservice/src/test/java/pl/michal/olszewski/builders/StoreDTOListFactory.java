package pl.michal.olszewski.builders;

import pl.michal.olszewski.dto.StoreDTO;
import pl.michal.olszewski.entity.Address;
import pl.michal.olszewski.entity.Store;
import pl.michal.olszewski.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StoreDTOListFactory {

    private final StoreRepository repository;

    public StoreDTOListFactory(StoreRepository repository) {
        this.repository = repository;
    }

    public List<StoreDTO> buildNumberOfStoresDTOAndSave(int numberOfStores) {
        List<StoreDTO> storeDTOS = new ArrayList<>();
        IntStream.range(0, numberOfStores).forEachOrdered(number -> {
            Store store = Store.builder().name(String.format("store_%s", number)).address(Address.builder().city("city").country("pl").street("street").zipCode("zip").build()).build();
            repository.saveAndFlush(store);
            storeDTOS.add(new StoreDTO(store.getName(), store.getAddress()));
        });
        return storeDTOS;
    }

    public List<Store> buildNumberOfStoresAndSave(int numberOfStores) {
        List<Store> stores = new ArrayList<>();
        IntStream.range(0, numberOfStores).forEachOrdered(number -> {
            Store store = Store.builder().name(String.format("store_%s", number)).address(Address.builder().city("city").country("pl").street("street").zipCode("zip").build()).build();
            repository.saveAndFlush(store);
            stores.add(store);
        });
        return stores;
    }

    public static List<StoreDTO> getNotPersistedStores(int numberOfStores) {
        List<StoreDTO> storeDTOS = new ArrayList<>();
        IntStream.range(0, numberOfStores).forEachOrdered(number -> {
            Store store = Store.builder().name(String.format("store_%s", number)).address(Address.builder().city("city").country("pl").street("street").zipCode("zip").build()).build();
            storeDTOS.add(new StoreDTO(store.getName(), store.getAddress()));
        });
        return storeDTOS;
    }

}
