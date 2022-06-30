package it.uniroma3.siw.livefinder.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.livefinder.model.Citta;

public interface CittaRepository extends CrudRepository<Citta, Long>{

    public Optional<Citta> findByNome(String nome);

    public boolean existsByNome(String nome);

    /**
     * Con l'aggiunta di @Param questo errore non salta fuori
     * @param nome
     * @return
     */
    public Optional<Citta> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    public boolean existsByNomeContainingIgnoreCase(@Param("nome") String nome);
	
}
