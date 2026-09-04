package br.ufc.mapbackendprototype.service;

import br.ufc.mapbackendprototype.dto.zone.*;
import br.ufc.mapbackendprototype.entity.Coordinate;
import br.ufc.mapbackendprototype.entity.Zone;
import br.ufc.mapbackendprototype.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.ufc.mapbackendprototype.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    public List<ZoneResponse> findAll() {
        return zoneRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ZoneResponse findById(String id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada"));

        return toResponse(zone);
    }

    public ZoneResponse create(ZoneRequest request) {
        Zone zone = Zone.builder()
                .id(UUID.randomUUID().toString())
                .name(request.name())
                .color(request.color())
                .coordinates(
                        request.coordinates()
                                .stream()
                                .map(this::toEntity)
                                .toList()
                )
                .build();

        return toResponse(zoneRepository.save(zone));
    }

    public ZoneResponse update(String id, ZoneRequest request) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada"));

        zone.setName(request.name());
        zone.setColor(request.color());
        zone.setCoordinates(
                request.coordinates()
                        .stream()
                        .map(this::toEntity)
                        .toList()
        );

        return toResponse(zoneRepository.save(zone));
    }

    public void delete(String id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada"));

        zoneRepository.delete(zone);
    }

    private Coordinate toEntity(CoordinateRequest request) {
        return Coordinate.builder()
                .lat(request.lat())
                .lng(request.lng())
                .build();
    }

    private ZoneResponse toResponse(Zone zone) {
        List<CoordinateResponse> coordinates = zone.getCoordinates()
                .stream()
                .map(coordinate -> new CoordinateResponse(
                        coordinate.getLat(),
                        coordinate.getLng()
                ))
                .toList();

        return new ZoneResponse(
                zone.getId(),
                zone.getName(),
                zone.getColor(),
                coordinates
        );
    }
}