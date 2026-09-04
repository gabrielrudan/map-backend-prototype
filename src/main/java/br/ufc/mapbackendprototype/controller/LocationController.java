package br.ufc.mapbackendprototype.controller;

import br.ufc.mapbackendprototype.dto.location.LocationRequest;
import br.ufc.mapbackendprototype.dto.location.LocationResponse;
import br.ufc.mapbackendprototype.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Tag(name = "Locations", description = "Operações relacionadas às localizações")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    @Operation(summary = "Listar todas as localizações")
    public ResponseEntity<List<LocationResponse>> findAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar localização por ID")
    public ResponseEntity<LocationResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova localização")
    public ResponseEntity<LocationResponse> create(
            @Valid @RequestBody LocationRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma localização")
    public ResponseEntity<LocationResponse> update(
            @PathVariable String id,
            @Valid @RequestBody LocationRequest request
    ) {
        return ResponseEntity.ok(locationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma localização")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        locationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
