package pl.michal.olszewski.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.enums.ProductStatus;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductDefinition productDefinition;

    private Long productStatus;

    public Product(ProductDefinition productDefinition, ProductStatus productStatus) {
        this.productDefinition = productDefinition;
        this.productStatus = productStatus.getValue();
    }
}
