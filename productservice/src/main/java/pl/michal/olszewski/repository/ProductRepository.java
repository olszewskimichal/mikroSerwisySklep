package pl.michal.olszewski.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.entity.Product;
import pl.michal.olszewski.entity.ProductDefinition;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p join fetch p.productDefinition where p.id=?1")
    Product findByIdFetchProductDetails(Long id);

    @Query(value = "SELECT v FROM Product v LEFT JOIN FETCH v.productDefinition",
            countQuery = "select count(v) from Product v")
    Page<Product> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Product p set p.productDefinition = ?1, p.productStatus = ?2 where p.id = ?3")
    int updateProduct(ProductDefinition definition, Long status, Long id);
}
