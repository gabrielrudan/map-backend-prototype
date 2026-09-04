package br.ufc.mapbackendprototype.dto.zone;

import jakarta.validation.constraints.NotNull;

public record CoordinateRequest(

        @NotNull(message = "A latitude é obrigatória")
        Double lat,

        @NotNull(message = "A longitude é obrigatória")
        Double lng
) {
}