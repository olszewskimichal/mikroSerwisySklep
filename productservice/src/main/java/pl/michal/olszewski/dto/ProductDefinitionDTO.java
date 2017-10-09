package pl.michal.olszewski.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import pl.michal.olszewski.entity.ProductDefinition;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ProductDefinitionDTO implements Serializable {
    @XmlTransient
    @JsonIgnore
    private final Long id;
    private final String name;
    private final String description;
    private final String imageUrl;
    private final Long prodType;
    private final BigDecimal price;

    public ProductDefinitionDTO(@NonNull ProductDefinition productDefinition) {
        this.id = productDefinition.getId();
        this.name = productDefinition.getName();
        this.description = productDefinition.getDescription();
        this.imageUrl = productDefinition.getImageUrl();
        this.prodType = productDefinition.getProdType();
        this.price = productDefinition.getPrice();
    }
}
