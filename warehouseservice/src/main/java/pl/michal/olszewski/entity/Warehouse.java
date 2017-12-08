package pl.michal.olszewski.entity;

import lombok.*;
import pl.michal.olszewski.dto.WarehouseDTO;

import javax.persistence.*;
import java.util.HashSet;
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

    @ElementCollection
    private Set<Long> productIds;

    public Warehouse(@NonNull WarehouseDTO warehouseDTO) {
        this.name = warehouseDTO.getName();
        this.address = new Address(warehouseDTO);
    }

    public Set<Long> getProductIds() {
        if (productIds == null) productIds = new HashSet<>();
        return productIds;
    }
}
