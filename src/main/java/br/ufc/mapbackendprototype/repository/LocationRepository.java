package br.ufc.mapbackendprototype.repository;

import br.ufc.mapbackendprototype.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {
}
