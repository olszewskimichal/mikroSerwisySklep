package pl.michal.olszewski.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @Embedded
    private Address address;

    @ElementCollection
    private Set<Long> productIds;

    public Set<Long> getProductIds() {
        if (productIds == null) productIds = new HashSet<>();
        return productIds;
    }
}
