package br.ufc.mapbackendprototype.dto.relation;

import jakarta.validation.constraints.NotBlank;

public record RelationRequest(

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "A localização de origem é obrigatória")
        String sourceId,

        @NotBlank(message = "A localização de destino é obrigatória")
        String targetId
) {
}
