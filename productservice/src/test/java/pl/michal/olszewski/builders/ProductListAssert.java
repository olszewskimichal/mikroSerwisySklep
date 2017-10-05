package pl.michal.olszewski.builders;

import org.assertj.core.api.ListAssert;
import pl.michal.olszewski.dto.ProductDTO;

import java.util.List;

public class ProductListAssert extends ListAssert<ProductDTO> {
    private List<ProductDTO> actual;

    protected ProductListAssert(List<ProductDTO> productList) {
        super(productList);
        this.actual = productList;
    }

    public static ProductListAssert assertThat(List<ProductDTO> actual) {
        return new ProductListAssert(actual);
    }

    public ProductListAssert isSuccessful() {
        assertThat(actual).isNotNull();
        return this;
    }

    public ProductListAssert hasNumberOfItems(int number) {
        assertThat(actual).hasSize(number);
        return this;
    }

}
