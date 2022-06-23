package it.uniroma3.siw.livefinder.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Citta;
import it.uniroma3.siw.livefinder.repository.CittaRepository;

@Service
public class CittaService {

    @Autowired
    private CittaRepository cittaRepository;

    public List<Citta> findAll(){
        return StreamSupport.stream(cittaRepository.findAll().spliterator(), true)
            .collect(Collectors.toList());
    }

    public boolean existsByNome(String nome){
        return cittaRepository.existsByNomeContainingIgnoreCase(nome);
    }

	public Citta findByNome(String nome){
        return cittaRepository.findByNomeContainingIgnoreCase(nome).orElse(null);
    }
}
