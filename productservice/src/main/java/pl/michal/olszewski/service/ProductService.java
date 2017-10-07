package pl.michal.olszewski.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.olszewski.dto.ProductDTO;
import pl.michal.olszewski.dto.ProductDefinitionDTO;
import pl.michal.olszewski.dto.ProductsStatusChangeDTO;
import pl.michal.olszewski.entity.Product;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.enums.ProductStatus;
import pl.michal.olszewski.repository.ProductDefinitionRepository;
import pl.michal.olszewski.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final int PAGE_LIMIT = 20;
    private final ProductRepository productRepository;
    private final ProductDefinitionRepository productDefinitionRepository;

    public ProductService(ProductRepository productRepository, ProductDefinitionRepository productDefinitionRepository) {
        this.productRepository = productRepository;
        this.productDefinitionRepository = productDefinitionRepository;
    }

    public ProductDTO getProduct(final Long id) {
        Product product = productRepository.findByIdFetchProductDetails(id);
        if (product == null)
            return null;
        return new ProductDTO(product);
    }

    public List<ProductDTO> getProducts(final Integer limit, final Integer page) {
        PageRequest pageRequest = new PageRequest(getPage(page), getLimit(limit));
        return productRepository.findAll(pageRequest).getContent().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    public void deleteProduct(final Long id) {
        productRepository.delete(id);
    }

    public void createProduct(final ProductDTO productDTO) {
        productRepository.save(new Product(productDefinitionRepository.findOne(productDTO.getProductDefinition()), Objects.requireNonNull(ProductStatus.fromValue(productDTO.getProductStatus()))));
    }

    public void updateProduct(final Long productId, final ProductDTO productDTO) {
        productRepository.updateProduct(productDefinitionRepository.findOne(productDTO.getProductDefinition()), productDTO.getProductStatus(), productId);
    }

    public ProductDefinitionDTO getProductDefinition(final Long id) {
        return new ProductDefinitionDTO(productDefinitionRepository.findOne(id));
    }

    public ProductDefinitionDTO getProductDefinitionByName(final String name) {
        return new ProductDefinitionDTO(productDefinitionRepository.findByName(name));
    }

    public List<ProductDefinitionDTO> getProductDefinitions(final Integer limit, final Integer page) {
        PageRequest pageRequest = new PageRequest(getPage(page), getLimit(limit));
        return productDefinitionRepository.findAll(pageRequest).getContent().stream().map(ProductDefinitionDTO::new).collect(Collectors.toList());
    }

    public void deleteProductDefinition(final Long id) {
        productDefinitionRepository.delete(id);
    }

    public void createProductDefinition(final ProductDefinitionDTO productDefinitionDTO) {
        productDefinitionRepository.save(new ProductDefinition(productDefinitionDTO));
    }

    public void updateProductDefinition(final Long productDefId, final ProductDefinitionDTO productDefinitionDTO) {
        productDefinitionRepository.updateProductDefinition(productDefinitionDTO.getName(), productDefinitionDTO.getDescription(), productDefinitionDTO.getImageUrl(), productDefinitionDTO.getProdType(), productDefinitionDTO.getPrice(), productDefId);
    }

    private int getLimit(final Integer size) {
        return (Objects.isNull(size) ? PAGE_LIMIT : size);
    }

    private int getPage(final Integer page) {
        return (Objects.isNull(page) ? 0 : page);
    }

    public List<ProductDTO> getAvailableProducts(String productsIds) {
        List<Product> products = productRepository.findByProductIds(extractIdsFromString(productsIds));
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @Modifying
    public List<ProductDTO> changeProductsStatus(ProductsStatusChangeDTO statusChangeDTO) {
        List<Product> products = productRepository.findByProductIds(statusChangeDTO.getProductsId());
        products.forEach(v -> v.setProductStatus(statusChangeDTO.getProductStatus()));
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    private List<Long> extractIdsFromString(String productsIds) {
        return Arrays.stream(productsIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }
}
