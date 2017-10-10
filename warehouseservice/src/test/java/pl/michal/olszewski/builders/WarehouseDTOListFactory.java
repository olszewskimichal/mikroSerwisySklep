package pl.michal.olszewski.builders;

import pl.michal.olszewski.dto.WarehouseDTO;
import pl.michal.olszewski.entity.Address;
import pl.michal.olszewski.entity.Warehouse;
import pl.michal.olszewski.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class WarehouseDTOListFactory {

    private final WarehouseRepository repository;

    public WarehouseDTOListFactory(WarehouseRepository repository) {
        this.repository = repository;
    }

    public List<WarehouseDTO> buildNumberOfWarehousesDTOAndSave(int numberOfWarehouses) {
        List<WarehouseDTO> warehouseDTOS = new ArrayList<>();
        IntStream.range(0, numberOfWarehouses).forEachOrdered(number -> {
            Warehouse warehouse = Warehouse.builder().name(String.format("warehouse_%s", number)).address(Address.builder().city("city").country("pl").street("street").zipCode("zip").build()).build();
            repository.saveAndFlush(warehouse);
            warehouseDTOS.add(new WarehouseDTO(warehouse.getName(), warehouse.getAddress()));
        });
        return warehouseDTOS;
    }
    public List<Warehouse> buildNumberOfWarehousesAndSave(int numberOfWarehouses) {
        List<Warehouse> warehouses = new ArrayList<>();
        IntStream.range(0, numberOfWarehouses).forEachOrdered(number -> {
            Warehouse warehouse = Warehouse.builder().name(String.format("warehouse_%s", number)).address(Address.builder().city("city").country("pl").street("street").zipCode("zip").build()).build();
            repository.saveAndFlush(warehouse);
            warehouses.add(warehouse);
        });
        return warehouses;
    }

    public static List<WarehouseDTO> getNotPersistedWarehouses(int numberOfWarehouses) {
        List<WarehouseDTO> warehouseDTOS = new ArrayList<>();
        IntStream.range(0, numberOfWarehouses).forEachOrdered(number -> {
            Warehouse warehouse = Warehouse.builder().name(String.format("warehouse_%s", number)).address(Address.builder().city("city").country("pl").street("street").zipCode("zip").build()).build();
            warehouseDTOS.add(new WarehouseDTO(warehouse.getName(), warehouse.getAddress()));
        });
        return warehouseDTOS;
    }

}
