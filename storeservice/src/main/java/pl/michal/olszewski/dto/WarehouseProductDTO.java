package pl.michal.olszewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Klasa transportowa zawierajaca id magazynu i liste produktów z ich iloscia
 */
@Data
@Builder
@AllArgsConstructor
public class WarehouseProductDTO implements Serializable {
    private final Long warehouseId;

    private final List<Long> productsIds;
}
