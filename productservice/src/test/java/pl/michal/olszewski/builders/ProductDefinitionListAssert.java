package pl.michal.olszewski.builders;

import org.assertj.core.api.ListAssert;
import pl.michal.olszewski.dto.ProductDefinitionDTO;

import java.util.List;

public class ProductDefinitionListAssert extends ListAssert<ProductDefinitionDTO> {
    private List<ProductDefinitionDTO> actual;

    protected ProductDefinitionListAssert(List<ProductDefinitionDTO> productList) {
        super(productList);
        this.actual = productList;
    }

    public static ProductDefinitionListAssert assertThat(List<ProductDefinitionDTO> actual) {
        return new ProductDefinitionListAssert(actual);
    }

    public ProductDefinitionListAssert isSuccessful() {
        assertThat(actual).isNotNull();
        return this;
    }

    public ProductDefinitionListAssert hasNumberOfItems(int number) {
        assertThat(actual).hasSize(number);
        return this;
    }

}
