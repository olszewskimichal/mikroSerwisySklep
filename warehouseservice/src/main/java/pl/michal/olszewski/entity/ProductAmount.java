package pl.michal.olszewski.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ProductAmount {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private Long productId;

    @NonNull
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    public ProductAmount(Long productId, Long amount) {
        this.productId = productId;
        this.amount = amount;
    }
}
