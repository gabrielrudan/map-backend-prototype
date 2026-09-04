package br.ufc.mapbackendprototype.service;

import br.ufc.mapbackendprototype.dto.location.LocationRequest;
import br.ufc.mapbackendprototype.dto.location.LocationResponse;
import br.ufc.mapbackendprototype.entity.Location;
import br.ufc.mapbackendprototype.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<LocationResponse> findAll() {
        return locationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public LocationResponse findById(String id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localização não encontrada"));

        return toResponse(location);
    }

    public LocationResponse create(LocationRequest request) {
        Location location = Location.builder()
                .id(UUID.randomUUID().toString())
                .name(request.name())
                .description(request.description())
                .type(request.type())
                .lat(request.lat())
                .lng(request.lng())
                .build();

        return toResponse(locationRepository.save(location));
    }

    public LocationResponse update(String id, LocationRequest request) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localização não encontrada"));

        location.setName(request.name());
        location.setDescription(request.description());
        location.setType(request.type());
        location.setLat(request.lat());
        location.setLng(request.lng());

        return toResponse(locationRepository.save(location));
    }

    public void delete(String id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localização não encontrada"));

        locationRepository.delete(location);
    }

    private LocationResponse toResponse(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getDescription(),
                location.getType(),
                location.getLat(),
                location.getLng()
        );
    }
}
