package pl.michal.olszewski.product;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ProductStatus {
    NEW(0L, "nowy"),
    IN_WAREHOUSE(1L, "Dostępny w magazynie"),
    IN_COMPLAINT(2L, "W reklamacji"),
    IN_STORE(3L, "Dostępny w sklepie"),
    DAMAGED(4L, "Zepsuty"),
    LIQUIDATION(5L, "Zlikwidowany"),
    SOLD(6L, "Sprzedany");

    public static List<ProductStatus> availableForMoveToStoreStatuses = Arrays.asList(NEW, IN_WAREHOUSE);
    public static List<ProductStatus> availableForMoveToWarehouseStatuses = Arrays.asList(NEW, IN_STORE);

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
