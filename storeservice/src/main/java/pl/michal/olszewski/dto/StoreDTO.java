package pl.michal.olszewski.dto;

import lombok.*;
import pl.michal.olszewski.entity.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {

    private String name;
    //addres
    private String street;
    private String state;
    private String city;
    private String country;
    private String zipCode;

    public StoreDTO(String name, @NonNull Address address) {
        this.name = name;
        this.street = address.getStreet();
        this.state = address.getState();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.zipCode = address.getZipCode();
    }
}
