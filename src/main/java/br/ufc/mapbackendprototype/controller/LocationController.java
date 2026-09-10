package br.ufc.mapbackendprototype.controller;

import br.ufc.mapbackendprototype.dto.location.LocationRequest;
import br.ufc.mapbackendprototype.dto.location.LocationResponse;
import br.ufc.mapbackendprototype.service.LocationService;
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
 * Controller responsável pelos endpoints REST de localizações.
 *
 * <p>Disponibiliza operações de criação, consulta,
 * atualização e remoção de locais.</p>
 */
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Tag(
        name = "Locations",
        description = "Operações de gerenciamento de localizações"
)
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    @Operation(
            summary = "Listar localizações",
            description = "Retorna todas as localizações cadastradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Localizações retornadas com sucesso"
    )
    public ResponseEntity<List<LocationResponse>> findAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar localização por ID",
            description = "Retorna uma localização a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Localização encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Localização não encontrada"
            )
    })
    public ResponseEntity<LocationResponse> findById(
            @Parameter(description = "ID da localização", required = true)
            @PathVariable String id
    ) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar localização",
            description = "Cadastra uma nova localização."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Localização criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos"
            )
    })
    public ResponseEntity<LocationResponse> create(
            @Valid @RequestBody LocationRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar localização",
            description = "Atualiza os dados de uma localização existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Localização atualizada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Localização não encontrada"
            )
    })
    public ResponseEntity<LocationResponse> update(
            @Parameter(description = "ID da localização", required = true)
            @PathVariable String id,
            @Valid @RequestBody LocationRequest request
    ) {
        return ResponseEntity.ok(
                locationService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover localização",
            description = "Remove uma localização cadastrada."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Localização removida com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Localização não encontrada"
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da localização", required = true)
            @PathVariable String id
    ) {
        locationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}