package pl.michal.olszewski.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.michal.olszewski.dto.WarehouseDTO;

import javax.persistence.Embeddable;

@Data
@Embeddable
@Builder
@NoArgsConstructor
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

    public Address(String street, String state, String city, String country, String zipCode) {
        this.street = street;
        this.state = state;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }

    public Address(WarehouseDTO warehouseDTO) {
        this.street = warehouseDTO.getStreet();
        this.state = warehouseDTO.getState();
        this.country = warehouseDTO.getCountry();
        this.city = warehouseDTO.getCity();
        this.zipCode = warehouseDTO.getZipCode();
    }
}
