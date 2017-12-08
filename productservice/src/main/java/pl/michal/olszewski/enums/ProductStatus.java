package pl.michal.olszewski.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {
    NEW(0L, "Nowy"),
    IN_WAREHOUSE(1L, "Dostępny w magazynie"),
    IN_COMPLAINT(2L, "W reklamacji"),
    IN_STORE(3L, "Dostępny w sklepie"),
    DAMAGED(4L, "Zepsuty"),
    LIQUIDATION(5L, "Zlikwidowany"),
    SOLD(6L, "Sprzedany");

    private final Long value;
    private final String description;

    ProductStatus(Long value, String description) {
        this.value = value;
        this.description = description;
    }

    public static ProductStatus fromValue(Long value) {
        for (ProductStatus productStatus : ProductStatus.values()) {
            if (productStatus.getValue().equals(value)) return productStatus;
        }
        return null;
    }
}
