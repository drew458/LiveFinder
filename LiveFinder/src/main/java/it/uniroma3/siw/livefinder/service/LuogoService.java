package it.uniroma3.siw.livefinder.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Luogo;
import it.uniroma3.siw.livefinder.repository.LuogoRepository;

@Service
public class LuogoService {

    @Autowired
    private LuogoRepository luogoRepository;

    public List<Luogo> findAll(){
        return StreamSupport.stream(luogoRepository.findAll().spliterator(), true)
            .sorted()
            .collect(Collectors.toList());
    }

}
