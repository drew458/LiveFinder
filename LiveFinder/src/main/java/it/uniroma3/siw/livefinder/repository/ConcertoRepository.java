package it.uniroma3.siw.livefinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.livefinder.model.Citta;
import it.uniroma3.siw.livefinder.model.Concerto;

public interface ConcertoRepository extends CrudRepository<Concerto, Long>{
		//Query personalizzata
		@Query(value = "select a.nome, ci.nome, co.data from concerto co, citta ci, tour t, artista a where c.citta like $:parolachiave% and co.citta_id=ci.id and t.artista_id=a.id", nativeQuery = true)
		List<Concerto> findByParolachiave(@Param("parolachiave") String parolachiave);
}
