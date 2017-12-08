package pl.michal.olszewski.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.api.WarehouseEndPoint;
import pl.michal.olszewski.dto.WarehouseDTO;
import pl.michal.olszewski.dto.WarehouseProductDTO;
import pl.michal.olszewski.service.WarehouseService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class WarehousesEndPointTest {
    private WarehouseEndPoint endPoint;

    @Mock
    private WarehouseService service;

    @Before
    public void setUp() {
        initMocks(this);
        endPoint = new WarehouseEndPoint(service);
    }

    @Test
    public void shouldReturnWarehouseById() {
        given(service.getWarehouseById(1L)).willReturn(WarehouseDTO.builder().city("city").name("nazwa").build());

        ResponseEntity<WarehouseDTO> warehouse = endPoint.getWarehouse(1L);

        assertThat(warehouse).isNotNull();
        assertThat(warehouse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(warehouse.getBody()).isNotNull();
        assertThat(warehouse.getBody().getName()).isEqualTo("nazwa");
    }

    @Test
    public void shouldNotReturnWarehouseByIdWhenNotExist() {
        given(service.getWarehouseById(1L)).willReturn(null);

        ResponseEntity<WarehouseDTO> warehouse = endPoint.getWarehouse(1L);

        assertThat(warehouse).isNotNull();
        assertThat(warehouse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(warehouse.getBody()).isNull();
    }

    @Test
    public void shouldReturnWarehouseByName() {
        given(service.getWarehouseByName("nazwa2")).willReturn(WarehouseDTO.builder().name("nazwa2").build());

        ResponseEntity<WarehouseDTO> warehouse = endPoint.getWarehouseByName("nazwa2");

        assertThat(warehouse).isNotNull();
        assertThat(warehouse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(warehouse.getBody()).isNotNull();
        assertThat(warehouse.getBody().getName()).isEqualTo("nazwa2");
    }

    @Test
    public void shouldNotReturnWarehouseByNameWhenNotExist() {
        given(service.getWarehouseByName("nazwa2")).willReturn(null);

        ResponseEntity<WarehouseDTO> warehouse = endPoint.getWarehouseByName("nazwa2");

        assertThat(warehouse).isNotNull();
        assertThat(warehouse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(warehouse.getBody()).isNull();
    }

    @Test
    public void shouldMoveProductsToWarehouse() {
        given(service.moveProductsToWarehouse(WarehouseProductDTO.builder().build())).willReturn(true);

        ResponseEntity<String> responseEntity = endPoint.moveProductsToWarehouse(WarehouseProductDTO.builder().build());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEmpty();
    }

    @Test
    public void shouldNotMoveProductsToWarehouseOnError() {
        given(service.moveProductsToWarehouse(WarehouseProductDTO.builder().build())).willReturn(null);

        ResponseEntity<String> responseEntity = endPoint.moveProductsToWarehouse(WarehouseProductDTO.builder().build());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isEmpty();
    }

    @Test
    public void shouldRemoveProductsFromWarehouse() {
        given(service.removeProductsFromWarehouse(WarehouseProductDTO.builder().build())).willReturn(true);

        ResponseEntity<String> responseEntity = endPoint.removeProductsFromWarehouse(WarehouseProductDTO.builder().build());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEmpty();
    }

    @Test
    public void shouldNotRemoveProductsFromWarehouse() {
        given(service.removeProductsFromWarehouse(WarehouseProductDTO.builder().build())).willReturn(null);

        ResponseEntity<String> responseEntity = endPoint.removeProductsFromWarehouse(WarehouseProductDTO.builder().build());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isEmpty();
    }

}
