package it.uniroma3.siw.livefinder.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Artista;
import it.uniroma3.siw.livefinder.model.Luogo;
import it.uniroma3.siw.livefinder.repository.ArtistaRepository;
import it.uniroma3.siw.livefinder.repository.LuogoRepository;

@Service
public class LuogoService {

    @Autowired
    private LuogoRepository luogoRepository;
	
	@Transactional
	public void save(Luogo luogo) {
		luogoRepository.save(luogo);
	}
	
	@Transactional
	public void delete(Luogo luogo) {
		luogoRepository.delete(luogo);
	}
	
	@Transactional
	public void deleteById(Long id) {
		luogoRepository.deleteById(id);
	}
	
	public Luogo findById(Long id) {
		return luogoRepository.findById(id).get();
	}

    public List<Luogo> findAll(){
        return StreamSupport.stream(luogoRepository.findAll().spliterator(), true)
            .sorted()
            .collect(Collectors.toList());
    }
    
    public boolean alreadyExists(Luogo target) {
		return luogoRepository.existsByNomeAndIndirizzoAndCap(target.getNome(), target.getIndirizzo(), target.getCap());
	}
}
