package pl.michal.olszewski.unit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.api.StoreEndPoint;
import pl.michal.olszewski.dto.StoreDTO;
import pl.michal.olszewski.service.StoreService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class StoresEndPointTest {
    private StoreEndPoint endPoint;

    @Mock
    private StoreService service;

    @Before
    public void setUp() {
        initMocks(this);
        endPoint = new StoreEndPoint(service);
    }

    @Test
    public void shouldReturnStoreById() {
        given(service.getStoreById(1L)).willReturn(StoreDTO.builder().city("city").name("nazwa").build());

        ResponseEntity<StoreDTO> store = endPoint.getStore(1L);

        assertThat(store).isNotNull();
        assertThat(store.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(store.getBody()).isNotNull();
        assertThat(store.getBody().getName()).isEqualTo("nazwa");
    }

    @Test
    public void shouldNotReturnStoreByIdWhenNotExist() {
        given(service.getStoreById(1L)).willReturn(null);

        ResponseEntity<StoreDTO> store = endPoint.getStore(1L);

        assertThat(store).isNotNull();
        assertThat(store.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(store.getBody()).isNull();
    }

    @Test
    public void shouldReturnStoreByName() {
        given(service.getStoreByName("nazwa2")).willReturn(StoreDTO.builder().name("nazwa2").build());

        ResponseEntity<StoreDTO> store = endPoint.getStoreByName("nazwa2");

        assertThat(store).isNotNull();
        assertThat(store.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(store.getBody()).isNotNull();
        assertThat(store.getBody().getName()).isEqualTo("nazwa2");
    }

    @Test
    public void shouldNotReturnStoreByNameWhenNotExist() {
        given(service.getStoreByName("nazwa2")).willReturn(null);

        ResponseEntity<StoreDTO> store = endPoint.getStoreByName("nazwa2");

        assertThat(store).isNotNull();
        assertThat(store.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(store.getBody()).isNull();
    }

}
