package it.uniroma3.siw.livefinder.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.livefinder.model.Luogo;

public interface LuogoRepository extends CrudRepository<Luogo, Long>{

	boolean existsByNomeAndIndirizzoAndCap(String nome, String indirizzo, String cap);
    
}
