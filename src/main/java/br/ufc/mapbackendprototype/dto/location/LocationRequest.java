package br.ufc.mapbackendprototype.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocationRequest(

        @NotBlank(message = "O nome é obrigatório")
        String name,

        String description,

        @NotBlank(message = "O tipo é obrigatório")
        String type,

        @NotNull(message = "A latitude é obrigatória")
        Double lat,

        @NotNull(message = "A longitude é obrigatória")
        Double lng
) {
}
