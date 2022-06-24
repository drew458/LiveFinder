package it.uniroma3.siw.livefinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Biglietto;
import it.uniroma3.siw.livefinder.repository.BigliettoRepository;

@Service
public class BigliettoService {

    @Autowired
    private BigliettoRepository bigliettoRepository;

    public void delete(Biglietto biglietto){
        bigliettoRepository.delete(biglietto);
    }

}
