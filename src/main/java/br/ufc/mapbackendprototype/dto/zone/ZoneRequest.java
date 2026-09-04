package br.ufc.mapbackendprototype.dto.zone;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ZoneRequest(

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "A cor é obrigatória")
        String color,

        @NotEmpty(message = "A zona deve possuir coordenadas")
        List<@Valid CoordinateRequest> coordinates
) {
}
