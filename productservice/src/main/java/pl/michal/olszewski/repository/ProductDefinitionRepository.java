package pl.michal.olszewski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.entity.ProductDefinition;

import java.math.BigDecimal;

@Repository
public interface ProductDefinitionRepository extends JpaRepository<ProductDefinition, Long> {

    @Transactional
    @Modifying
    @Query("update ProductDefinition p set p.name = ?1, p.description = ?2, p.imageUrl = ?3, p.prodType = ?4, p.price = ?5 where p.id = ?6")
    int updateProductDefinition(String name, String description, String imageUrl, Long prodType, BigDecimal price, Long productDefinitionId);

    ProductDefinition findByName(String name);
}
