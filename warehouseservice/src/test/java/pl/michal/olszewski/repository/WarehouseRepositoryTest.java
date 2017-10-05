package pl.michal.olszewski.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.JpaTestBase;
import pl.michal.olszewski.dto.WarehouseDTO;
import pl.michal.olszewski.entity.Address;
import pl.michal.olszewski.entity.Warehouse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseRepositoryTest extends JpaTestBase {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    public void shouldFindWarehouseById() {
        //given
        Long id = entityManager.persistAndFlush(new Warehouse(WarehouseDTO.builder().city("city").country("pl").name("warehouse").street("street").build())).getId();
        //when
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        //then
        assertThat(warehouse.isPresent()).isTrue();
        assertThat(warehouse.get().getName()).isEqualTo("warehouse");
    }

    @Test
    public void shouldFindWarehouseByName() {
        //given
        entityManager.persistAndFlush(new Warehouse(WarehouseDTO.builder().city("city2").country("pl").name("warehouse2").street("street2").build())).getId();
        //when
        Optional<Warehouse> warehouse = warehouseRepository.findByName("warehouse2");
        //then
        assertThat(warehouse.isPresent()).isTrue();
        assertThat(warehouse.get().getName()).isEqualTo("warehouse2");
    }

    @Test
    public void shouldUpdateWarehouse() {
        //given
        Warehouse warehouse = entityManager.persistAndFlush(new Warehouse(WarehouseDTO.builder().city("city").country("pl").name("warehouse").street("street").build()));
        //when
        warehouseRepository.updateWarehouse("warehouse3", "str", "city", "DE", "zip", warehouse.getId());
        entityManager.clear();
        //then
        Warehouse updatedWarehouse = warehouseRepository.findOne(warehouse.getId());
        assertThat(updatedWarehouse).isNotNull();
        assertThat(updatedWarehouse.getName()).isEqualTo("warehouse3");
        assertThat(updatedWarehouse.getAddress().getStreet()).isEqualTo("str");
        assertThat(updatedWarehouse.getAddress().getCountry()).isEqualTo("DE");
        assertThat(updatedWarehouse.getAddress().getZipCode()).isEqualTo("zip");
    }
}