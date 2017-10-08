package pl.michal.olszewski.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.dto.ProductsStatusChangeDTO;
import pl.michal.olszewski.product.ProductDTO;
import pl.michal.olszewski.product.ProductService;
import pl.michal.olszewski.product.ProductStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("test")
public class MockProductService implements ProductService {
    @Override
    public List<ProductDTO> getAvailableProductsForStoreFromApi(String productsId) {
        return Arrays.stream(productsId.split(",")).map(v -> ProductDTO.builder().productId(Long.parseLong(v)).productStatus(Long.parseLong(v)).build()).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> changeProductsStatus(ProductsStatusChangeDTO statusChangeDTO) {
        if (statusChangeDTO.getProductsId().size() > 2) throw new NullPointerException();
        return new ArrayList<>();
    }

    @Override
    public List<ProductDTO> filterProductsForStatus(List<ProductDTO> productDTOList, List<ProductStatus> statuses) {
        return productDTOList.stream().filter(productDTO -> statuses.contains(ProductStatus.fromValue(productDTO.getProductStatus()))).collect(Collectors.toList());
    }
}
