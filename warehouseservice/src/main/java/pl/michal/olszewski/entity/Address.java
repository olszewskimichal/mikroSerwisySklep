package pl.michal.olszewski.entity;

import lombok.*;
import pl.michal.olszewski.dto.WarehouseDTO;

import javax.persistence.Embeddable;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NonNull
    private String street;
    private String state;
    @NonNull
    private String city;
    @NonNull
    private String country;
    @NonNull
    private String zipCode;

    public Address(WarehouseDTO warehouseDTO) {
        this.street = warehouseDTO.getStreet();
        this.state = warehouseDTO.getState();
        this.country = warehouseDTO.getCountry();
        this.city = warehouseDTO.getCity();
        this.zipCode = warehouseDTO.getZipCode();
    }
}
