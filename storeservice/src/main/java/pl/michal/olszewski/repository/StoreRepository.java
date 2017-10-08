package pl.michal.olszewski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.entity.Store;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Transactional
    @Modifying
    @Query("update Store p set p.name = ?1, p.address.street = ?2, p.address.city = ?3, p.address.country = ?4, p.address.zipCode = ?5 where p.id = ?6")
    int updateStore(String name, String street, String city, String country, String zipCode, Long id);

    @Query("select w from Store w left join fetch w.productIds where w.name=?1")
    Optional<Store> findByName(String storeName);

    @Query("select w from Store w left join fetch w.productIds where w.id=?1")
    Optional<Store> findById(Long storeId);
}
