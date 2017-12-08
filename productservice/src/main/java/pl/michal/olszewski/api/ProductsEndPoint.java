package pl.michal.olszewski.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.michal.olszewski.dto.ProductDTO;
import pl.michal.olszewski.dto.ProductsStatusChangeDTO;
import pl.michal.olszewski.service.ProductService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/products")
public class ProductsEndPoint {

    private final ProductService service;

    public ProductsEndPoint(ProductService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long productId) {
        return Optional.ofNullable(service.getProduct(productId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDTO> getProducts(@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "page", required = false) Integer page) {
        return service.getProducts(limit, page);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDTO productDTO) {
        service.updateProduct(productId, productDTO);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProduct(@RequestBody ProductDTO product) {
        service.createProduct(product);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Long productId) {
        service.deleteProduct(productId);
    }

    @GetMapping(value = "/byIds/{productIds}")
    public ResponseEntity getAvailableProductsForProductIds(@PathVariable("productIds") String productsIds) {
        return Optional.ofNullable(service.getAvailableProducts(productsIds))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/changeProductsStatus")
    public ResponseEntity changeProductsStatus(@RequestBody ProductsStatusChangeDTO statusChangeDTO) {
        return Optional.ofNullable(service.changeProductsStatus(statusChangeDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
