package pl.michal.olszewski.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.michal.olszewski.entity.ProductDefinition;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDefinitionDTO implements Serializable {
    @XmlTransient
    @JsonIgnore
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Long prodType;
    private BigDecimal price;

    public ProductDefinitionDTO(@NonNull ProductDefinition productDefinition) {
        this.id = productDefinition.getId();
        this.name = productDefinition.getName();
        this.description = productDefinition.getDescription();
        this.imageUrl = productDefinition.getImageUrl();
        this.prodType = productDefinition.getProdType();
        this.price = productDefinition.getPrice();
    }
}
