package it.uniroma3.siw.livefinder.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.livefinder.model.Indirizzo;
import it.uniroma3.siw.livefinder.model.Luogo;

public interface LuogoRepository extends CrudRepository<Luogo, Long>{

	boolean existsByNomeAndIndirizzo(String nome, Indirizzo indirizzo);
    
}
