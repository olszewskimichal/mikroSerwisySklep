package pl.michal.olszewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import pl.michal.olszewski.entity.Product;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class ProductDTO implements Serializable {
    private final Long productId;
    private final Long productDefinition;
    private final Long productStatus;

    public ProductDTO(@NonNull Product product) {
        this.productId = product.getId();
        this.productDefinition = product.getProductDefinition().getId();
        this.productStatus = product.getProductStatus();
    }
}
