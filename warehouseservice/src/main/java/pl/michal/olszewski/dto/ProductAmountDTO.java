package pl.michal.olszewski.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.entity.ProductAmount;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ProductAmountDTO implements Serializable {
    private Long warehouseId;

    private Long productId;

    private Long amount;

    public ProductAmountDTO(Long warehouseId, Long productId, Long amount) {
        this.warehouseId = warehouseId;
        this.productId = productId;
        this.amount = amount;
    }

    public ProductAmountDTO(Long warehouseId, ProductAmount productAmount) {
        this.warehouseId = warehouseId;
        this.productId = productAmount.getProductId();
        this.amount = productAmount.getAmount();
    }
}
