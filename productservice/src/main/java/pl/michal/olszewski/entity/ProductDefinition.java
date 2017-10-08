package pl.michal.olszewski.entity;

import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.michal.olszewski.dto.ProductDefinitionDTO;
import pl.michal.olszewski.enums.ProductType;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@Audited
public class ProductDefinition {
    @GeneratedValue
    @Id
    private Long id;
    @Column(unique = true)
    @NonNull
    private String name;

    private String description;

    private String imageUrl;

    @NonNull
    private Long prodType;

    @NonNull
    private BigDecimal price;

    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    private Long lastModified;

    /**
     * @param name        nazwaProduktu
     * @param description opisProduktu
     * @param imageUrl    url do zdjecia produktu
     * @param prodType    typ produktu @{@link ProductType}
     * @param price       cenaProduktu
     */
    public ProductDefinition(String name, String description, String imageUrl, ProductType prodType, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.prodType = prodType.getValue();
        this.price = price;
    }

    public ProductDefinition(ProductDefinitionDTO productDefinitionDTO) {
        this.name = productDefinitionDTO.getName();
        this.description = productDefinitionDTO.getDescription();
        this.imageUrl = productDefinitionDTO.getImageUrl();
        this.prodType = productDefinitionDTO.getProdType();
        this.price = productDefinitionDTO.getPrice();
    }
}
