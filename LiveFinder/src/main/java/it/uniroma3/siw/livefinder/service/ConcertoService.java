package it.uniroma3.siw.livefinder.service;

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

	public Concerto findById(Long id){
		return concertoRepository.findById(id).orElse(null);
	}

	public boolean alreadyExists(Concerto concerto){
		if(concerto.getId() == null){	//se l'id non c'è vuol dire che devo controllare che non stia creando un duplicato
			return concerto.getTour()!=null && concertoRepository.existsByTourNomeAndData(concerto.getTour().getNome(), concerto.getData());
		}else if(concertoRepository.existsByTourNomeAndData(concerto.getTour().getNome(), concerto.getData())){ //se l'id c'è controllo comunque che non creo un duplicato
			Concerto esistente = concertoRepository.findByTourNomeAndData(concerto.getTour().getNome(), concerto.getData());
			return !(concerto.getId().equals(esistente.getId())); //se l'id non è lo stesso vuol dire che ho creato un duplicato
		}else{
			return false;
		}
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
		if(!cittaService.existsByNome(nomeCitta)){
			return null;
		}
		Citta citta = cittaService.findByNome(nomeCitta);
		return concertoRepository.findByLuogoCitta(citta);
	}
	
	/*
	 * Ottenere i concerti di una città
	 */
	public List<Concerto> getByCitta(String citta){
		return concertoRepository.findByParolachiave(citta);
	}
}