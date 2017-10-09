package pl.michal.olszewski.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.michal.olszewski.enums.ProductStatus;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductDefinition productDefinition;

    private Long productStatus;

    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    private Long lastModified;

    public Product(ProductDefinition productDefinition, ProductStatus productStatus) {
        this.productDefinition = productDefinition;
        this.productStatus = productStatus.getValue();
    }
}
