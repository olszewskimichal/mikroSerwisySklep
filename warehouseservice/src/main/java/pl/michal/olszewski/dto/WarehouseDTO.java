package pl.michal.olszewski.dto;

import lombok.*;
import pl.michal.olszewski.entity.Address;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO implements Serializable {
    private String name;
    //addres
    private String street;
    private String state;
    private String city;
    private String country;
    private String zipCode;

    public WarehouseDTO(String name, @NonNull Address address) {
        this.name = name;
        this.street = address.getStreet();
        this.state = address.getState();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.zipCode = address.getZipCode();
    }
}
