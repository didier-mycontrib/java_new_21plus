package tp.withWebFlux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import tp.withWebFlux.data.Devise;

@Repository
public interface DeviseRepository extends ReactiveCrudRepository<Devise, String> {
	
}
