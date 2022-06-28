package it.uniroma3.siw.livefinder.repository;

import java.time.Year;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.livefinder.model.Artista;

public interface ArtistaRepository extends CrudRepository<Artista, Long>{
    
	boolean existsByNomeAndGenereAndAnnoFormazione(String nome, String genere, Year annoDiFormazione);

	public Artista findByNomeAndGenereAndAnnoFormazione(String nome, String genere, Year annoDiFormazione);
}
