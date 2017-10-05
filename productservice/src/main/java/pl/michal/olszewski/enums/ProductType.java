package pl.michal.olszewski.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductType {
    TSHIRT(1L, "T-Shirt"),
    SHOES(2L, "Buty"),
    PANTS(3L, "Spodnie");
    private final Long value;
    private final String desc;

    ProductType fromValue(Long value) {
        for (ProductType productType : ProductType.values()) {
            if (productType.getValue().equals(value)) return productType;
        }
        return null;
    }
}
