package pl.michal.olszewski.builders;

import org.assertj.core.api.ListAssert;
import pl.michal.olszewski.dto.StoreDTO;

import java.util.List;

public class StoreListAssert extends ListAssert<StoreDTO> {
    private List<StoreDTO> actual;

    protected StoreListAssert(List<StoreDTO> productList) {
        super(productList);
        this.actual = productList;
    }

    public static StoreListAssert assertThat(List<StoreDTO> actual) {
        return new StoreListAssert(actual);
    }

    public StoreListAssert isSuccessful() {
        assertThat(actual).isNotNull();
        return this;
    }

    public StoreListAssert hasNumberOfItems(int number) {
        assertThat(actual).hasSize(number);
        return this;
    }

}
