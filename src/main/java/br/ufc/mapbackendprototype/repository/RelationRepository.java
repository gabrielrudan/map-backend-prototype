package br.ufc.mapbackendprototype.repository;

import br.ufc.mapbackendprototype.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationRepository extends JpaRepository<Relation, String> {
}
