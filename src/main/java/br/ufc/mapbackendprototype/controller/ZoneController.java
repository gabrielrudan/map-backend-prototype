package br.ufc.mapbackendprototype.controller;

import br.ufc.mapbackendprototype.dto.zone.ZoneRequest;
import br.ufc.mapbackendprototype.dto.zone.ZoneResponse;
import br.ufc.mapbackendprototype.service.ZoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelos endpoints REST de zonas.
 *
 * <p>Disponibiliza operações de criação, consulta,
 * atualização e remoção de zonas.</p>
 */
@RestController
@RequestMapping("/zones")
@RequiredArgsConstructor
@Tag(
        name = "Zones",
        description = "Operações de gerenciamento de zonas"
)
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping
    @Operation(
            summary = "Listar zonas",
            description = "Retorna todas as zonas cadastradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Zonas retornadas com sucesso"
    )
    public ResponseEntity<List<ZoneResponse>> findAll() {
        return ResponseEntity.ok(zoneService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar zona por ID",
            description = "Retorna uma zona a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Zona encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Zona não encontrada"
            )
    })
    public ResponseEntity<ZoneResponse> findById(
            @Parameter(
                    description = "ID da zona",
                    required = true
            )
            @PathVariable String id
    ) {
        return ResponseEntity.ok(zoneService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar zona",
            description = "Cadastra uma nova zona e suas coordenadas."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Zona criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos"
            )
    })
    public ResponseEntity<ZoneResponse> create(
            @Valid @RequestBody ZoneRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(zoneService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar zona",
            description = "Atualiza os dados e as coordenadas de uma zona existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Zona atualizada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Zona não encontrada"
            )
    })
    public ResponseEntity<ZoneResponse> update(
            @Parameter(
                    description = "ID da zona",
                    required = true
            )
            @PathVariable String id,
            @Valid @RequestBody ZoneRequest request
    ) {
        return ResponseEntity.ok(
                zoneService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover zona",
            description = "Remove uma zona cadastrada."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Zona removida com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Zona não encontrada"
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "ID da zona",
                    required = true
            )
            @PathVariable String id
    ) {
        zoneService.delete(id);

        return ResponseEntity.noContent().build();
    }
}