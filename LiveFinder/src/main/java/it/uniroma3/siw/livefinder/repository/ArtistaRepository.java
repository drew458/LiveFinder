package it.uniroma3.siw.livefinder.repository;

import java.time.Year;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.livefinder.model.Artista;

public interface ArtistaRepository extends CrudRepository<Artista, Long>{
    
	boolean existsByNomeAndGenereAndAnnoFormazione(String nome, String genere, Year annoDiFormazione);

	public List<Artista> findByNomeStartsWithIgnoreCase(@Param("nome") String primaLettera);

	public Artista findByNomeAndGenereAndAnnoFormazione(String nome, String genere, Year annoDiFormazione);
}
