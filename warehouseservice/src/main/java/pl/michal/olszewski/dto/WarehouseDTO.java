package pl.michal.olszewski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import pl.michal.olszewski.entity.Address;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class WarehouseDTO implements Serializable {
    private final String name;
    //addres
    private final String street;
    private final String state;
    private final String city;
    private final String country;
    private final String zipCode;

    public WarehouseDTO(String name, @NonNull Address address) {
        this.name = name;
        this.street = address.getStreet();
        this.state = address.getState();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.zipCode = address.getZipCode();
    }
}
