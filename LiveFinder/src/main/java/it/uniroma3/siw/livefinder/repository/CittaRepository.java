package it.uniroma3.siw.livefinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.livefinder.model.Citta;

public interface CittaRepository extends CrudRepository<Citta, Long>{
	
}
