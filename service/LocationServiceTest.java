package br.ufc.mapbackendprototype.service;

import br.ufc.mapbackendprototype.dto.location.LocationRequest;
import br.ufc.mapbackendprototype.dto.location.LocationResponse;
import br.ufc.mapbackendprototype.entity.Location;
import br.ufc.mapbackendprototype.exception.ResourceNotFoundException;
import br.ufc.mapbackendprototype.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Location location;
    private LocationRequest request;

    @BeforeEach
    void setUp() {
        location = Location.builder()
                .id("location-1")
                .name("Local teste")
                .description("Descrição teste")
                .type("teste")
                .lat(-3.7)
                .lng(-38.5)
                .build();

        request = new LocationRequest(
                "Local teste",
                "Descrição teste",
                "teste",
                -3.7,
                -38.5
        );
    }

    @Test
    void deveListarTodasAsLocalizacoes() {
        when(locationRepository.findAll())
                .thenReturn(List.of(location));

        List<LocationResponse> result = locationService.findAll();

        assertEquals(1, result.size());
        assertEquals("location-1", result.get(0).id());
        assertEquals("Local teste", result.get(0).name());

        verify(locationRepository).findAll();
    }

    @Test
    void deveBuscarLocalizacaoPorId() {
        when(locationRepository.findById("location-1"))
                .thenReturn(Optional.of(location));

        LocationResponse result = locationService.findById("location-1");

        assertEquals("location-1", result.id());
        assertEquals("Local teste", result.name());

        verify(locationRepository).findById("location-1");
    }

    @Test
    void deveLancarExcecaoQuandoLocalizacaoNaoExistir() {
        when(locationRepository.findById("inexistente"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> locationService.findById("inexistente")
        );

        verify(locationRepository).findById("inexistente");
    }

    @Test
    void deveCriarLocalizacao() {
        when(locationRepository.save(any(Location.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LocationResponse result = locationService.create(request);

        assertNotNull(result.id());
        assertEquals("Local teste", result.name());
        assertEquals("teste", result.type());

        verify(locationRepository).save(any(Location.class));
    }

    @Test
    void deveAtualizarLocalizacao() {
        LocationRequest updateRequest = new LocationRequest(
                "Local atualizado",
                "Nova descrição",
                "novo-tipo",
                -4.0,
                -39.0
        );

        when(locationRepository.findById("location-1"))
                .thenReturn(Optional.of(location));

        when(locationRepository.save(any(Location.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LocationResponse result =
                locationService.update("location-1", updateRequest);

        assertEquals("Local atualizado", result.name());
        assertEquals("Nova descrição", result.description());
        assertEquals("novo-tipo", result.type());

        verify(locationRepository).findById("location-1");
        verify(locationRepository).save(location);
    }

    @Test
    void deveRemoverLocalizacao() {
        when(locationRepository.findById("location-1"))
                .thenReturn(Optional.of(location));

        locationService.delete("location-1");

        verify(locationRepository).delete(location);
    }
}