package br.ufc.mapbackendprototype.dto.relation;

public record RelationResponse(
        String id,
        String name,
        String sourceId,
        String targetId
) {
}
