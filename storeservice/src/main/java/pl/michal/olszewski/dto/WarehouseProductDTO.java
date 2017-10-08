package pl.michal.olszewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Klasa transportowa zawierajaca id magazynu i liste produktów z ich iloscia
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseProductDTO implements Serializable {
    private Long warehouseId;

    private List<Long> productsIds;
}
