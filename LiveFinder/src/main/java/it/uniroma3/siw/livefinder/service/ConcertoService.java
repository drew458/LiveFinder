package it.uniroma3.siw.livefinder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Citta;
import it.uniroma3.siw.livefinder.model.Concerto;
import it.uniroma3.siw.livefinder.repository.ConcertoRepository;

@Service
public class ConcertoService {

	@Autowired
	private CittaService cittaService;
	
	@Autowired
	private ConcertoRepository concertoRepository;

	public Concerto save(Concerto concerto){
		return concertoRepository.save(concerto);
	}
	
	/*
	 * Ottienere la lista dei concerti
	 */
	public List<Concerto> getAllConcerti(){
		return StreamSupport.stream(concertoRepository.findAll().spliterator(), true)
			.sorted()
            .collect(Collectors.toList());
	}

	public List<Concerto> findByCitta(String nomeCitta){
		Citta citta = cittaService.findByNome(nomeCitta);
		return concertoRepository.findByCitta(citta);
	}
	
	/*
	 * Ottenere i concerti di una città
	 */
	public List<Concerto> getByCitta(String citta){
		return concertoRepository.findByParolachiave(citta);
	}
}