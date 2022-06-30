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

    public Citta findById(Long id){
        return cittaRepository.findById(id).orElse(null);
    }

    public List<Citta> findAll(){
        return StreamSupport.stream(cittaRepository.findAll().spliterator(), true)
            .sorted()
            .collect(Collectors.toList());
    }

    public Citta save(Citta citta){
        return cittaRepository.save(citta);
    }

    public boolean existsByNome(String nome){
        return cittaRepository.existsByNomeContainingIgnoreCase(nome);
    }

    public boolean alreadyExists(Citta citta){
        if(citta.getId()==null){
            return cittaRepository.existsByNome(citta.getNome());
        }else if(cittaRepository.existsByNome(citta.getNome())){
            Citta cittaEsistente = cittaRepository.findByNome(citta.getNome()).get();
            return !(citta.getId().equals(cittaEsistente.getId()));
        }else{
            return false;
        }
    }

	public Citta findByNome(String nome){
        return cittaRepository.findByNomeContainingIgnoreCase(nome).orElse(null);
    }
}
