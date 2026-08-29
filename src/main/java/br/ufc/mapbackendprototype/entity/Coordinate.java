package br.ufc.mapbackendprototype.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coordinate {

    private Double lat;

    private Double lng;
}
