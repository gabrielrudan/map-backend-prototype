package br.ufc.mapbackendprototype.service;

import br.ufc.mapbackendprototype.dto.relation.RelationRequest;
import br.ufc.mapbackendprototype.dto.relation.RelationResponse;
import br.ufc.mapbackendprototype.entity.Relation;
import br.ufc.mapbackendprototype.exception.ResourceNotFoundException;
import br.ufc.mapbackendprototype.repository.LocationRepository;
import br.ufc.mapbackendprototype.repository.RelationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RelationServiceTest {

    @Mock
    private RelationRepository relationRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private RelationService relationService;

    private RelationRequest request;

    @BeforeEach
    void setUp() {
        request = new RelationRequest(
                "Relação teste",
                "location-1",
                "location-2"
        );
    }

    @Test
    void deveCriarRelacaoQuandoLocalizacoesExistirem() {
        when(locationRepository.existsById("location-1"))
                .thenReturn(true);

        when(locationRepository.existsById("location-2"))
                .thenReturn(true);

        when(relationRepository.save(any(Relation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RelationResponse result = relationService.create(request);

        assertNotNull(result.id());
        assertEquals("location-1", result.sourceId());
        assertEquals("location-2", result.targetId());
    }

    @Test
    void deveLancarExcecaoQuandoOrigemNaoExistir() {
        when(locationRepository.existsById("location-1"))
                .thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> relationService.create(request)
        );

        verify(relationRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoDestinoNaoExistir() {
        when(locationRepository.existsById("location-1"))
                .thenReturn(true);

        when(locationRepository.existsById("location-2"))
                .thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> relationService.create(request)
        );

        verify(relationRepository, never()).save(any());
    }
}