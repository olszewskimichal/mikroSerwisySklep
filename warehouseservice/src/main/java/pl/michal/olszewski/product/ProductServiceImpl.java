package pl.michal.olszewski.product;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.michal.olszewski.dto.ProductsStatusChangeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("!test")
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Pobiera przez API produkty po idkach i pozniej je filtruje pod kątem tych produktów które mozna przenieść do magazynu
     *
     * @param productsId
     * @return
     */
    public List<ProductDTO> getAvailibleProductsForWarehouseFromApi(String productsId) {
        List<ProductDTO> productDTOList = Arrays.asList(restTemplate.getForObject("http://localhost:8080/api/v1/products/byIds/" + productsId, ProductDTO[].class));
        return productDTOList.stream().filter(v -> ProductStatus.availableForMoveToWarehouseStatuses.contains(ProductStatus.fromValue(v.getProductStatus()))).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> changeProductsStatus(ProductsStatusChangeDTO statusChangeDTO) {
        return Arrays.asList(restTemplate.postForEntity("http://localhost:8080/api/v1/products/changeProductsStatus", statusChangeDTO, ProductDTO[].class).getBody());
    }
}
