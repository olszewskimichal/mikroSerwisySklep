package pl.michal.olszewski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michal.olszewski.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
