package it.uniroma3.siw.livefinder.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.livefinder.model.Citta;

public interface CittaRepository extends CrudRepository<Citta, Long>{

    public Optional<Citta> findByNome(String citta);
	
}
