package pl.michal.olszewski.product;

import pl.michal.olszewski.dto.ProductsStatusChangeDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAvailibleProductsForWarehouseFromApi(String productsId);

    List<ProductDTO> changeProductsStatus(ProductsStatusChangeDTO statusChangeDTO);
}
