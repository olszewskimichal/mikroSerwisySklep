package pl.michal.olszewski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.entity.ProductAmount;

import java.util.Optional;

@Repository
public interface ProductAmountRepository extends JpaRepository<ProductAmount, Long> {

    Optional<ProductAmount> findByProductId(Long productId);

    @Transactional
    @Modifying
    @Query("update ProductAmount p set p.amount = ?1 where p.id = ?2")
    int updateAmountOfProduct(Long productId, Long id);
}
