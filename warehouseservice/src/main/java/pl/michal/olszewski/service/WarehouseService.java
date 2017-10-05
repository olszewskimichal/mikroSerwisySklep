package pl.michal.olszewski.service;

import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.dto.WarehouseDTO;
import pl.michal.olszewski.entity.Address;
import pl.michal.olszewski.entity.Warehouse;
import pl.michal.olszewski.repository.WarehouseRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private static final int PAGE_LIMIT = 20;
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public WarehouseDTO getWarehouseById(final Long warehouseId) {
        return warehouseRepository.findById(warehouseId).map(v -> new WarehouseDTO(v.getName(), v.getAddress()))
                .orElse(null);
    }

    public WarehouseDTO getWarehouseByName(@NonNull final String warehouseName) {
        return warehouseRepository.findByName(warehouseName).map(v -> new WarehouseDTO(v.getName(), v.getAddress()))
                .orElse(null);
    }

    public List<WarehouseDTO> getWarehouses(final Integer limit, final Integer page) {
        PageRequest pageRequest = new PageRequest(getPage(page), getLimit(limit));
        return warehouseRepository.findAll(pageRequest).getContent().stream().map(v -> new WarehouseDTO(v.getName(), v.getAddress())).collect(Collectors.toList());
    }

    public void createWarehouse(final WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse(warehouseDTO);
        warehouseRepository.save(warehouse);
    }

    public void updateWarehouse(final WarehouseDTO warehouseDTO, final Long id) {
        warehouseRepository.updateWarehouse(warehouseDTO.getName(), warehouseDTO.getStreet(), warehouseDTO.getCity(), warehouseDTO.getCountry(), warehouseDTO.getZipCode(), id);
    }

    public void deleteWarehouse(final Long id) {
        warehouseRepository.delete(id);
    }

    private int getLimit(final Integer size) {
        return (Objects.isNull(size) ? PAGE_LIMIT : size);
    }

    private int getPage(final Integer page) {
        return (Objects.isNull(page) ? 0 : page);
    }
}
