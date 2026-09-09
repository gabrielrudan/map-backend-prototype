package br.ufc.mapbackendprototype.service;

import br.ufc.mapbackendprototype.dto.zone.CoordinateRequest;
import br.ufc.mapbackendprototype.dto.zone.ZoneRequest;
import br.ufc.mapbackendprototype.dto.zone.ZoneResponse;
import br.ufc.mapbackendprototype.entity.Coordinate;
import br.ufc.mapbackendprototype.entity.Zone;
import br.ufc.mapbackendprototype.exception.ResourceNotFoundException;
import br.ufc.mapbackendprototype.repository.ZoneRepository;
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
class ZoneServiceTest {

    @Mock
    private ZoneRepository zoneRepository;

    @InjectMocks
    private ZoneService zoneService;

    private Zone zone;
    private ZoneRequest request;

    @BeforeEach
    void setUp() {
        zone = Zone.builder()
                .id("zone-1")
                .name("Zona teste")
                .color("#FFFFFF")
                .coordinates(List.of(
                        new Coordinate(-3.7, -38.5),
                        new Coordinate(-3.8, -38.6)
                ))
                .build();

        request = new ZoneRequest(
                "Zona teste",
                "#FFFFFF",
                List.of(
                        new CoordinateRequest(-3.7, -38.5),
                        new CoordinateRequest(-3.8, -38.6)
                )
        );
    }

    @Test
    void deveListarTodasAsZonas() {
        when(zoneRepository.findAll())
                .thenReturn(List.of(zone));

        List<ZoneResponse> result = zoneService.findAll();

        assertEquals(1, result.size());
        assertEquals("zone-1", result.get(0).id());
    }

    @Test
    void deveBuscarZonaPorId() {
        when(zoneRepository.findById("zone-1"))
                .thenReturn(Optional.of(zone));

        ZoneResponse result = zoneService.findById("zone-1");

        assertEquals("Zona teste", result.name());
        assertEquals(2, result.coordinates().size());
    }

    @Test
    void deveLancarExcecaoQuandoZonaNaoExistir() {
        when(zoneRepository.findById("inexistente"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> zoneService.findById("inexistente")
        );
    }

    @Test
    void deveCriarZona() {
        when(zoneRepository.save(any(Zone.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ZoneResponse result = zoneService.create(request);

        assertNotNull(result.id());
        assertEquals("Zona teste", result.name());
        assertEquals(2, result.coordinates().size());
    }
}