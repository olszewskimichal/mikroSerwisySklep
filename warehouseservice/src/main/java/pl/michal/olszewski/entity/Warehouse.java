package pl.michal.olszewski.entity;

import lombok.*;
import pl.michal.olszewski.dto.WarehouseDTO;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Embedded
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "warehouse", orphanRemoval = true)
    private Set<ProductAmount> productAmounts;

    public void addProductAmount(ProductAmount productAmount) {
        productAmounts.add(productAmount);
        productAmount.setWarehouse(this);
    }

    public void removeProductAmount(ProductAmount productAmount) {
        productAmounts.remove(productAmount);
        productAmount.setAmount(null);
    }

    public Warehouse(@NonNull WarehouseDTO warehouseDTO) {
        this.name = warehouseDTO.getName();
        this.address = new Address(warehouseDTO);
    }
}
