package br.ufc.mapbackendprototype.service;

import br.ufc.mapbackendprototype.dto.relation.RelationRequest;
import br.ufc.mapbackendprototype.dto.relation.RelationResponse;
import br.ufc.mapbackendprototype.entity.Relation;
import br.ufc.mapbackendprototype.repository.LocationRepository;
import br.ufc.mapbackendprototype.repository.RelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.ufc.mapbackendprototype.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RelationService {

    private final RelationRepository relationRepository;
    private final LocationRepository locationRepository;

    public List<RelationResponse> findAll() {
        return relationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RelationResponse findById(String id) {
        Relation relation = relationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relação não encontrada"));

        return toResponse(relation);
    }

    public RelationResponse create(RelationRequest request) {
        validateLocations(request.sourceId(), request.targetId());

        Relation relation = Relation.builder()
                .id(UUID.randomUUID().toString())
                .name(request.name())
                .sourceId(request.sourceId())
                .targetId(request.targetId())
                .build();

        return toResponse(relationRepository.save(relation));
    }

    public RelationResponse update(String id, RelationRequest request) {
        Relation relation = relationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relação não encontrada"));

        validateLocations(request.sourceId(), request.targetId());

        relation.setName(request.name());
        relation.setSourceId(request.sourceId());
        relation.setTargetId(request.targetId());

        return toResponse(relationRepository.save(relation));
    }

    public void delete(String id) {
        Relation relation = relationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relação não encontrada"));

        relationRepository.delete(relation);
    }

    private void validateLocations(String sourceId, String targetId) {
        if (!locationRepository.existsById(sourceId)) {
            throw new ResourceNotFoundException("Localização de origem não encontrada");
        }

        if (!locationRepository.existsById(targetId)) {
            throw new ResourceNotFoundException("Localização de destino não encontrada");
        }
    }

    private RelationResponse toResponse(Relation relation) {
        return new RelationResponse(
                relation.getId(),
                relation.getName(),
                relation.getSourceId(),
                relation.getTargetId()
        );
    }
}
