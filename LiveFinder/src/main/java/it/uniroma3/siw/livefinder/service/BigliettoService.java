package it.uniroma3.siw.livefinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Biglietto;
import it.uniroma3.siw.livefinder.repository.BigliettoRepository;

@Service
public class BigliettoService {

    @Autowired
    private BigliettoRepository bigliettoRepository;

    public void saveAll(List<Biglietto> biglietti){
        bigliettoRepository.saveAll(biglietti);
    }

    public void delete(Biglietto biglietto){
        bigliettoRepository.delete(biglietto);
    }

    public Biglietto findById(Long id){
        return bigliettoRepository.findById(id).orElse(null);
    }

    public void deleteNonCompratiByConcertoId(Long id){
        List<Biglietto> nonComprati = bigliettoRepository.findNonCompratiByConcertoId(id);
        bigliettoRepository.deleteAll(nonComprati);
    }

}
