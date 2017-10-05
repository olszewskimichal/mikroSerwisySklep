package pl.michal.olszewski.builders;

import pl.michal.olszewski.dto.ProductDTO;
import pl.michal.olszewski.entity.Product;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.enums.ProductStatus;
import pl.michal.olszewski.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProductDTOListFactory {

    private final ProductRepository repository;

    public ProductDTOListFactory(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductDTO> buildNumberOfProductsAndSave(int numberOfProducts, ProductDefinition productDefinition, ProductStatus productStatus) {
        List<ProductDTO> productList = new ArrayList<>();
        IntStream.range(0, numberOfProducts).forEachOrdered(number -> {
            Product product = new Product(productDefinition, productStatus);
            repository.save(product);
            productList.add(new ProductDTO(product));
        });
        return productList;
    }

    public static List<ProductDTO> getNotPersistedProducts(int numberOfProdcuts, ProductDefinition productDefinition, ProductStatus productStatus) {
        List<ProductDTO> productList = new ArrayList<>();
        IntStream.range(0, numberOfProdcuts).forEach(num -> {
            Product product = new Product(productDefinition, productStatus);
            ProductDTO productDTO = new ProductDTO(product);
            productList.add(productDTO);
        });
        return productList;
    }

}
