package pl.michal.olszewski.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.michal.olszewski.dto.WarehouseDTO;
import pl.michal.olszewski.service.WarehouseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseEndPoint {
    private final WarehouseService warehouseService;

    public WarehouseEndPoint(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<WarehouseDTO> getWarehouse(@PathVariable("id") Long warehouseId) {
        return Optional.ofNullable(warehouseService.getWarehouseById(warehouseId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/name/{warehouseName}")
    public ResponseEntity<WarehouseDTO> getWarehouseByName(@PathVariable("warehouseName") String warehouseName) {
        return Optional.ofNullable(warehouseService.getWarehouseByName(warehouseName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WarehouseDTO> getWarehouses(@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "page", required = false) Integer page) {
        return warehouseService.getWarehouses(limit, page);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{warehouseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateWarehouse(@PathVariable("warehouseId") Long warehouseId, @RequestBody WarehouseDTO warehouseDTO) {
        warehouseService.updateWarehouse(warehouseDTO, warehouseId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        warehouseService.createWarehouse(warehouseDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWarehouse(@PathVariable("id") Long warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
    }
}
