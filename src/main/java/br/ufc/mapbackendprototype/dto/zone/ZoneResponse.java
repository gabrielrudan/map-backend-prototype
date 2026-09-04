package br.ufc.mapbackendprototype.dto.zone;

import java.util.List;

public record ZoneResponse(
        String id,
        String name,
        String color,
        List<CoordinateResponse> coordinates
) {
}
