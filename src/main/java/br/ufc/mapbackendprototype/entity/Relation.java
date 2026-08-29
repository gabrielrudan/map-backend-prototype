package br.ufc.mapbackendprototype.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "relations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Relation {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sourceId;

    @Column(nullable = false)
    private String targetId;
}