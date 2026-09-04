package br.ufc.mapbackendprototype.controller;

import br.ufc.mapbackendprototype.dto.zone.ZoneRequest;
import br.ufc.mapbackendprototype.dto.zone.ZoneResponse;
import br.ufc.mapbackendprototype.service.ZoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zones")
@RequiredArgsConstructor
@Tag(name = "Zones", description = "Operações relacionadas às zonas")
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping
    @Operation(summary = "Listar todas as zonas")
    public ResponseEntity<List<ZoneResponse>> findAll() {
        return ResponseEntity.ok(zoneService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar zona por ID")
    public ResponseEntity<ZoneResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(zoneService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova zona")
    public ResponseEntity<ZoneResponse> create(
            @Valid @RequestBody ZoneRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(zoneService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma zona")
    public ResponseEntity<ZoneResponse> update(
            @PathVariable String id,
            @Valid @RequestBody ZoneRequest request
    ) {
        return ResponseEntity.ok(zoneService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma zona")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        zoneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
