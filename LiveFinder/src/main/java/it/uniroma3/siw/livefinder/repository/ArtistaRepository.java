package it.uniroma3.siw.livefinder.repository;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.livefinder.model.Artista;

public interface ArtistaRepository extends CrudRepository<Artista, Long>{
    
	boolean existsByNomeAndGenereAndAnnoFormazione(String nome, String genere, Year annoDiFormazione);

	@Query(value="SELECT min(b.prezzo) FROM Biglietto b, Concerto c, Tour t, Artista a WHERE c.id=b.concerto_id AND c.tour_id=t.id AND t.artista_id=a.id AND a.nome=?1",
			nativeQuery=true)
	public BigDecimal findMinPrezzoByArtista(@Param("artista") String artista);

	public List<Artista> findTop5ByNomeStartsWithIgnoreCaseOrderByNome(@Param("nome") String primaLettera);

	public List<Artista> findByNomeStartsWithIgnoreCaseOrderByNome(@Param("nome") String primaLettera);

	public Artista findByNomeAndGenereAndAnnoFormazione(String nome, String genere, Year annoDiFormazione);
}
