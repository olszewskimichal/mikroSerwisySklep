package pl.michal.olszewski.dto;

import lombok.*;
import pl.michal.olszewski.entity.Product;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO implements Serializable {
    private Long productId;
    private Long productDefinition;
    private Long productStatus;

    public ProductDTO(@NonNull Product product) {
        this.productId = product.getId();
        this.productDefinition = product.getProductDefinition().getId();
        this.productStatus = product.getProductStatus();
    }
}
