package pl.michal.olszewski.factory;

import org.assertj.core.api.ListAssert;
import pl.michal.olszewski.dto.UserDTO;

import java.util.List;

public class UserListAssert extends ListAssert<UserDTO> {
    private List<UserDTO> actual;

    protected UserListAssert(List<UserDTO> productList) {
        super(productList);
        this.actual = productList;
    }

    public static UserListAssert assertThat(List<UserDTO> actual) {
        return new UserListAssert(actual);
    }

    public UserListAssert isSuccessful() {
        assertThat(actual).isNotNull();
        return this;
    }

    public UserListAssert hasNumberOfItems(int number) {
        assertThat(actual).hasSize(number);
        return this;
    }

}
