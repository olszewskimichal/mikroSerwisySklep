package pl.michal.olszewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Klasa transportowa zawierajaca id sklepu i liste produktów z ich iloscia
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductDTO implements Serializable {
    private Long storeId;

    private List<Long> productsIds;
}
