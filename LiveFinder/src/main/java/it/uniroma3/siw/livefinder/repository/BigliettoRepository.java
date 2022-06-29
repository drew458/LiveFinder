package it.uniroma3.siw.livefinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.livefinder.model.Biglietto;

public interface BigliettoRepository extends CrudRepository<Biglietto, Long>{

    @Query(value="select biglietto.id, biglietto.prezzo, biglietto.tipologia, biglietto.concerto_id, biglietto.num_posti "+
                "from biglietto, concerto "+
                "where concerto.id=biglietto.concerto_id and concerto.id=?1 "+
                "except "+
                    "(Select biglietto.id, biglietto.prezzo, biglietto.tipologia, biglietto.concerto_id, biglietto.num_posti "+
                        "from biglietto, users_biglietti "+
                        "where biglietto.id=users_biglietti.biglietti_id)",
        nativeQuery = true)
    public List<Biglietto> findNonCompratiByConcertoId(@Param("id") Long id);
    
}
