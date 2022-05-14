package it.uniroma3.siw.livefinder.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.livefinder.model.pojo.Concerto;

public interface ConcertoRepository extends CrudRepository<Concerto, Long>{
    
}
