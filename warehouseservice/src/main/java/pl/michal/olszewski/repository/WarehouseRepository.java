package pl.michal.olszewski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.entity.Address;
import pl.michal.olszewski.entity.Warehouse;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByName(String name);

    Optional<Warehouse> findById(Long id);

    @Transactional
    @Modifying
    @Query("update Warehouse p set p.name = ?1, p.address.street = ?2, p.address.city = ?3, p.address.country = ?4, p.address.zipCode = ?5 where p.id = ?6")
    int updateWarehouse(String name, String street, String city, String country, String zipCode, Long id);
}
