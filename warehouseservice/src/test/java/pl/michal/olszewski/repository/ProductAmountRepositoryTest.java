package pl.michal.olszewski.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.JpaTestBase;
import pl.michal.olszewski.entity.ProductAmount;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductAmountRepositoryTest extends JpaTestBase {

    @Autowired
    private ProductAmountRepository productAmountRepository;

    @Test
    public void shouldFindProductAmountByProductId() {
        //given
        entityManager.persistAndFlush(new ProductAmount(1L, 4L));
        //when
        Optional<ProductAmount> productAmount = productAmountRepository.findByProductId(1L);
        //then
        assertThat(productAmount.isPresent()).isTrue();
        assertThat(productAmount.get().getProductId()).isEqualTo(1L);
    }

    @Test
    public void shouldUpdateProductAmount() {
        //given
        ProductAmount productAmount = entityManager.persistAndFlush(new ProductAmount(1L, 4L));
        //when
        productAmountRepository.updateAmountOfProduct(10L, productAmount.getId());
        entityManager.clear();
        //then
        ProductAmount amount = productAmountRepository.findOne(productAmount.getId());
        assertThat(amount).isNotNull();
        assertThat(amount.getAmount()).isEqualTo(10L);
    }
}