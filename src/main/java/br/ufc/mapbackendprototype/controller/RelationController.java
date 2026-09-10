package br.ufc.mapbackendprototype.controller;

import br.ufc.mapbackendprototype.dto.relation.RelationRequest;
import br.ufc.mapbackendprototype.dto.relation.RelationResponse;
import br.ufc.mapbackendprototype.service.RelationService;
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
 * Controller responsável pelos endpoints REST de relações.
 *
 * <p>Disponibiliza operações de criação, consulta,
 * atualização e remoção de relações entre localizações.</p>
 */
@RestController
@RequestMapping("/relations")
@RequiredArgsConstructor
@Tag(
        name = "Relations",
        description = "Operações de gerenciamento de relações entre localizações"
)
public class RelationController {

    private final RelationService relationService;

    @GetMapping
    @Operation(
            summary = "Listar relações",
            description = "Retorna todas as relações cadastradas."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Relações retornadas com sucesso"
    )
    public ResponseEntity<List<RelationResponse>> findAll() {
        return ResponseEntity.ok(relationService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar relação por ID",
            description = "Retorna uma relação a partir do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Relação encontrada"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relação não encontrada"
            )
    })
    public ResponseEntity<RelationResponse> findById(
            @Parameter(
                    description = "ID da relação",
                    required = true
            )
            @PathVariable String id
    ) {
        return ResponseEntity.ok(
                relationService.findById(id)
        );
    }

    @PostMapping
    @Operation(
            summary = "Criar relação",
            description = "Cria uma nova relação entre duas localizações existentes."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Relação criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Localização de origem ou destino não encontrada"
            )
    })
    public ResponseEntity<RelationResponse> create(
            @Valid @RequestBody RelationRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(relationService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar relação",
            description = "Atualiza uma relação existente entre localizações."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Relação atualizada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados da requisição inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relação ou localização não encontrada"
            )
    })
    public ResponseEntity<RelationResponse> update(
            @Parameter(
                    description = "ID da relação",
                    required = true
            )
            @PathVariable String id,
            @Valid @RequestBody RelationRequest request
    ) {
        return ResponseEntity.ok(
                relationService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover relação",
            description = "Remove uma relação cadastrada."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Relação removida com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Relação não encontrada"
            )
    })
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "ID da relação",
                    required = true
            )
            @PathVariable String id
    ) {
        relationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}