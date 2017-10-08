package pl.michal.olszewski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michal.olszewski.entity.Store;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    void updateStore(String name, String street, String city, String country, String zipCode, Long id);

    Optional<Store> findByName(String storeName);

    Optional<Store> findById(Long storeId);
}
