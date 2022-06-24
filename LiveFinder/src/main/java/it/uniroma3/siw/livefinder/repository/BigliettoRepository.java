package it.uniroma3.siw.livefinder.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.livefinder.model.Biglietto;

public interface BigliettoRepository extends CrudRepository<Biglietto, Long>{
    
}
