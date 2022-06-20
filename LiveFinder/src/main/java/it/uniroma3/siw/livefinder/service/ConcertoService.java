package it.uniroma3.siw.livefinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.livefinder.model.Concerto;
import it.uniroma3.siw.livefinder.repository.ConcertoRepository;

@Service
public class ConcertoService {
	
	@Autowired
	private ConcertoRepository concertoRepository;
	
	/*
	 * Ottienere la lista dei concerti
	 */
	public List<Concerto> getAllConcerti(){
		List<Concerto> concerti = (List<Concerto>)concertoRepository.findAll();
		return concerti;
	}
	
	/*
	 * Ottenere i concerti di una città
	 */
	public List<Concerto> getByCitta(String citta){
		return concertoRepository.findByParolachiave(citta);
	}
}