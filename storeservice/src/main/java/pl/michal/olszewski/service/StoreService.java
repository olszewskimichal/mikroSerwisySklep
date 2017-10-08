package pl.michal.olszewski.service;

import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.dto.StoreDTO;
import pl.michal.olszewski.entity.Store;
import pl.michal.olszewski.repository.StoreRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StoreService {
    private static final int PAGE_LIMIT = 20;
    private final StoreRepository storeRepository;
    

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
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
}
