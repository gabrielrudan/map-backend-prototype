package br.ufc.mapbackendprototype.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "zones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zone {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ElementCollection
    @CollectionTable(
            name = "zone_coordinates",
            joinColumns = @JoinColumn(name = "zone_id")
    )
    @OrderColumn(name = "coordinate_order")
    private List<Coordinate> coordinates = new ArrayList<>();
}
