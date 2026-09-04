package br.ufc.mapbackendprototype.dto.location;

public record LocationResponse(
        String id,
        String name,
        String description,
        String type,
        Double lat,
        Double lng
) {
}
