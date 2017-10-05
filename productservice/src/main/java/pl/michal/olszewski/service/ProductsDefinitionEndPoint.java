package pl.michal.olszewski.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.michal.olszewski.dto.ProductDefinitionDTO;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/productDefinitions")
public class ProductsDefinitionEndPoint {

    private final ProductService service;

    public ProductsDefinitionEndPoint(ProductService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDefinitionDTO> getProductDefinition(@PathVariable("id") Long productDefinitionId) {
        return Optional.ofNullable(service.getProductDefinition(productDefinitionId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/name/{productName}")
    public ResponseEntity<ProductDefinitionDTO> getProductDefinitionByName(@PathVariable("productName") String productName) {
        return Optional.ofNullable(service.getProductDefinitionByName(productName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDefinitionDTO> getProductDefinitions(@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "page", required = false) Integer page) {
        return service.getProductDefinitions(limit, page);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductDefinition(@PathVariable("productId") Long productId, @RequestBody ProductDefinitionDTO productDefinitionDTO) {
        service.updateProductDefinition(productId, productDefinitionDTO);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductDefinition(@RequestBody ProductDefinitionDTO product) {
        service.createProductDefinition(product);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductDefinition(@PathVariable("id") Long productId) {
        service.deleteProductDefinition(productId);
    }
}
