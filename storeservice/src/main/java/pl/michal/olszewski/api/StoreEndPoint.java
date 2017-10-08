package pl.michal.olszewski.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.michal.olszewski.dto.StoreDTO;
import pl.michal.olszewski.service.StoreService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreEndPoint {
    private final StoreService storeService;

    public StoreEndPoint(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable("id") Long storeId) {
        return Optional.ofNullable(storeService.getStoreById(storeId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/name/{storeName}")
    public ResponseEntity<StoreDTO> getStoreByName(@PathVariable("storeName") String storeName) {
        return Optional.ofNullable(storeService.getStoreByName(storeName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StoreDTO> getStores(@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "page", required = false) Integer page) {
        return storeService.getStores(limit, page);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStore(@PathVariable("storeId") Long storeId, @RequestBody StoreDTO storeDTO) {
        storeService.updateStore(storeDTO, storeId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addStore(@RequestBody StoreDTO storeDTO) {
        storeService.createStore(storeDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStore(@PathVariable("id") Long storeId) {
        storeService.deleteStore(storeId);
    }


}
