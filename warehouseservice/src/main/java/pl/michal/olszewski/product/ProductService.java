package pl.michal.olszewski.product;

import pl.michal.olszewski.dto.ProductsStatusChangeDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAvailableProductsForWarehouseFromApi(String productsId);

    List<ProductDTO> changeProductsStatus(ProductsStatusChangeDTO statusChangeDTO);

    List<ProductDTO> filterProductsForStatus(List<ProductDTO> productDTOList, List<ProductStatus> statuses);
}
