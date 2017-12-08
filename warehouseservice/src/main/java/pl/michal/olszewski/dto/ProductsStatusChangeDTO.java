package pl.michal.olszewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ProductsStatusChangeDTO implements Serializable {
    private final List<Long> productsId;

    private final Long productStatus;
}
