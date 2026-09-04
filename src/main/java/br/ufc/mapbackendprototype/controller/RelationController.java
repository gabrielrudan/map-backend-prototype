package br.ufc.mapbackendprototype.controller;

import br.ufc.mapbackendprototype.dto.relation.RelationRequest;
import br.ufc.mapbackendprototype.dto.relation.RelationResponse;
import br.ufc.mapbackendprototype.service.RelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ufc.mapbackendprototype.exception.ApiError;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/relations")
@RequiredArgsConstructor
@Tag(name = "Relations", description = "Operações relacionadas às relações entre localizações")
public class RelationController {

    private final RelationService relationService;

    @GetMapping
    @Operation(summary = "Listar todas as relações")
    public ResponseEntity<List<RelationResponse>> findAll() {
        return ResponseEntity.ok(relationService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar relação por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relação encontrada"),
            @ApiResponse(responseCode = "404", description = "Relação não encontrada")
    })
    public ResponseEntity<RelationResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(relationService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova relação")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Relação criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<RelationResponse> create(
            @Valid @RequestBody RelationRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(relationService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma relação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relação atualizada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Relação não encontrada")
    })
    public ResponseEntity<RelationResponse> update(
            @PathVariable String id,
            @Valid @RequestBody RelationRequest request
    ) {
        return ResponseEntity.ok(relationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma relação")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Relação removida"),
            @ApiResponse(responseCode = "404", description = "Relação não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        relationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
