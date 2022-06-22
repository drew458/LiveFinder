package it.uniroma3.siw.livefinder.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.livefinder.model.Citta;

public interface CittaRepository extends CrudRepository<Citta, Long>{
    
    /* query che dovrebbe funzionare ma che lancia un errore causato dal problema #2472 in Hibernate 5.6.6 e 5.6.7
    public Optional<Citta> findByNomeContaining(String citta);
    */

    /**
     * Con l'aggiunta di @Param questo errore non salta fuori
     * @param citta
     * @return
     */
    public Optional<Citta> findByNomeContainingIgnoreCase(@Param("citta") String citta);
	
}
