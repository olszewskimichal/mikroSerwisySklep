package pl.michal.olszewski.builders;

import pl.michal.olszewski.dto.ProductDefinitionDTO;
import pl.michal.olszewski.entity.ProductDefinition;
import pl.michal.olszewski.enums.ProductType;
import pl.michal.olszewski.repository.ProductDefinitionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProductDefinitionsDTOListFactory {

    private final ProductDefinitionRepository repository;

    public ProductDefinitionsDTOListFactory(ProductDefinitionRepository repository) {
        this.repository = repository;
    }

    public List<ProductDefinitionDTO> buildNumberOfProductDefinitionsAndSave(int numberOfProducts) {
        List<ProductDefinitionDTO> productDefinitionDTOS = new ArrayList<>();
        IntStream.range(0, numberOfProducts).forEachOrdered(number -> {
            ProductDefinition productDefinition = ProductDefinition.builder().name(String.format("product_%s", number)).prodType(ProductType.TSHIRT.getValue()).price(BigDecimal.TEN.setScale(2,BigDecimal.ROUND_HALF_UP)).build();
            repository.saveAndFlush(productDefinition);
            productDefinitionDTOS.add(new ProductDefinitionDTO(productDefinition));
        });
        return productDefinitionDTOS;
    }

    public static List<ProductDefinitionDTO> getNotPersistedProductDef(int numberOfProducts) {
        List<ProductDefinitionDTO> productDefinitionDTOS = new ArrayList<>();
        IntStream.range(0, numberOfProducts).forEachOrdered(number -> {
            ProductDefinition productDefinition = ProductDefinition.builder().name(String.format("product_%s", number)).prodType(ProductType.TSHIRT.getValue()).price(BigDecimal.TEN.setScale(2,BigDecimal.ROUND_HALF_UP)).build();
            productDefinitionDTOS.add(new ProductDefinitionDTO(productDefinition));
        });
        return productDefinitionDTOS;
    }

}
