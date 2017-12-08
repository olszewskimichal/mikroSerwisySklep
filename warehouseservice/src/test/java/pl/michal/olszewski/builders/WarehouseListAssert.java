package pl.michal.olszewski.builders;

import org.assertj.core.api.ListAssert;
import pl.michal.olszewski.dto.WarehouseDTO;

import java.util.List;

public class WarehouseListAssert extends ListAssert<WarehouseDTO> {
    private List<WarehouseDTO> actual;

    protected WarehouseListAssert(List<WarehouseDTO> productList) {
        super(productList);
        this.actual = productList;
    }

    public static WarehouseListAssert assertThat(List<WarehouseDTO> actual) {
        return new WarehouseListAssert(actual);
    }

    public WarehouseListAssert isSuccessful() {
        assertThat(actual).isNotNull();
        return this;
    }

    public WarehouseListAssert hasNumberOfItems(int number) {
        assertThat(actual).hasSize(number);
        return this;
    }

}
